package com.lw.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.house.common.model.User;
import com.lw.house.service.UserService;

@Controller
public class HelloController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="hello")
	public String hello(ModelMap modelMap){
		List<User> users = userService.getUsers();
		modelMap.put("user", users.get(0));
		return "hello";//freemarker模板的名称
	}
	
	@RequestMapping(value="index")
	public String index(){
		return "homepage/index";
	}
}
