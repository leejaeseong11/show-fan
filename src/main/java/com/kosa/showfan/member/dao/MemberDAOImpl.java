package com.kosa.showfan.member.dao;

import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.member.dto.MemberDTO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberDAOImpl implements MemberDAO {
    private SqlSessionFactory sqlSessionFactory;

    public MemberDAOImpl() {
        String resource = "com/kosa/showfan/sql/mybatis-config.xml";
//		String resource = "com/kosa/showfan/mybatis-config.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public MemberDTO selectById(String email, String pwd) throws FindException {
        SqlSession session = null;
        try {
            session = sqlSessionFactory.openSession();
            Map<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("pwd", pwd);
            MemberDTO m = session.selectOne("com.kosa.showfan.MemberMapper.selectById", map);

            if (m != null) {
                return m;
            } else {
                throw new FindException("회원이 없습니다");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


	@Override
	public void insertMember(MemberDTO m, List<Long> genreList) throws AddException {
		SqlSession session = null;
		try {
			if (sqlSessionFactory == null) {
	            // sqlSessionFactory가 null인 경우 초기화
				String resource = "com/kosa/showfan/sql/mybatis-config.xml";
	            InputStream inputStream = Resources.getResourceAsStream(resource);
	            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	        }
			
			//멤버테이블에 멤버 정보 넣기
			session = sqlSessionFactory.openSession();
			Map<String, Object> map = new HashMap<>();
			map.put("email", m.getMemberEmail());
			map.put("pwd", m.getMemberPwd());
			map.put("nickname", m.getMemberNickname());
			map.put("emailr", m.getMemberEmailAlert());
			session.insert("com.kosa.showfan.MemberMapper.insertMember", map);
			map.clear();
			
			//멤버 id값을 얻어오기 위한 작업
			map.put("email", m.getMemberEmail());
            map.put("pwd", m.getMemberPwd());
			MemberDTO m2 = session.selectOne("com.kosa.showfan.MemberMapper.selectById", map);
			map.clear();
			session.commit();
			
			//가져온 멤버 id를 바탕으로 내 선호 장르 테이블에 insert 해야한다
			for (int i = 0; i < genreList.size(); i++) {
			    Long genreId = genreList.get(i);
			    map.put("member_id", m2.getMemberId());
			    map.put("genre_id", genreId);
			    session.insert("com.kosa.showfan.MyGenreMapper.insertMyGenre", map);
			    map.clear();
			}
			session.commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.rollback();
			throw new AddException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		
	}

	@Override
	public String selectByNickName(String nickname) throws FindException {
		SqlSession session = null;
		try {
			if (sqlSessionFactory == null) {
	            // sqlSessionFactory가 null인 경우 초기화
				String resource = "com/kosa/showfan/sql/mybatis-config.xml";
	            InputStream inputStream = Resources.getResourceAsStream(resource);
	            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	        }
			session = sqlSessionFactory.openSession();
			String nick = session.selectOne("com.kosa.showfan.MemberMapper.selectByNickName", nickname);
			//중복된 닉네임이 있는경우
			if(nick != null) {
				return "0";
			//중복된 닉네임이 없는 경우
			}else {
				return "1";
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
	}
	
	@Override
	public void updateMember(MemberDTO m, List<Long> genreList) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Map<String, Object> map = new HashMap<>();
			
			//memberid를 얻어오기 위한 작업
			MemberDTO member = selectByEmail(m.getMemberEmail());
			
			member.setMemberPwd(m.getMemberPwd());
			member.setMemberNickname(m.getMemberNickname());
			member.setMemberEmailAlert(m.getMemberEmailAlert());
			
			System.out.println(member.getMemberId());
			System.out.println(member.getMemberPwd());
			System.out.println(member.getMemberNickname());
			System.out.println(member.getMemberEmailAlert());
			map.put("id", member.getMemberId());
			map.put("pwd", member.getMemberPwd());
			map.put("nickname", member.getMemberNickname());
			map.put("emailr", member.getMemberEmailAlert());
			session.update("com.kosa.showfan.MemberMapper.updateMember", map);
			session.commit();
			map.clear();
			
			session.delete("com.kosa.showfan.MyGenreMapper.deleteMyGenre", member.getMemberId());
			session.commit();
			//선호장르 테이블 업데이트
			for (int i = 0; i < genreList.size(); i++) {
			    Long genreId = genreList.get(i);
			    map.put("member_id", member.getMemberId());
			    map.put("genre_id", genreId);
			    session.insert("com.kosa.showfan.MyGenreMapper.insertMyGenre", map);
			    map.clear();
			}
			session.commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.rollback();
			throw new Exception("업데이트 하지 못했습니다");
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		
	}
    
    
    @Override
    public MemberDTO selectByEmail(String email) throws FindException {
        SqlSession session = null;
        try {
            session = sqlSessionFactory.openSession();
            MemberDTO member = session.selectOne("com.kosa.showfan.MemberMapper.selectByEmail", email);
            return member;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException("회원 검색에 실패했습니다");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }



}