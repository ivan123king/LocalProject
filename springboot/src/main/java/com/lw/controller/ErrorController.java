package com.lw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/error")
public class ErrorController {

	@RequestMapping("/divide")
	public String errorPage(){
		int num = 1/0;
		return "thymeleaf/center";
	}
}
