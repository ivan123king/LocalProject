package com.lw.spring.springframework.aspect.proxy;
/**
 * 切面类
 * @author lenovo
 *
 */
public class MyAspect {
	public void check_Permissions(){
		System.out.println("模拟检查权限....");
	}
	public void log(){
		System.out.println("模拟日志增加....");
	}
}
