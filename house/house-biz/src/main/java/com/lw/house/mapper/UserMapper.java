package com.lw.house.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lw.house.common.model.User;

@Mapper
public interface UserMapper {

	public List<User> selectUsers();
	
	/**
	 * 
	 * @param account
	 * @return 表示插入个数
	 */
	public int insert(User account);
	
	public int delete(String email);
	
	public int update(User account);
	
	public List<User> selectUsersByQuery(User user);
}
