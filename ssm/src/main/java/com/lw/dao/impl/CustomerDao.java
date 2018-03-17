package com.lw.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.lw.dao.ICustomerDao;
import com.lw.entity.Customer;
import com.lw.mapper.CustomerMapper;

public class CustomerDao extends SqlSessionDaoSupport implements ICustomerDao{
	
	public List<Customer> findCustomerWithName(String name) {
		CustomerMapper mapper = getMapper();
		List<Customer> customers = mapper.findCustomerWithName(name);
		return customers; 
	}

	public void addNewCustomer(Customer customer) {
		CustomerMapper mapper = getMapper();
		mapper.addNewCustomer(customer);
	}

	public void updateCustomerById(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	public void deleteByCustomerId(List<Integer> ids) {
		// TODO Auto-generated method stub
		
	}
	
	public CustomerMapper getMapper(){
		return this.getSqlSession().getMapper(CustomerMapper.class);
	}

}
