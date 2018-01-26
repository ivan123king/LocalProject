package com.lw.decodeAencode.classcase;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.UrlBase64;

/**
 * UrlBase64编码解码
 * @author lenovo
 *
 */
public class UrlBase64Case {
	public static void main(String[] args) {
		String str = "Base64 编码";
		System.err.println("原文:\t"+str);
		byte[] input = str.getBytes();
		//URL Base64 编码
		byte[] data = UrlBase64.encode(input);
		//QmFzZTY0IOe8lueggQ..   QmFzZTY0IOe8lueggQ==  所以UrlBase64和Base64编码是一样的
		System.err.println("编码后:\t"+new String(data)+"--"+new String(Base64.encode(input)));
		//URL Base64解码
		byte[] output = UrlBase64.decode(data);
		System.err.println("解码后：\t"+new String(output));
	}
}
