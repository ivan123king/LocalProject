package com.lw.spring.springframework.jdbc;

public interface CustomerDao {

	public void operCustomer(String sql,Object[] params)throws Exception;
}
