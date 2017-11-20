package com.lw.mapper;

import java.util.List;
import java.util.Map;

import com.lw.pojo.Customer;
import com.lw.pojo.Customer02;

public interface CustomerMapper {

	public List<Customer> getAllCustomer() throws Exception;
	public Customer findByCustomerId(int id)throws Exception;
	
	public Customer02 findByCustomer02Id(int id) throws Exception;
	
	public Customer02 findCustomerWithItem(int id)throws Exception;
	
	public void addNewCustomer(Customer c)throws Exception;
	
	public void updateCustomerById(Customer c)throws Exception;
	
	public void deleteByCondition(Map inMap) throws Exception;
	
	public void deleteByCustomerId(List inList) throws Exception;
}
