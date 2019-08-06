package com.lw.kettle;

public class DatabaseConn {

	/** 数据库服务器IP地址 */
	private String server;

	/** 数据库类型 */
	private String type;

	/** 访问类型（Native,ODBC,JNDI） */
	private String access = "Native";

	/** 数据库名称 */
	private String database;

	/** 连接端口 */
	private String port;

	/** 连接用户名 */
	private String username;

	/** 连接密码 */
	private String password;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
