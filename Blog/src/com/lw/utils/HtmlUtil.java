package com.lw.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析完word文档后，生成HTML代码的工具类
 * @author lenovo
 *
 */
public class HtmlUtil {

	
	
	
	public static String createHtmlPage(Document document){
		Element word = document.getRootElement();
		
		//遍历
		StringBuffer content = new StringBuffer();
		content.append(listNode(word));
		System.out.println(content.toString());
		return content.toString();
	}
	
	
	public static String createHtmlPage(String filePath) throws MalformedURLException, DocumentException{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(filePath));
		//获取根节点元素对象
		Element word = document.getRootElement();
		
		//遍历
		StringBuffer content = new StringBuffer();
		content.append(listNode(word));
		System.out.println(content.toString());
		return content.toString();
	}
	
	private static String listNode(Element node){
		StringBuffer content = new StringBuffer();
		if(node.getName().equals(ConstFile.PARAGRAPH)){
			if(null!=node.getTextTrim()&&!"".equals(node.getTextTrim())){
				content.append("<"+ConstFile.SPANH+">");
				content.append(node.getText());
				content.append("</"+ConstFile.SPANH+">");
			}
		}else if(node.getName().equals(ConstFile.PICTURE)){
			if(null!=node.getTextTrim()&&!"".equals(node.getTextTrim())){
				content.append("<"+ConstFile.IMGH+" src='"+ConstFile.PIC_PATH_H+File.separator+node.getText()+".png' />");
//				content.append(node.getText());
//				content.append("</"+ConstFile.IMGH+">");
			}
		}else if(node.getName().equals(ConstFile.TABLE)){
			if(null!=node.getTextTrim()&&!"".equals(node.getTextTrim())){
				content.append("<"+ConstFile.SPANH+">");
				content.append(node.getText());
				content.append("</"+ConstFile.SPANH+">");
			}
		}
		
		Iterator<Element> iter = node.elementIterator();
		while(iter.hasNext()){
			String c = listNode(iter.next());
			content.append(c);
		}
		return content.toString();
	}
	
	public static void main(String[] args) {
		String filePath = "d:\\tmp\\person.xml";
		try {
			HtmlUtil.createHtmlPage(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
