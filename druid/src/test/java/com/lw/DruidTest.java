package com.lw;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lw.bean.DatabaseConn;
import com.lw.druid.DruidUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringbootApplication.class)
@Slf4j
public class DruidTest {

	
	@Test
	public void testDruidGetConnection() throws SQLException, Exception{
		DatabaseConn dbConn = new DatabaseConn();
		dbConn.setDatabase("Orcl");
		dbConn.setServer("192.168.90.29");
		dbConn.setType("Oracle");
		dbConn.setPassword("LLH2");
		dbConn.setUsername("LLH2");
		dbConn.setPort("1521");
		DataSource ds = DruidUtils.getDataSource(dbConn);
		Connection conn = ds.getConnection();
		System.out.println(conn);
	}
	
}
