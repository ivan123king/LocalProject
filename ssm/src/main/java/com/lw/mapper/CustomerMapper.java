package com.lw.mapper;

import java.util.List;

import com.lw.entity.Customer;

public interface CustomerMapper {

public List<Customer> findCustomerWithName(String name);
	
	public void addNewCustomer(Customer customer);
	
	public void updateCustomerById(Customer customer);
	
	public void deleteByCustomerId(List<Integer> ids);
}
