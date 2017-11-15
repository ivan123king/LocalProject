package com.lw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.lw.pojo.Customer;

public interface CustomerMapper {

	public List<Customer> getAllCustomer() throws Exception;
	public Customer findByCustomerId(int id)throws Exception;
}
