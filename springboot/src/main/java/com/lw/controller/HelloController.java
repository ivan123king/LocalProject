package com.lw.controller;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lw.pojo.Resource;
import com.lw.pojo.User;

@RestController
public class HelloController {

	@RequestMapping(value="/")
	public User hello(){
		User user = new User();
		user.setName("king");
		user.setAge(220);
		user.setBirthday(new Date());
		user.setPassword("22313");
		return user;
	}

	@Autowired
	private Resource resource;
	
	@RequestMapping(value="/getResource")
	public Resource getResource(){
		Resource r = new Resource();
		BeanUtils.copyProperties(resource, r);
		return r;
	}
}
