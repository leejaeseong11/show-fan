package com.kosa.showfan.review.controller;

import com.google.gson.Gson;
import com.kosa.showfan.review.dto.ReviewDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SelectReviewByIdController extends ReviewController {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json;charset=utf-8");

        PrintWriter out = response.getWriter();
        Long reviewId = Long.parseLong(request.getParameter("reviewId"));

        try {
            ReviewDTO result = service.selectReviewById(reviewId);
            Gson gson = new Gson();
            String jsonResult = gson.toJson(result);
            out.print(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
