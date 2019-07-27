package com.lw.house.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ConfigurationProperties注解的作用见DruidConfig类
 * @author lenovo
 *
 */
@ConfigurationProperties(prefix="spring.httpclient")
public class HttpClientProperties {
	
	private Integer connectTimeOut = 1000;
	private Integer socketTimeOut = 10*1000;

	private String agent = "agent";
	//每个线程最大连接数
	private Integer maxConnPerRoute = 10;
	//总的连接数
	private Integer maxConnTotal = 50;
	public Integer getConnectTimeOut() {
		return connectTimeOut;
	}
	public void setConnectTimeOut(Integer connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	public Integer getSocketTimeOut() {
		return socketTimeOut;
	}
	public void setSocketTimeOut(Integer socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public Integer getMaxConnPerRoute() {
		return maxConnPerRoute;
	}
	public void setMaxConnPerRoute(Integer maxConnPerRoute) {
		this.maxConnPerRoute = maxConnPerRoute;
	}
	public Integer getMaxConnTotal() {
		return maxConnTotal;
	}
	public void setMaxConnTotal(Integer maxConnTotal) {
		this.maxConnTotal = maxConnTotal;
	}
}
