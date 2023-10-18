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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kosa.showfan.controller.Controller;
import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.member.dto.MemberDTO;
import com.kosa.showfan.member.service.MemberService;
import com.kosa.showfan.myGenre.dto.MyGenreDTO;


public class SignupController extends HttpServlet implements Controller {
	private static final long serialVersionUID = 1L;
	private MemberService service;
	
	public SignupController() {
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
        
        //MemberDTO
        String email = request.getParameter("email");
        String pwd = request.getParameter("pwd");
        String nickname = request.getParameter("nickname");
        String emailr = request.getParameter("emailr");
        
        //선호장르 얻어오기
        //MyGenreDTO
        String[] selectedGenres = request.getParameter("genre").split(",");
        List<Long> genreList = new ArrayList<>();
        if (selectedGenres != null) {
            for (String genre : selectedGenres) {
                genreList.add(Long.parseLong(genre));
            }
        }
        
//		Long memberId = Long.parseLong(request.getParameter("memberId"));


       
        
//        HttpSession session = request.getSession();
        MemberDTO m = new MemberDTO();
        MyGenreDTO mg = new MyGenreDTO();
        
        
        m.setMemberEmail(email);
        m.setMemberPwd(pwd);
        m.setMemberNickname(nickname);
        m.setMemberEmailAlert(emailr);
        
//        mg.setGenreId(genreList);
        
        
        try {
			service.signup(m, genreList);
			map.put("status", 1);
		} catch (AddException e) {
			// TODO Auto-generated catch block
			map.put("status", 0);
			e.printStackTrace();
		}
        
        String jsonStr = gson.toJson(map);
        out.print(jsonStr);
		
	}

}
