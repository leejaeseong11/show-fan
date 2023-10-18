package com.kosa.showfan.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kosa.showfan.controller.Controller;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.member.service.MemberService;

public class LoginController extends HttpServlet implements Controller {
    private static final long serialVersionUID = 1L;
    private MemberService service;

    public LoginController() {
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

        String email = request.getParameter("email");
        String pwd = request.getParameter("pwd");
        String auto = request.getParameter("autoLogin");

        HttpSession session = request.getSession();
        try {
            service.login(email, pwd);
            map.put("status", 1);
            map.put("msg", "로그인 성공");
            session.setAttribute("loginedEmail", email);
            session.setAttribute("loginedPwd", pwd);
            String loginEmail = (String) session.getAttribute("loginedEmail");

            if (auto.equals("true")) {   //자동로그인을 체크한 경우
               Cookie cookie = new Cookie("loginCookie", loginEmail);
               cookie.setMaxAge(60 * 60 * 24 * 7); // 단위는 (초)임으로 7일정도로 유효시간을 설정해 준다.
               cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
                
                // 쿠키 적용
               response.addCookie(cookie);
               
//               cookie.setHttpOnly(false);
//            	response.setHeader("Set-Cookie", "loginCookie=" + loginEmail + "; Max-Age=864000; Path=/; Secure; SameSite=None");
//				response.setHeader("Set-Cookie", "loginCookie=" + loginEmail + "; Max-Age=864000; Path=/; Secure=false; SameSite=None");

            } else { //자동로그인을 체크 안한 경우
            		Cookie cookie = new Cookie("loginCookie", loginEmail);
//				cookie.setMaxAge(60*60*24*7); // 자동로그인을 체크하지 않는 경우는 설정하지 않는다
            		cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
            	
            		// 쿠키 적용
            		response.addCookie(cookie);
            		
//                cookie.setHttpOnly(false);
//                cookie.setSecure(false);
             
//            	response.setHeader("Set-Cookie", "loginCookie=" + loginEmail + "; Path=/; Secure=false; SameSite=None");
            }
        } catch (FindException e) {
            // TODO Auto-generated catch block
            e.printStackTrace
                    ();
            map.put("status", 0);
            map.put("msg", "로그인 실패");
        }
        String jsonStr = gson.toJson(map);
        out.print(jsonStr);


    }

}