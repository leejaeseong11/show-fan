package com.kosa.showfan.review.dto;

import lombok.Data;

@Data
public class ReviewDTO {
	private Long reviewId;
	private Long memberId;
	private String showId;
	private Float reviewGrade;
	private String reviewContent;
	private Integer seatId;

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public void setShowId(String showId) {
		this.showId = showId;
	}
	public void setReviewGrade(Float reviewGrade) {
		this.reviewGrade = reviewGrade;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	public void setSeatId(Integer seatId) {
		this.seatId = seatId;
	}
}