package com.lw.house.user.common;

/**
 * 响应实体
 * @author lenovo
 *
 * @param <T>
 */
public class RestResponse<T> {

	private int code;
	private String msg;
	
	private T result;
	
	
	
	public static <T> RestResponse<T> success(){
		RestResponse<T> response = new RestResponse<T>();
		return response;
	}
	
	public static <T> RestResponse<T> success(T result){
		RestResponse<T> response = new RestResponse<T>();
		response.setResult(result);
		return response;
	}
	
	public static <T> RestResponse<T> error(RestCode code){
		RestResponse<T> response = new RestResponse<T>(code.code,code.message);
		return response;
	}
	
	public RestResponse(int code,String message){
		this.code = code;
		this.msg = message;
	}
	
	public RestResponse(RestCode code){
		this.code = code.code;
		this.msg = code.message;
	}
	
	public RestResponse(){
		this(RestCode.OK);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
	
}
