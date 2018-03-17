package com.lw.dao;

import java.util.List;

import com.lw.entity.Customer;

public interface ICustomerDao {

	public List<Customer> findCustomerWithName(String name);
	
	public void addNewCustomer(Customer customer);
	
	public void updateCustomerById(Customer customer);
	
	public void deleteByCustomerId(List<Integer> ids);
}
