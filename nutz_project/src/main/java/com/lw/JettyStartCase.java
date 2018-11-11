package com.lw;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * jetty启动服务测试
 * @author lWX558056
 *
 */
public class JettyStartCase {

	public static void main(String[] args) throws Exception {
		//创建jetty web 容器
		Server server = new Server(8080);
		//退出程序就关闭服务
		server.setStopAtShutdown(true);
		
		Connector connector = new ServerConnector(server);
		
		//添加连接
		server.addConnector(connector);
		
		//配置服务
		WebAppContext context = new WebAppContext();
		context.setContextPath("/");//访问服务路径 http://ip:port/
		context.setConfigurationDiscovered(true);
		String baseDir = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		//指明服务描述文件就是web.xml
		context.setDescriptor(baseDir+File.separator+"WEB-INF/web.xml");
		//指定服务的资源根路径
		context.setResourceBase(System.getProperty("user.dir")+"/src/main/webapp/");
		server.setHandler(context);
		try {
			//启动服务
			server.start();
			server.join();
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
