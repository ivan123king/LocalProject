package com.lw.spring.springframework.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CustomerDaoImpl implements CustomerDao{

	private JdbcTemplate jt;
	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}
	@Transactional(propagation=Propagation.REQUIRED,
			isolation=Isolation.DEFAULT,readOnly=false)
	@Override
	public void operCustomer(String sql,Object[] params) throws Exception {
		jt.update(sql,params);
		int i = 1/0;
		jt.update(sql,params);
	}

}
