package com.lw.spring.springframework.assemblage;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("userService")
//相当于xml中<bean id="userService" class="com.lw.spring.springframework.assemblage.UserServiceImpl"/>
public class UserServiceImpl implements UserService{

	@Resource(type=UserDaoImpl.class)
	//相当于xml中 <property name="userDao" ref="userDao"/>
	private UserDao userDao;
	
	@Override
	public void save() {
		userDao.save();
		System.out.println("userservice save function.");
	}

}
