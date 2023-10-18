package com.kosa.showfan.review.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kosa.showfan.review.dto.ReviewDTO;

public class InsertReviewController extends ReviewController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setContentType("application/json;charset=utf-8");

		String showId = request.getParameter("showId");
		Long memberId = Long.parseLong(request.getParameter("memberId"));
		Float reviewGrade = Float.parseFloat(request.getParameter("reviewGrade"));
		String reviewContent = request.getParameter("reviewContent");
		Integer seatId = Integer.parseInt(request.getParameter("seatId"));

		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setShowId(showId);
		reviewDTO.setMemberId(memberId);
		reviewDTO.setReviewGrade(reviewGrade);
		reviewDTO.setReviewContent(reviewContent);
		reviewDTO.setSeatId(seatId);

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<>();

		try {
			service.insertReview(reviewDTO);
			map.put("msg", "리뷰 작성 완료");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			map.put("msg", "리뷰 작성 실패");
		}
		out.print(gson.toJson(map));
	}
}
