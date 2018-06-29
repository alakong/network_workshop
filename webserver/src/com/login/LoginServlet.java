package com.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/LoginServlet", "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 들어올 때는 request 나갈 때는 response
		
		//받을 때의 한글도 인코딩한다.
		response.setCharacterEncoding("UTF-8");//꺼내오기 전에 이 셋팅을 위에다 해줘야해~~~

		//request.setCharacterEncoding("UTF-8");
		// 인코딩하는 다른 방법 name=new String(name.getBytes("8859_1"),"UTF-8");

		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name= request.getParameter("name");
		
		System.out.println(name);
		
		
		String result="";
		if(id.equals("qq")&&pwd.equals("11")) {
			result="로그인 성공"; 
		}else {
			result="로그인 실패";
		}
		//euc-kr은 한국어만 쓸때/utf-8은 다국어
		PrintWriter out= response.getWriter();
		out.print(result);
		out.close();	
	}
}
