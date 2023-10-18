package com.kosa.showfan.reply.dao;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.ModifyException;
import com.kosa.showfan.exception.RemoveException;
import com.kosa.showfan.reply.dto.ReplyDTO;

public class ReplyDAOImpl implements ReplyDAO {
	private static final ReplyDAO replyDAO = new ReplyDAOImpl();
	private SqlSessionFactory sqlSessionFactory;

	public static ReplyDAO getInstance() {
		return replyDAO;
	}

	public ReplyDAOImpl() {
		String resource = "com/kosa/showfan/sql/mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertReply(ReplyDTO reply) throws AddException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.insert("com.kosa.showfan.reply.ReplyMapper.insertReply", reply);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw new AddException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void updateReply(ReplyDTO reply) throws ModifyException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.update("com.kosa.showfan.reply.ReplyMapper.updateReply", reply);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public void deleteReply(Long replyId) throws RemoveException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.delete("com.kosa.showfan.reply.ReplyMapper.deleteReply", replyId);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
}
