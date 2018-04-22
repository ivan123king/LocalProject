package com.lw.user.service;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lw.user.bean.User;

public class CustomerServiceTest{

	ApplicationContext context = null;
	@Before
	public void before(){
		System.out.println("in");
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void testRegister() {
	}

	@Test
	public void testLogin() {
		CustomerService service = context.getBean(CustomerService.class);
		User user = service.login("433", "111", null);
		System.out.println(user.getUserName());
		
	}

}
