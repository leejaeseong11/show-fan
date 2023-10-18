package com.kosa.showfan.artist.controller;

import com.google.gson.Gson;
import com.kosa.showfan.artist.dto.MyArtistDTO;
import com.kosa.showfan.artist.service.MyArtistService;
import com.kosa.showfan.controller.Controller;
import com.kosa.showfan.exception.FindException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MyArtistController implements Controller {
    protected MyArtistService service;

    public MyArtistController() {
        service = MyArtistService.getInstance();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        
        if (request.getMethod().equals("GET")) {
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            List<MyArtistDTO> myArtistList;
            try {
                myArtistList = service.selectById(Long.valueOf(request.getParameter("memberId")));
                String jsonResult = gson.toJson(myArtistList);
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

}
