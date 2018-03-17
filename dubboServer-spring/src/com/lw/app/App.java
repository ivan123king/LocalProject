package com.lw.app;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javassist.ClassPath;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.lw.dubbo.IUserService;
import com.lw.service.impl.UserServiceImpl;

public class App {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-Server.xml");
		context.start();
		System.out.println("服务端启动成功");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
