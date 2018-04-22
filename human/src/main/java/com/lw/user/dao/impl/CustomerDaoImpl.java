package com.lw.user.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.lw.user.bean.Customer;
import com.lw.user.bean.User;
import com.lw.user.dao.CustomerDao;
import com.lw.user.dao.mapper.CustomerMapper;
import com.lw.user.dao.mapper.UserMapper;


public class CustomerDaoImpl extends SqlSessionDaoSupport implements CustomerDao{

	public void addNewCustomer(Customer customer,Map map) {
		getCustomerMapper().addNewCustomer(customer);
	}
	
	public List<Customer> findCustomerWithID(String ID,Map map){
		return getCustomerMapper().findCustomerWithID(ID);
	}
	public void addNewUser(User user,Map map){
		getUserMapper().addNewUser(user);
	}
	
	private CustomerMapper getCustomerMapper(){
		return this.getSqlSession().getMapper(CustomerMapper.class);
	}
	private UserMapper getUserMapper(){
		return this.getSqlSession().getMapper(UserMapper.class);
	}

	public List<User> findUserByNameAndPassword(String name, String password,
			Map inMap) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("user_name", name);
		condition.put("password", password);
		Map map = new HashMap();
		map.put("condition", condition);
		return this.getUserMapper().findUserByCondition(map);
	}

}
