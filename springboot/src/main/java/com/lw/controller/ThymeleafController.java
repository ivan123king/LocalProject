package com.lw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/th")
public class ThymeleafController {
	
	@RequestMapping("/index")
	public String index(ModelMap map){
		map.addAttribute("name","king");
		return "thymeleaf/index";
	}
	
	@RequestMapping(value="/center")
	public String center(){
		return "thymeleaf/center/center";
	}
}
