package com.kosa.showfan.reply.service;

import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.ModifyException;
import com.kosa.showfan.exception.RemoveException;
import com.kosa.showfan.reply.dao.ReplyDAO;
import com.kosa.showfan.reply.dao.ReplyDAOImpl;
import com.kosa.showfan.reply.dto.ReplyDTO;

public class ReplyService {
	private static final ReplyService service = new ReplyService();
	private ReplyDAO replyDAO = ReplyDAOImpl.getInstance();

	public static ReplyService getInstance() {
		return service;
	}

	public void insertReply(ReplyDTO replyDTO) throws AddException {
		replyDAO.insertReply(replyDTO);
	}

	public void updateReply(ReplyDTO replyDTO) {
		try {
			replyDAO.updateReply(replyDTO);
		} catch (ModifyException e) {
			e.printStackTrace();
		}
	}

	public void deleteReply(Long replyId) {
		try {
			replyDAO.deleteReply(replyId);
		} catch (RemoveException e) {
			e.printStackTrace();
		}
	}
}
