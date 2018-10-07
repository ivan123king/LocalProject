package com.lw.house.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lw.house.common.model.User;
import com.lw.house.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public List<User> getUsers(){
		return userMapper.selectUsers();
	}
}
