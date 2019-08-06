package com.lw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

	
	@RequestMapping(value="/errorpage")
	public String toErrorPage(HttpServletRequest req){
		req.setAttribute("reason", "你二大爷的");
		req.setAttribute("url", "ccc");
		return "error";
	}
}
