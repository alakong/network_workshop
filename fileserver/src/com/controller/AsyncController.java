package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AsyncController {
	
	  Logger logger = Logger.getLogger("work");
	   
	   

	   @RequestMapping("/main.do")
	   @ResponseBody
	   public String main(HttpServletRequest request) {
	      
	      String speed = request.getParameter("speed");
	      
	      logger.debug("speed: "+speed);  //로그에 찍자
	      
	      return "main";
	   }

}
