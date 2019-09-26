package com.lw.web;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.pool.DruidDataSource;
import com.lw.bean.DatabaseConn;
import com.lw.druid.DruidUtils;

/**
 * Druid测试web
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/druid")
@Slf4j
public class DruidController {
	
	private Map<String,DataSource> dsMap = new HashMap<>();
	
	@RequestMapping(value="/getConnection")
	public String getConnection(){
		DatabaseConn dbConn = new DatabaseConn();
		dbConn.setDatabase("Orcl");
		dbConn.setServer("192.168.90.29");
		dbConn.setType("Oracle");
		dbConn.setPassword("LLH2");
		dbConn.setUsername("LLH2");
		dbConn.setPort("1521");
		DataSource ds = null;
		try {
			if(dsMap.containsKey(dbConn.getUsername())){
				ds = dsMap.get(dbConn.getUsername());
			}else{
				ds = DruidUtils.getDataSource(dbConn);
				dsMap.put(dbConn.getUsername(), ds);
			}
			Connection conn = ds.getConnection();
			return conn.toString();
		} catch (Exception e) {
			log.error(e.getMessage());
//			dsMap.remove(dbConn.getUsername());

//			try {
//				Properties properties = new Properties();
//				properties.setProperty("user","LLH2");
//				properties.setProperty("password","LLH2");
//				String url = "jdbc:oracle:thin:@192.168.90.29:1521:Orcl";
//				Connection conn =  ((DruidDataSource)ds).createPhysicalConnection(url,properties);
//				return conn.toString();
//			} catch (SQLException e1) {
//				log.info("sql");
//			}

			return e.getMessage();
		} 
	}
}
