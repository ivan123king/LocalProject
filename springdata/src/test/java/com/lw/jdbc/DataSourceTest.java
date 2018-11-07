package com.lw.jdbc;

import static org.junit.Assert.fail;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataSourceTest {

	private ApplicationContext ctx = null;
	
	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("beans.xml");
	}
	
	@After
	public void after() {
		if(ctx!=null) {
		}
	}
	
	@Test
	public void test() throws SQLException {
		PersonDao dao = (PersonDao) ctx.getBean("personDao");
		dao.query();
	}
	
	

}
