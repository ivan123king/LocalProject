package com.lw.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		properties.forEach((key,value)->{
			System.out.println(key+":"+value);
		});
		
		Connection conn = null;
		Class.forName(properties.getProperty("jdbc.driver","com.mysql.jdbc.Driver"));
		String url = properties.getProperty("jdbc.url");
		String user = "root";
		String password = "root";
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
}
