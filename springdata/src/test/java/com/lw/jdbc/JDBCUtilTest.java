package com.lw.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class JDBCUtilTest {

	@Test
	public void testGetConnection() {
		try {
			Connection conn = JDBCUtil.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

}
