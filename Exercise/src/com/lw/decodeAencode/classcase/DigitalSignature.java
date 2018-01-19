package com.lw.decodeAencode.classcase;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

/**
 * 数字签名
 * @author lenovo
 *
 */
public class DigitalSignature {
	
	public static void main(String args[]) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException{
		/**
		 * 私钥签名和公钥验证需要的公共部分
		 */
		//实例化KeyPairGenerator对象，并指定DSA算法
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
		//初始化KeyPairGenerator对象
		keyPairGen.initialize(1024);
		//生成KeyPair对象
		KeyPair keyPair = keyPairGen.generateKeyPair();
		//实例化Signature对象
		Signature signature = Signature.getInstance(keyPairGen.getAlgorithm());
		/*
		 * 1. 私钥签名
		 */
		//待做数字签名的原始信息
		byte[] data = "Data Signature".getBytes();
		//初始化用于签名操作的Signature对象
		signature.initSign(keyPair.getPrivate());
		//更新
		signature.update(data);
		//获得签名，即字节数组sign
		byte[] sign = signature.sign();
		
		/*
		 * 2. 公钥验证
		 */
		signature.initVerify(keyPair.getPublic());
		signature.update(data);
		boolean status = signature.verify(sign);
		System.out.println(status);
	}
}
