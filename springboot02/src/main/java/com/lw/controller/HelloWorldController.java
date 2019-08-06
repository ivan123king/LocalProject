package com.lw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lw.entity.JdbcResource;

@RestController
public class HelloWorldController {

	@RequestMapping(value="/helloworld")
	public String helloworld(HttpServletRequest req){
		String name = req.getParameter("name");
		return "hello "+name;
	}
	
	
	@Autowired
	private JdbcResource jdbcResource;
	
	@RequestMapping(value="/jdbcinfo")
	public JdbcResource jdbcInfo(){
		JdbcResource jr = new JdbcResource();
		BeanUtils.copyProperties(jdbcResource, jr);
		return jr;
	}
	
	@RequestMapping(value="/error/divide")
	public void errorPage(){
		int i = 3/0;
	}
	
}
