package com.lw.decodeAencode.classcase;

import org.bouncycastle.util.encoders.Base64;


/**
 * Base64加密解密
 * @author lenovo
 *
 */
public class Base64Case {
	public static void main(String[] args) {
		String str = "base64编码";
		System.err.println("原文：\t"+str);
		byte[] input = str.getBytes();
		//Base64编码
		byte[] data = Base64.encode(input);
		System.err.println("编码后：\t"+new String(data));
		//Base64解码
		byte[] output = Base64.decode(data);
		System.err.println("解码后:\t"+new String(output));
	}
}
