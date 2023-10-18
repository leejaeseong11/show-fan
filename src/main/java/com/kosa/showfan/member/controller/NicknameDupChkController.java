package com.kosa.showfan.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kosa.showfan.controller.Controller;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.member.service.MemberService;


//nicknamedupchk
public class NicknameDupChkController extends HttpServlet implements Controller {
	private static final long serialVersionUID = 1L;
	private MemberService service;
	public NicknameDupChkController() {
		service = new MemberService();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//응답형식
		response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        //응답출력스트림얻기
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        
        Map<String, Object> map = new HashMap<>();
        
        String nickname = request.getParameter("nickname");
        
        try {
        		String num = service.nickNameDupChk(nickname);
        		if(num.equals("1")) {
        			System.out.println("닉네임 사용 가능!");
        			map.put("status", 1);        			
        		}else if(num.equals("0")) {
        			System.out.println("닉네임 사용 불가!");
        			map.put("status", 0);
        		}
		} catch (Exception e) {
			e.printStackTrace();
		}
        String jsonStr = gson.toJson(map);
        out.print(jsonStr);
		
	}

}
