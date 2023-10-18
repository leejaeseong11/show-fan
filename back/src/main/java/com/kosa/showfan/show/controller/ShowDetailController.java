package com.kosa.showfan.show.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kosa.showfan.show.dto.ShowAllInfoDTO;

public class ShowDetailController extends ShowController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setContentType("application/json;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		
		String showId = request.getParameter("showId");

		try {
			List<ShowAllInfoDTO> result = service.selectByShowId(showId);
			System.out.println(result);
			Gson gson = new Gson();
			String jsonResult = gson.toJson(result);
			out.print(jsonResult);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

}
