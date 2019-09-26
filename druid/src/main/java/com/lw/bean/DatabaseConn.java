package com.lw.bean;

import lombok.Data;

/**
 * 数据库连接
 * 
 * @author Administrator
 *
 */
@Data
public class DatabaseConn {
	private String server;
	private String type;
	private String access = "Native"; 
	private String database;
	private String port;
	private String username;
	private String password;
}
