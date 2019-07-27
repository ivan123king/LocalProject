package com.lw.house.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lw.house.api.dao.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public String getUserName(Long id){
		return userDao.getUserName(id);
	}
}
