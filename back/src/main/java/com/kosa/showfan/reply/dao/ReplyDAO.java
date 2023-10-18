package com.kosa.showfan.reply.dao;

import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.ModifyException;
import com.kosa.showfan.exception.RemoveException;
import com.kosa.showfan.reply.dto.ReplyDTO;

public interface ReplyDAO {
	/**
	 * 답글 달기
	 * 
	 * @param reply
	 * @throws AddException
	 */
	void insertReply(ReplyDTO reply) throws AddException;

	/**
	 * 답글 수정
	 * 
	 * @param reply
	 * @throws ModifyException
	 */
	void updateReply(ReplyDTO reply) throws ModifyException;

	/**
	 * 답글 삭제
	 * 
	 * @param reply
	 * @throws RemoveException
	 */
	void deleteReply(Long replyId) throws RemoveException;
}
