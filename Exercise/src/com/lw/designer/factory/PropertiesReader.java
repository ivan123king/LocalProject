package com.lw.designer.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesReader {

	public static void main(String[] args) throws IOException {
		//PropertiesReader.class 的值为  class com.lw.designer.factory.PropertiesReader
		InputStream is = PropertiesReader.class
				.getResourceAsStream("../../hairtype.properties");
//		InputStream is = PropertiesReader.class
//				.getResourceAsStream("hairtype.properties");
		Properties p = new Properties();
		p.load(is);
		Enumeration<String> en = (Enumeration<String>) p.propertyNames();
		while(en.hasMoreElements()){
			String key = en.nextElement();
			String value = p.getProperty(key);
			System.out.println(key+"="+value);
		}
	}
}
