package com.lw.spring.springframework.simple;

/**
 * 构建bean的工厂类
 * @author lenovo
 *
 */
public class BeansFactory {
	
	public BeansFactory() {
//		System.out.println("beanfactory");
	}

	public UserService createService(){
		return new UserServiceImpl();
	}
	
	public static Object createBean(){
		return new UserServiceImpl();
	}
}
