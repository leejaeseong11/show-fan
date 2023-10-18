package com.kosa.showfan.reply.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.reply.dto.ReplyDTO;

public class InsertReplyController extends ReplyController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setContentType("application/json;charset=utf-8");
		
		Long reviewId = Long.parseLong(request.getParameter("reviewId"));
		Long memberId = Long.parseLong(request.getParameter("memberId"));
		String replyContent = request.getParameter("replyContent");

		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setReviewId(reviewId);
		replyDTO.setMemberId(memberId);
		replyDTO.setReplyContent(replyContent);

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<>();

		try {
			service.insertReply(replyDTO);
			map.put("msg", "댓글 작성 완료");
		} catch (AddException e) {
			e.printStackTrace();
			response.setStatus(500);
			map.put("msg", "댓글 작성 실패");
		}
		out.print(gson.toJson(map));
	}
}
