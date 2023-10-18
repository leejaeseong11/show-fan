package com.kosa.showfan.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kosa.showfan.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class CookieController extends HttpServlet implements Controller{
    private static final long serialVersionUID = 1L;


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //응답형식
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //응답출력스트림얻기
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        HttpSession session = request.getSession();


        //지정한 쿠키이름
        String logincookie = "loginCookie";

        Cookie[] cookies = request.getCookies();

        //1. 쿠키가 있는지 확인
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {

                //3. 있다면 우리쪽 쿠키인지 확인 그리고 맞다면 로그인 진행
                boolean ck = cookies[i].getName().equals(logincookie);

                if (ck == true) {
                    String cookieEmail = cookies[i].getValue();
                    
                    session.setAttribute("loginedEmail", cookieEmail);
                    
                    map.put("status", 1);
                    map.put("msg", "로그인 성공");
                    break;
                }
            }

            //2. 쿠키가 없다
        } else {
            map.put("status", 0);
            map.put("msg", "로그인 실패");
        }

        String jsonStr =gson.toJson(map);
        out.print(jsonStr);
		
	}

}
