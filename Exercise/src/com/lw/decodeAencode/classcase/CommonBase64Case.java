package com.lw.decodeAencode.classcase;

import org.apache.commons.codec.binary.Base64;

/**
 * common-codec的base64编码解码
 * @author lenovo
 *
 */
public class CommonBase64Case {

	public static void main(String[] args) {
		String str = "Base64 编码";
		byte[] input = str.getBytes();
		//Base64 编码
		byte[] data = Base64.encodeBase64(input);
		System.out.println("编码后:\t"+new String(data));//QmFzZTY0IOe8lueggQ==
		//Base64 解码
		byte[] output = Base64.decodeBase64(data);
		System.out.println("解码后：\t"+new String(output));
	}
}
