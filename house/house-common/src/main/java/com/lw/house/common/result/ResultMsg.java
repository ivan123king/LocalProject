package com.lw.house.common.result;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.UrlEncoded;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

/**
 * 返回消息的类
 * @author lenovo
 *
 */
public class ResultMsg {

	private static final String errorMsgKey = "errorMsg";
	private static final String successMsgKey = "successMsg";
	
	private String errorMsg;
	private String successMsg;
	
	/**
	 * 判断是否是成功信息
	 * @return
	 */
	public boolean isSuccess(){
		return StringUtils.isBlank(errorMsg);
	}
	
	/**
	 * 将返回信息变成Url拼接的格式
	 * @return
	 */
	public String asUrlParams(){
		Map<String,String> map = asMap();
		Map<String,String> newMap = Maps.newHashMap();
		map.forEach((k,v)->{
			if(v!=null) newMap.put(k, UrlEncoded.encodeString(v,Charsets.UTF_8));
		});
		//将每个map中的key,value一组使用&连接，如果value为空使用""替换，同时key,value之间使用=拼接
		/*
		 * 格式就变成 key00=value00&key01=value01&key02=""&key03=value03.....
		 */
		return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
	}
	
	public static ResultMsg errorMsg(String msg){
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setErrorMsg(msg);
		return resultMsg;
	}
	
	public static ResultMsg successMsg(String msg){
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setSuccessMsg(msg);
		return resultMsg;
	}
	
	public Map<String,String> asMap(){
		Map<String,String> map = Maps.newHashMap();
		map.put(successMsgKey, successMsg);
		map.put(errorMsgKey, errorMsg);
		return map;
	}
	
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getSuccessMsg() {
		return successMsg;
	}
	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	public static String getErrormsgkey() {
		return errorMsgKey;
	}
	public static String getSuccessmsgkey() {
		return successMsgKey;
	}
}
