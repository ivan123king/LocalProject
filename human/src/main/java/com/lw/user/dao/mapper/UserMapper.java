package com.lw.user.dao.mapper;

import java.util.List;
import java.util.Map;

import com.lw.user.bean.User;



public interface UserMapper {

	public void addNewUser(User user);
	
	public User findCustomerWithID(String ID);
	
	/**
	 * 只能用作简单查询，批量查询使用其他方法
	 * @param condition
	 * @return
	 */
	public List<User> findUserByCondition(Map condition);
}
