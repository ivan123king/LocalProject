package com.lw.user.service;

import java.util.Map;

import com.lw.user.bean.Customer;
import com.lw.user.bean.User;



public interface CustomerService {
	
	/**
	 * 注册
	 */
	public int register(Customer customer,Map map);
	
	/**
	 * 登录
	 * @param name
	 * @param password
	 * @param map
	 * @return
	 */
	public User login(String name,String password,Map map);
	
}
