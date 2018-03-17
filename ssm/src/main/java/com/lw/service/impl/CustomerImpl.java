package com.lw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lw.dao.ICustomerDao;
import com.lw.entity.Customer;
import com.lw.service.ICustomerService;

@Service("customerService")
public class CustomerImpl implements ICustomerService{

	@Autowired
	public ICustomerDao dao;
	
	public void insert(Customer customer) throws Exception {
		dao.addNewCustomer(customer);
	}

	public List<Customer> findCustomerByName(String name) {
		return dao.findCustomerWithName(name);
	}

}
