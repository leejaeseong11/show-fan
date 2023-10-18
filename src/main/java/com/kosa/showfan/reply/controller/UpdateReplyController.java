package com.kosa.showfan.reply.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kosa.showfan.reply.dto.ReplyDTO;

public class UpdateReplyController extends ReplyController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setContentType("application/json;charset=utf-8");

		Long replyId = Long.parseLong(request.getParameter("replyId"));
		String replyContent = request.getParameter("replyContent");

		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setReplyId(replyId);
		replyDTO.setReplyContent(replyContent);

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<>();

		try {
			service.updateReply(replyDTO);
			map.put("msg", "댓글 수정 완료");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(404);
			map.put("msg", "댓글 수정 실패");
		}
		out.print(gson.toJson(map));
	}
}
