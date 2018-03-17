package com.lw.app;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.lw.dubbo.IUserService;

public class App {

	public void hello(){
		//配置服务名称
				ApplicationConfig application = new ApplicationConfig("sayHello");
				//注册服务
				RegistryConfig registry = new RegistryConfig();
				registry.setAddress("zookeeper://192.168.0.101:2181");//注册中心地址
				
				//引用服务
				ReferenceConfig<IUserService> reference = new ReferenceConfig<IUserService>();
				reference.setApplication(application);
				reference.setRegistry(registry);
				reference.setInterface("com.lw.dubbo.IUserService");
				
				IUserService userService = reference.get();
				userService.sayHello("king");
				
	}
	
	public static void main(String[] args) {
		new App().hello();
	}
}
