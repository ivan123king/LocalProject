package com.lw.spring.springframework.assemblage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

@Controller("userController")
/*
 * 相当于xml中<bean id="userController" class="com.lw.spring.springframework.assemblage.UserController"/>
 */
public class UserController {
	
	@Resource(name="userService")
	//相当于xml中<property name="userService" ref="userService"/>
	private UserService userService;
	public void save(){
		userService.save();
		System.out.println("userController save function.");
	}
}
