package com.lw.designer.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogHandler implements InvocationHandler{

	private Object target = null;
	
	public LogHandler(Object target){
		super();
		this.target = target;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
//		System.out.println("日志开始记录");
		method.invoke(target, args);
//		System.out.println("日志记录完毕");
		return null;
	}

}
