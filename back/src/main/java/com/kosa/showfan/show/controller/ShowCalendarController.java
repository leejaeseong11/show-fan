package com.kosa.showfan.show.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.show.dto.ShowListDTO;

public class ShowCalendarController extends ShowController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json;charset=utf-8");
		
		PrintWriter out = response.getWriter();	
		String year = request.getParameter("y");
		String month = request.getParameter("m");
				
		Gson gson = new Gson();
		ShowListDTO showList;
		
		try {
			showList = service.calendar(year + month);
			String jsonResult = gson.toJson(showList);
			out.print(jsonResult);
		} catch (FindException e) {
			e.printStackTrace();
			response.setStatus(404);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
		}
	}

}
