package com.kosa.showfan.review.dao;

import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.exception.ModifyException;
import com.kosa.showfan.exception.RemoveException;
import com.kosa.showfan.review.dto.ReviewDTO;
import com.kosa.showfan.review.dto.ReviewResponseDTO;
import com.kosa.showfan.review.dto.ReviewShowResponseDTO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {
    private static final ReviewDAO reviewDAO = new ReviewDAOImpl();
    private SqlSessionFactory sqlSessionFactory;

    public static ReviewDAO getInstance() {
        return reviewDAO;
    }

    public ReviewDAOImpl() {
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
    public List<ReviewResponseDTO> selectByShowId(String showId) throws FindException {
        SqlSession session = sqlSessionFactory.openSession();
        List<ReviewResponseDTO> list = session.selectList("com.kosa.showfan.review.ReviewMapper.selectByShowId",
                showId);
        return list;
    }

    @Override
    public List<ReviewShowResponseDTO> selectByMemberId(Long memberId) throws FindException {
        SqlSession session = sqlSessionFactory.openSession();
        List<ReviewShowResponseDTO> list = session.selectList("com.kosa.showfan.review.ReviewMapper.selectByMemberId",
                memberId);
        return list;
    }

    @Override
    public void insertReview(ReviewDTO review) throws AddException {
        SqlSession session = null;
        try {
            session = sqlSessionFactory.openSession();
            session.insert("com.kosa.showfan.review.ReviewMapper.insertReview", review);
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
    public void updateReview(ReviewDTO review) throws ModifyException {
        SqlSession session = null;
        try {
            session = sqlSessionFactory.openSession();
            session.update("com.kosa.showfan.review.ReviewMapper.updateReview", review);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteReview(Long review) throws RemoveException {
        SqlSession session = null;
        try {
            session = sqlSessionFactory.openSession();
            session.delete("com.kosa.showfan.review.ReviewMapper.deleteReview", review);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public ReviewDTO selectReviewById(Long reviewId) throws FindException {
        SqlSession session = null;
        try {
            session = sqlSessionFactory.openSession();
            ReviewDTO res = session.selectOne("com.kosa.showfan.review.ReviewMapper.selectReviewById", reviewId);
            session.commit();
            return res;
        } catch (Exception e) {
            session.rollback();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
