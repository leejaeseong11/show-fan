package com.kosa.showfan.member.controller;

import com.google.gson.Gson;
import com.kosa.showfan.controller.Controller;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.member.dto.MemberDTO;
import com.kosa.showfan.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MemberController implements Controller {
    protected MemberService service;

    public MemberController() {
        service = MemberService.getInstance();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        try {
            MemberDTO member = service.select(request.getParameter("email"));
            out.print(gson.toJson(member));
        } catch (FindException e) {
            e.printStackTrace();
            response.setStatus(404);
		    } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
		    }
    }
}
