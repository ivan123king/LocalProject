package com.lw.app;

import java.io.IOException;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.lw.dubbo.IUserService;
import com.lw.service.impl.UserServiceImpl;

public class App {

	IUserService userService = new UserServiceImpl();
	
	public void hello(){
		//配置服务名称
		ApplicationConfig application = new ApplicationConfig("sayHello");
		//注册服务
		RegistryConfig registry = new RegistryConfig();
		/*
		 * 使用multicast是一个224开头的地址，此地址随便写，是广播地址，但无法做成集群 multicast://224.5.6.7:1234
		 * 使用zookeeper： zookeeper://IP:port  IP是zookeeper启动的地址，port是zookeeper配置的端口
		 */
		registry.setAddress("zookeeper://192.168.0.101:2181");//注册中心地址
//		registry.setRegister(false);//不把服务注册到注册中心
		//配置服务协议信息
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setName("dubbo");
		protocol.setPort(22800);
		
		//配置服务相关联的类
		/*
		 * 下面的类是dubboApi项目中的接口
		 */
		ServiceConfig<IUserService> service = new ServiceConfig<IUserService>();
		service.setInterface("com.lw.dubbo.IUserService");
		service.setRef(userService);
		service.setApplication(application);
		service.setRegistry(registry);
		service.setProtocol(protocol);
		
		//发布服务
		service.export();
		System.out.println("服务端启动成功");
		try {
			//这里是为了服务端在运行到此地是阻塞住，这样客户端才能调用到，否则服务端运行完了客户端还怎么找到服务
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new App().hello();
	}
}
