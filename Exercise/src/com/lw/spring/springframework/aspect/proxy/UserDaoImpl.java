package com.lw.spring.springframework.aspect.proxy;

import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements UserDao{

	@Override
	public void addUser() throws Exception {
		System.out.println("Dao添加用户");
//		throw new Exception("自定义异常。");
	}

	@Override
	public void deleteUser() {
		System.out.println("Dao删除用户。");
	}

}
