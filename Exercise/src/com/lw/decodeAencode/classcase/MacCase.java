package com.lw.decodeAencode.classcase;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

/**
 * 安全消息摘要案例
 * @author lenovo
 *
 */
public class MacCase {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
		//待做安全消息摘要的原始信息
		byte[] input = "MAC".getBytes();
		//初始化KeyGenerator对象，并使用HmacMD5算法
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
		//构建SecretKey对象  这个就是双方共享的秘密密钥
		SecretKey secretKey = keyGenerator.generateKey();
		//构建Mac对象
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		//初始化Mac对象
		mac.init(secretKey);
		//获取经过安全消息摘要后的信息
		byte[] output = mac.doFinal(input);
	}
}
