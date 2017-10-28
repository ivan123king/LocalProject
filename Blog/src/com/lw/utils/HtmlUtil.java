package com.lw.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析完word文档后，生成HTML代码的工具类
 * 
 * @author lenovo
 *
 */
public class HtmlUtil {

	public static String createHtmlPage(Document document) {
		Element word = document.getRootElement();

		// 遍历
		StringBuffer content = new StringBuffer();
		content.append(listNode(word));
		System.out.println(content.toString());
		return content.toString();
	}

	public static String createHtmlPage(String filePath)
			throws MalformedURLException, DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(filePath));
		// 获取根节点元素对象
		Element word = document.getRootElement();

		// 遍历
		StringBuffer content = new StringBuffer();
		content.append(listNode(word));
		System.out.println(content.toString());
		return content.toString();
	}

	private static String listNode(Element node) {
		StringBuffer content = new StringBuffer();
		String nodeName = node.getName();
		if (nodeName.equals(ConstFile.PARAGRAPH)) {
			if (null != node.getTextTrim() && !"".equals(node.getTextTrim())) {
				String color = node.attributeValue("color");
				String fontSize = node.attributeValue("fontSize");
				int fontSizeI = Integer.parseInt(fontSize);
				if(fontSizeI<=0) fontSize = "100";
				else{
					fontSize = String.valueOf(fontSizeI*5+100);
					
					fontSize = "100";//由于目前还没有找到很好对应word中font-size方法，所以统一设为100
				}
				content.append("<" + ConstFile.DIVH + " style='color:#"+color+"; font-size:"+fontSize+"% '>");
				content.append(StringEscapeUtils.escapeXml11(node.getText()));
				content.append("</" + ConstFile.DIVH + ">");
			}
		} else if (nodeName.equals(ConstFile.PICTURE)) {
			if (null != node.getTextTrim() && !"".equals(node.getTextTrim())) {
				content.append("<" + ConstFile.IMGH + " src='"
						+ ConstFile.PIC_PATH_H + File.separator
						+ node.getText() + ".png' />");
			}
		} else if (nodeName.equals(ConstFile.TABLE)) {
			content.append("<" + ConstFile.TABLEH + ">");
			Iterator<Element> iter = node.elementIterator();
			while (iter.hasNext()) {
				String c = listNode(iter.next());
				content.append(c);
			}
			content.append("</" + ConstFile.TABLEH + ">");
		} else if (nodeName.equals(ConstFile.ROW)) {
			content.append("<" + ConstFile.ROWH + ">");
			Iterator<Element> iter = node.elementIterator();
			while (iter.hasNext()) {
				String c = listNode(iter.next());
				content.append(c);
			}
			content.append("</" + ConstFile.ROWH + ">");
		} else if (nodeName.equals(ConstFile.COL)) {
			if (null != node.getTextTrim() && !"".equals(node.getTextTrim())) {
				content.append("<" + ConstFile.COLH + ">");
				content.append("<" + ConstFile.PREH + ">");
				content.append(StringEscapeUtils.escapeXml11(node.getText()));
				content.append("</" + ConstFile.PREH + ">");
				content.append("</" + ConstFile.COLH + ">");
			}
		}
		//对表格不做处理
		if (!(nodeName.equals(ConstFile.COL) || nodeName.equals(ConstFile.ROW) || nodeName
				.equals(ConstFile.TABLE))) {
			Iterator<Element> iter = node.elementIterator();
			while (iter.hasNext()) {
				String c = listNode(iter.next());
				content.append(c);
			}
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
