package com.kosa.showfan.review.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kosa.showfan.review.dto.ReviewDTO;
import com.kosa.showfan.review.dto.ReviewResponseDTO;

public class SelectReviewController extends ReviewController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		String showId = request.getParameter("showId");
		try {
			List<ReviewResponseDTO> result = service.getSelectByShowId(showId);
			Gson gson = new Gson();
			String jsonResult = gson.toJson(result);
			out.print(jsonResult);
			System.out.println("showId= " + showId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

