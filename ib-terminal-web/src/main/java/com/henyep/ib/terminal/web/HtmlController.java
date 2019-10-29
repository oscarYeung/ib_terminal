package com.henyep.ib.terminal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {

	@RequestMapping(value="/mt4")
	public String getMt4(HttpServletRequest request,HttpServletResponse response){
		
		return "mt4";
	}
	
	@RequestMapping(value="/contactUs")
	public String getContactUs(HttpServletRequest request,HttpServletResponse response){
		
		return "contactUs";
	}
}
