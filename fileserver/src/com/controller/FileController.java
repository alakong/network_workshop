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
		//String name = file0.getOriginalFilename(); �׳� file0���θ� �Ķ���͸� ������ file0���� �ٷ� ���ε��ص���
		//vo��ü�� ���� �ϴ� ������� �ٲ� ��
		System.out.println(name);
		String result="";

		byte[] data = null;
		FileOutputStream fo = null;
		try {
			
			data = mf.getBytes();
			fo = new FileOutputStream("C:\\networks\\fileserver\\web\\file/" + name);
			fo.write(data);
			result="���ε� ����"; 

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		response.setCharacterEncoding("UTF-8");//�������� ���� �� ������ ������ �������~~~
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
