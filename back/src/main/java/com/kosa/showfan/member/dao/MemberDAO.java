package com.kosa.showfan.member.dao;

import java.util.List;

import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.member.dto.MemberDTO;

public interface MemberDAO {

    /**
     * 아이디 비번에 해당하는 회원을 검색한다
     *
     * @param email 이메일
     * @param pwd   패스워드
     * @return 멤버객체
     * @throws FindException 이메일과 패스워드에 해당하는 고객이 없거나 DB와 연결 실패하면 예외발생
     */
    public MemberDTO selectById(String email, String pwd) throws FindException;
    
    /**
     * 회원가입시 입력한 정보로 회원가입을 한다, 선호장르 값은 선호장르 테이블로 저장
     * @param m 회원가입시 입력한 정보를 담은 객체
     * @return
     * @throws AddException
     */
    public void insertMember(MemberDTO m, List<Long> genreList) throws AddException;

    /**
     * 회원가입시 닉네임 입력후 중복 체크 버튼을 클릭했을때 해당 닉네임을 검색한다 
     * @param nickname
     * @throws FindException
     */
	public String selectByNickName(String nickname) throws FindException;
	
	public void updateMember(MemberDTO m, List<Long> genreList) throws Exception;

    public MemberDTO selectByEmail(String email) throws FindException;
}
