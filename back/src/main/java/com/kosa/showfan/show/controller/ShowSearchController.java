package com.kosa.showfan.show.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.show.dto.ShowListDTO;

public class ShowSearchController extends ShowController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json;charset=utf-8");
		
		PrintWriter out = response.getWriter();	
		String value = request.getParameter("q");
		String cpage = request.getParameter("p");
		
		int page = 1;
		if(cpage != null && !cpage.equals("")) {
			page = Integer.parseInt(cpage);
		}
		
		Gson gson = new Gson();
		ShowListDTO showList;
		
		try {
			showList = service.search(value, page);
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
