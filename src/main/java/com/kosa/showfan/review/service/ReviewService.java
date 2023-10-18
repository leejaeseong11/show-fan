package com.kosa.showfan.review.service;

import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.exception.ModifyException;
import com.kosa.showfan.exception.RemoveException;
import com.kosa.showfan.review.dao.ReviewDAO;
import com.kosa.showfan.review.dao.ReviewDAOImpl;
import com.kosa.showfan.review.dto.ReviewDTO;
import com.kosa.showfan.review.dto.ReviewResponseDTO;
import com.kosa.showfan.review.dto.ReviewShowResponseDTO;

import java.util.List;

public class ReviewService {
    private static final ReviewService service = new ReviewService();
    private ReviewDAO reviewDAO = ReviewDAOImpl.getInstance();

    public static ReviewService getInstance() {
        return service;
    }

    public List<ReviewResponseDTO> getSelectByShowId(String showId) throws FindException {
        return reviewDAO.selectByShowId(showId);
    }

    public List<ReviewShowResponseDTO> getSelectByMemberId(Long memberId) throws FindException {
        return reviewDAO.selectByMemberId(memberId);
    }

    public void insertReview(ReviewDTO reviewDTO) throws AddException {
        reviewDAO.insertReview(reviewDTO);
    }

    public void updateReview(ReviewDTO reviewDTO) {
        try {
            reviewDAO.updateReview(reviewDTO);
        } catch (ModifyException e) {
            e.printStackTrace();
        }
    }

    public void deleteReview(Long reviewId) {
        try {
            reviewDAO.deleteReview(reviewId);
        } catch (RemoveException e) {
            e.printStackTrace();
        }
    }

    public ReviewDTO selectReviewById(Long reviewId) throws FindException {
        return reviewDAO.selectReviewById(reviewId);
    }
}
