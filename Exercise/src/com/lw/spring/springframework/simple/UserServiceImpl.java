package com.lw.spring.springframework.simple;

public class UserServiceImpl implements UserService {
	//由spring容器反射实例化userDao对象
	private UserDao userDao = null;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public void say() {
		userDao.say();
	}

}
