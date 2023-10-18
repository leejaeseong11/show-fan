package com.kosa.showfan.reply.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ReplyResponseDTO {
	private Long replyId;
	private Long reviewId;
	private String memberNickname;
	private String replyContent;
	private Date replyCreatedAt;
}
