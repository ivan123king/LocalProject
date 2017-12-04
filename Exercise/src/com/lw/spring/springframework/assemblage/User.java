package com.lw.spring.springframework.assemblage;

import java.util.List;

public class User {

	private String userName;
	private Integer password;
	private List<String> list;
	
	public User(){
		super();
		System.out.println("无参构造函数");
	}
	
	public User(String userName, Integer password, List<String> list) {
		super();
		this.userName = userName;
		this.password = password;
		this.list = list;
		System.out.println("有参构造函数");
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getPassword() {
		return password;
	}
	public void setPassword(Integer password) {
		this.password = password;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password
				+ ", list=" + list + "]";
	}
	
	
}
