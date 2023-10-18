package com.kosa.showfan.review.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.review.dto.ReviewResponseDTO;

public class DeleteReviewController extends ReviewController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setContentType("application/json;charset=utf-8");

		Long reviewId = Long.parseLong(request.getParameter("reviewId"));

		ReviewResponseDTO reviewDTO = new ReviewResponseDTO();
		reviewDTO.setReviewId(reviewId);

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<>();

		try {
			service.deleteReview(reviewId);
			map.put("msg", "리뷰 삭제 완료");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(404);
			map.put("msg", "리뷰 삭제 실패");
		}
		out.print(gson.toJson(map));
	}
}
