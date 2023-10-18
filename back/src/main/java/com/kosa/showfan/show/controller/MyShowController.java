package com.kosa.showfan.show.controller;

import com.google.gson.Gson;
import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.exception.RemoveException;
import com.kosa.showfan.show.dto.MyShowDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MyShowController extends ShowController {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        if (request.getMethod().equals("GET")) {
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            List<MyShowDTO> myShowList;
            try {
                myShowList = service.selectMyShowByMemberId(Long.valueOf(request.getParameter("memberId")));
                String jsonResult = gson.toJson(myShowList);
                out.print(jsonResult);
            } catch (FindException e) {
                e.printStackTrace();
                response.setStatus(404);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(500);
            }
        } else if (request.getMethod().equals("POST")) {
            try {
                MyShowDTO myShowDTO = new MyShowDTO();
                myShowDTO.setMemberId(Long.valueOf(request.getParameter("memberId")));
                myShowDTO.setShowId(request.getParameter("showId"));
                service.insertMyShow(myShowDTO);
            } catch (AddException e) {
                e.printStackTrace();
                response.setStatus(404);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(500);
            }
        } else if (request.getMethod().equals("DELETE")) {
            try {
                MyShowDTO myShowDTO = new MyShowDTO();

                myShowDTO.setMemberId(Long.valueOf(request.getParameter("memberId")));
                myShowDTO.setShowId(request.getParameter("showId"));
                service.deleteMyShow(myShowDTO);
            } catch (RemoveException e) {
                e.printStackTrace();
                response.setStatus(404);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(500);
            }
        }
    }
}