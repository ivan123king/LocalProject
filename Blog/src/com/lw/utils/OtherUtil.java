package com.lw.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class OtherUtil {

	public static boolean isNotEmpty(List list) {
		if (list != null && list.size() > 0)
			return true;
		return false;
	}

	public static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str))
			return true;
		return false;
	}

	public static String cUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	private static SessionFactory sessionFactory = null;
	public static Session createSession(){
		Session session = null;
		if(sessionFactory==null||sessionFactory.isClosed()){
			Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
								.applySettings(configuration.getProperties())
								.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		session = sessionFactory.openSession();
		return session;
	}
	

	public static final String proPath = "/com/lw/utils/character.properties";

	/**
	 * 特殊字符转义
	 * 同方法： StringEscapeUtils.escapeXml11 以前不知道有这方法，这就是apache转义方法
	 * @param inStr
	 * @return
	 */
	public static String changeCharacter(String inStr) {
		if(isNotEmpty(inStr)){
			Properties pro = new Properties();
			try {
				InputStream is = OtherUtil.class.getResourceAsStream(proPath);
				pro.load(is);
				Iterator<String> iter = pro.stringPropertyNames().iterator();
				while(iter.hasNext()){
					String key = iter.next();
					System.out.println(key+"="+pro.getProperty(key));
					if(inStr.contains(key)){
						inStr = inStr.replaceAll(key, pro.getProperty(key));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return inStr;
	}

	public static void main(String[] args) {
//		OtherUtil.cUUID();
		String inStr = "<body>itee</body>";
		String outStr = OtherUtil.changeCharacter(inStr);
		System.out.println(outStr);
		System.out.println(inStr.contains(">"));
		
//		 String userdir = System.getProperty("user.dir");  
//		 System.out.println(userdir);
//		String path = OtherUtil.class.getResource("").getPath();
//		System.out.println(path);
		
	}
}
