package com.kosa.showfan.review.controller;

import com.google.gson.Gson;
import com.kosa.showfan.review.dto.ReviewDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UpdateReviewController extends ReviewController {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json;charset=utf-8");

        Long reviewId = Long.parseLong(request.getParameter("reviewId"));
        String reviewContent = request.getParameter("reviewContent");
        Float reviewGrade = Float.parseFloat(request.getParameter("reviewGrade"));
        int seatId = Integer.parseInt(request.getParameter("seatId"));

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(reviewId);
        reviewDTO.setReviewContent(reviewContent);
        reviewDTO.setReviewGrade(reviewGrade);
        reviewDTO.setSeatId(seatId);

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();

        try {
            service.updateReview(reviewDTO);
            map.put("msg", "리뷰 수정 완료");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(404);
            map.put("msg", "리뷰 수정 실패");
        }
        out.print(gson.toJson(map));
    }
}
