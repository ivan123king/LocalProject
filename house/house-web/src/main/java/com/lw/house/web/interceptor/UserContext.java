package com.lw.house.web.interceptor;

import com.lw.house.common.model.User;

/**
 * User对象的上下文环境
 * 用于在线程中保存用户对象
 * @author lenovo
 *
 */
public class UserContext {

	private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();
	
	public static void setUser(User user){
		USER_HOLDER.set(user);
	}
	
	public static void remove(){
		USER_HOLDER.remove();
	}
	
	public static User getUser(){
		return USER_HOLDER.get();
	}
}
