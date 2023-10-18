package com.kosa.showfan.review.dao;

import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.exception.ModifyException;
import com.kosa.showfan.exception.RemoveException;
import com.kosa.showfan.review.dto.ReviewDTO;
import com.kosa.showfan.review.dto.ReviewResponseDTO;
import com.kosa.showfan.review.dto.ReviewShowResponseDTO;

import java.util.List;

public interface ReviewDAO {
    /**
     * 공연번호에 해당하는 리뷰들을 반환한다
     *
     * @param showId 공연번호
     * @return 리뷰들
     * @throws FindException
     */
    List<ReviewResponseDTO> selectByShowId(String showId) throws FindException;

    /**
     * @param memberId
     * @return
     * @throws FindException
     */
    List<ReviewShowResponseDTO> selectByMemberId(Long memberId) throws FindException;

    /**
     * 리뷰 추가
     *
     * @param review
     * @throws AddException
     */
    void insertReview(ReviewDTO review) throws AddException;

    /**
     * 리뷰 업데이트
     *
     * @param review
     * @throws ModifyException
     */
    void updateReview(ReviewDTO review) throws ModifyException;

    /**
     * 리뷰 삭제
     *
     * @param reviewDTO
     * @throws RemoveException
     */
    void deleteReview(Long reviewId) throws RemoveException;

    ReviewDTO selectReviewById(Long reviewId) throws FindException;
}
