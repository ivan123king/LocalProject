package com.lw.user.dao;

import com.lw.user.bean.User;

public interface UserDao {

	public User findUserById(String userId);
}
