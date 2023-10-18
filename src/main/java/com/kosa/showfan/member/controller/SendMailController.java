package com.kosa.showfan.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kosa.showfan.controller.Controller;

//sendmail
public class SendMailController extends HttpServlet implements Controller {
	private static final long serialVersionUID = 1L;


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//응답형식
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //응답출력스트림얻기
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        
        
        Map<String, Object> map = new HashMap<>();
        
        
        String mail = request.getParameter("email");
		String mailvalue = request.getParameter("emailvalue");
        
		
		Random random = new Random();
		int codeInt = 100000 + random.nextInt(900000); // 100000부터 999999까지의 랜덤 숫자 생성

        //6자리 랜던 숫자 저장 (메일로 보낼 코드값)
        String code = Integer.toString(codeInt); 
 
        // 1. 발신자의 메일 계정과 비밀번호 설정
        final String user = "showfan777@gmail.com";
//        final String password = "showfan789~";
        //앱 비밀번호(지메일)
        final String password = "juat tqtn gxtg kcnu";
 
        // 2. Property에 SMTP 서버 정보 설정
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

		
        // 3. SMTP 서버정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스 생성
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
 
 
        // 4. Message 클래스의 객체를 사용하여 수신자와 내용, 제목의 메시지를 작성한다.
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(user));
            message.setRecipients(
                    Message.RecipientType.TO, 
                    InternetAddress.parse(mail)
                );
 
            // 수신자 메일 주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
 
            // Subject
            message.setSubject("ShowFan 이메일 인증번호 입니다");
 
            // Text
            message.setText("이메일 인증을 위한 코드 번호 : ["+code+"]");
 

            map.put("status", 1);
            map.put("msg", code);
            Transport.send(message);    // send message

        } catch (AddressException e) {
        		map.put("status", 0);
        		map.put("msg", "이메일 전송 실패");
            e.printStackTrace();
        } catch (MessagingException e) {
        		map.put("status", 0);
        		map.put("msg", "이메일 전송 실패");
            e.printStackTrace();
        }
        String jsonStr = gson.toJson(map);
        out.print(jsonStr);
        
		
	}

}
