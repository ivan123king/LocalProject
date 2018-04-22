package com.lw.user.dao.mapper;

import java.util.List;

import com.lw.user.bean.Customer;



public interface CustomerMapper {

	public void addNewCustomer(Customer customer);
	
	public List<Customer> findCustomerWithID(String ID);
}
