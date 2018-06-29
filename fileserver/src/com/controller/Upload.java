package com.controller;

import org.springframework.web.multipart.MultipartFile;
//vo°´Ã¼
public class Upload {
	String name;
	MultipartFile file0;
	
	public Upload() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MultipartFile getFile0() {
		return file0;
	}
	public void setFile0(MultipartFile file0) {
		this.file0 = file0;
	}
	
	

}
