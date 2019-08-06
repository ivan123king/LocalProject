package com.lw.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.lw.user.bean.User;
import com.lw.user.dao.UserDao;
import com.lw.user.dao.mapper.UserMapper;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

	public User findUserById(String userId) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("user_id", userId);
		Map map = new HashMap();
		map.put("condition", condition);
		List<User> users = this.getUserMapper().findUserByCondition(map);
		if(users!=null&&users.size()>0){
			return users.get(0);
		}
		return null;
	}
	
	private UserMapper getUserMapper(){
		return this.getSqlSession().getMapper(UserMapper.class);
	}

}
