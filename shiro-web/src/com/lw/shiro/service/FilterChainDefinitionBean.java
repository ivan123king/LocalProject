package com.lw.shiro.service;

import java.util.LinkedHashMap;

/**
 * URL权限控制构造
 * @author lenovo
 *
 */
public class FilterChainDefinitionBean {

	/**
	 * 此处就可以从数据库获取相应的页面权限配置，然后组成如下Map形式返回
	 * @return
	 */
	public LinkedHashMap<String, String> filterChainDefinitionBuilder(){
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("/login.jsp", "anon");
		map.put("/**", "authc");
		return map;
	}
}
