package com.lw.designer.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler{

	private Object target = null;
	
	public TimeHandler(Object target) {
		super();
		this.target = target;
	}
	
	/**
	 * proxy 被代理对象
	 * method 被代理对象方法
	 * args 方法的参数
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("汽车开始行驶");
		method.invoke(target, args);
		System.out.println("汽车行驶结束");
		return null;
	}

}
