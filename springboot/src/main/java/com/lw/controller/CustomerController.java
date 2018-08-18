package com.lw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.lw.mapper.CustomerMapper;
import com.lw.pojo.Customer;

@RestController
public class CustomerController {

	@Autowired
	private CustomerMapper customerMapper;
	
	@RequestMapping(value="/findcustomer/{customerId}")
	public Customer findCustomerById(@PathVariable("customerId") int customerId){
		Customer customer = customerMapper.findCustomerById(customerId);
		return customer;
	}
	
	@RequestMapping(value="/findallcustomer/{page}")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Customer> findAllCustomer(@PathVariable("page") int page){
		int pageSize = 3;
		//开始分页
		PageHelper.startPage(page, pageSize);
		
		List<Customer> customers = customerMapper.findAllCustomer();
		return customers;
	}
	
	
}
