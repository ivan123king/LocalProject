package com.lw.shiro.service;

import org.apache.shiro.authz.annotation.RequiresRoles;

/**
 * 权限注解测试类
 * @author lenovo
 *
 */
public class ShiroServce {

	/**
	 * 拥有user角色就可以访问此方法，否则抛出异常
	 * @param userName
	 * @return
	 */
	@RequiresRoles({"user"})
	public String getUserInfo(String userName){
		return null;
	}
}
