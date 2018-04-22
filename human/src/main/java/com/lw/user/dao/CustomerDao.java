package com.lw.user.dao;

import java.util.List;
import java.util.Map;

import com.lw.user.bean.Customer;
import com.lw.user.bean.User;



public interface CustomerDao {

	/**
	 * 添加新客户
	 * @param customer
	 * @param map
	 */
	public void addNewCustomer(Customer customer,Map map);
	/**
	 * 添加新用户
	 * @param user
	 * @param map
	 */
	public void addNewUser(User user,Map map);
	public List<Customer> findCustomerWithID(String ID,Map map);
	public List<User> findUserByNameAndPassword(String name,String password,Map inMap);
}
