package com.lw.decodeAencode.classcase;

import org.bouncycastle.util.encoders.Hex;

/**
 * 16进制编码
 * @author lenovo
 *
 */
public class HexCase {
	public static void main(String[] args) {
		String str = "Hex 编码";
		byte[] input = str.getBytes();
		// Hex 编码     
		byte[] data = Hex.encode(input);
		System.out.println("编码后：\t"+new String(data));//48657820e7bc96e7a081
		// Hex 解码
		byte[] output = Hex.decode(data);
		System.out.println("解码后：\t"+new String(output));
	}
}
