package com.lw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.pojo.Resource;

@Controller
@RequestMapping(value="/ftl")
public class FreeMarkerController {

	@Autowired
	private Resource resource;
	
	@RequestMapping(value="/index")
	public String index(ModelMap model){
		model.addAttribute(resource);
		return "freemarker/index";
	}
	
	@RequestMapping(value="/center")
	public String center(){
		return "freemarker/center/center";
	}
}
