package com.lw.service;

import java.util.List;

import com.lw.entity.Customer;

public interface ICustomerService {

	public void insert(Customer customer)throws Exception;
	
	public List<Customer> findCustomerByName(String name);
}
