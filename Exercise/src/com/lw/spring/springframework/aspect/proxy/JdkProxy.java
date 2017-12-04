package com.lw.spring.springframework.aspect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy implements InvocationHandler{

	private UserDao userDao;
	
	/**
	 * 创建代理方法
	 * @param userDao
	 * @return
	 */
	public Object createProxy(UserDao userDao){
		this.userDao = userDao;
		//1.类加载器
		ClassLoader classLoader = JdkProxy.class.getClassLoader();
		//2. 被代理对象实现所有接口
		Class[] clazz = userDao.getClass().getInterfaces();
		//3. 使用代理类进行增加，返回被代理后的对象
		return Proxy.newProxyInstance(classLoader,clazz,this);
		
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		//声明切面
		MyAspect myAspect = new MyAspect();
		
		myAspect.check_Permissions();
		Object obj = method.invoke(userDao,args);
		myAspect.log();
		return obj;//这里返回null或者obj对测试方法testAspect没有影响
	}

}
