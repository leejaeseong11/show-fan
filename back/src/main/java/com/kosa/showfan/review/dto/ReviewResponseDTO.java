package com.kosa.showfan.review.dto;

import java.util.Date;
import java.util.List;

import com.kosa.showfan.reply.dto.ReplyResponseDTO;

import lombok.Data;

@Data
public class ReviewResponseDTO {
	private Long reviewId;
	private String showId;
	private String memberImage;
	private String memberNickname;
	private Float reviewGrade;
	private String reviewContent;
	private Date reviewCreatedAt;
	private String reviewIsModified;
	private String seatName;
	private Integer seatPrice;
	private List<ReplyResponseDTO> reviewReply;
	
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
}
