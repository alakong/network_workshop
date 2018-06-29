package com.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

	@RequestMapping(value="/upload.do", method = RequestMethod.POST)
	@ResponseBody
	public void addimpl(Upload file,HttpServletResponse response) {
	//public void addimpl( HttpServletResponse response, @RequestParam MultipartFile file0) {
		
		MultipartFile mf=file.getFile0();
		String name=mf.getOriginalFilename();
		//String name = file0.getOriginalFilename(); 그냥 file0으로만 파라미터를 받으면 file0으로 바로 업로드해도됨
		//vo객체를 만들어서 하는 방법으로 바꾼 것
		System.out.println(name);
		String result="";

		byte[] data = null;
		FileOutputStream fo = null;
		try {
			
			data = mf.getBytes();
			fo = new FileOutputStream("C:\\networks\\fileserver\\web\\file/" + name);
			fo.write(data);
			result="업로드 성공"; 

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		response.setCharacterEncoding("UTF-8");//꺼내오기 전에 이 셋팅을 위에다 해줘야해~~~
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(result);
		out.close();	
		
		
		
		
	}

}
