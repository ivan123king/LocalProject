package com.lw.mapper;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import com.lw.pojo.Customer;

public interface CustomerMapper extends Mapper<Customer>,MySqlMapper<Customer>{

	public Customer findCustomerById(int customerId);
	
	public List<Customer> findAllCustomer();
}
