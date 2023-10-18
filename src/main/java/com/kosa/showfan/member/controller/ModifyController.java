package com.kosa.showfan.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kosa.showfan.controller.Controller;
import com.kosa.showfan.member.dto.MemberDTO;
import com.kosa.showfan.member.service.MemberService;

//modify
public class ModifyController extends HttpServlet implements Controller{
	private static final long serialVersionUID = 1L;
	private MemberService service;
	
	public ModifyController() {
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
        
        //프론트에서 쿠키의 이메일값을 얻어와 보낸 값(아이디를 얻기 위함)
        String email = request.getParameter("email");
        
        //변경할값
        String pwd = request.getParameter("pwd");
        String nickname = request.getParameter("nickname");
        String emailr = request.getParameter("emailr");
        
        //선호장르 얻어오기
        String[] selectedGenres = request.getParameter("genre").split(",");
        List<Long> genreList = new ArrayList<>();
        if (selectedGenres != null) {
            for (String genre : selectedGenres) {
                genreList.add(Long.parseLong(genre));
            }
        }
        MemberDTO m = new MemberDTO();
        m.setMemberEmail(email);
        m.setMemberPwd(pwd);
        m.setMemberNickname(nickname);
        m.setMemberEmailAlert(emailr);
        
        
        
        try {
	        	service.modify(m, genreList);
	        	map.put("status", 1);        	
        } catch(Exception e ) {
        		map.put("status", 0);
        		e.printStackTrace();
        }
        String jsonStr = gson.toJson(map);
        out.print(jsonStr);
	}

}
