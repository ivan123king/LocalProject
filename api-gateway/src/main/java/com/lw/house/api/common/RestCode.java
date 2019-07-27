package com.lw.house.api.common;

/**
 * 响应码
 * @author lenovo
 *
 */
public enum RestCode {

	OK(0,"OK"),
	UNKNOW_ERROR(1,"服务异常"),
	WRONG_PAGE(10100,"页码不存在");
	
	public final int code;
	public final String message;
	
	private RestCode(int code,String message){
		this.code = code;
		this.message = message;
	}
}
