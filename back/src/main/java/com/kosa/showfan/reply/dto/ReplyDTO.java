package com.kosa.showfan.reply.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ReplyDTO {
	private Long replyId;
	private Long reviewId;
	private Long memberId;
	private String replyContent;
	private Date replyCreatedAt;
	
	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
}
