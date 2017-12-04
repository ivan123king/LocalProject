package com.lw.spring.springframework.aspect.proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor{

	/**
	 * 创建代理对象
	 * @param target
	 * @return
	 */
	public Object createProxy(Object target){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		MyAspect myAspect = new MyAspect();
		myAspect.check_Permissions();
		Object obj = methodProxy.invokeSuper(proxy, args);
		myAspect.log();
		return obj;
	}

}
