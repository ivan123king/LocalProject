package com.lw.designer.proxy.cglib_proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor{

	
	private Enhancer enhancer = new Enhancer();
	public Object getProxy(Class clazz){
		enhancer.setSuperclass(clazz);//设置将要动态创建子类的父类，父类是clazz
		//设置将要动态创建的子类的拦截器
		enhancer.setCallback(this);
		
		return enhancer.create();//动态创建子类，返回此子类实例
	}
	
	/**
	 * 拦截所有目标类方法的调用
	 * obj 目标类的实例
	 * m 目标方法的反射对象
	 * args2 方法参数
	 * proxy  代理类实例
	 */
	@Override
	public Object intercept(Object obj, Method m, Object[] arg2,
			MethodProxy proxy) throws Throwable {
		proxy.invokeSuper(obj, arg2);//调用父类方法 ，因为在enhancer中动态创建了子类
		return null;
	}

}
