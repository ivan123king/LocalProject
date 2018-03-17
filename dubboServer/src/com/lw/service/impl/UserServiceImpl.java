package com.lw.service.impl;

import com.lw.dubbo.IUserService;

public class UserServiceImpl implements IUserService{

	@Override
	public void sayHello(String name) {
		System.out.println("hello "+name);
	}

}
