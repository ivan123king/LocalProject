package com.lw.decodeAencode.classcase;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 构建秘钥对与还原密钥
 * @author lenovo
 *
 */
public class keyPairTest {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		//实例化KeyPairGenerator对象，并指定RSA算法
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		//初始化KeyPairGenerator对象
		keyPairGen.initialize(1024);
		//生成KeyPair对象
		KeyPair keyPair = keyPairGen.generateKeyPair();
		//获取私钥密钥字节数组。实际使用过程中该密钥以此形式保存传递给另一方
		byte[] keyBytes = keyPair.getPrivate().getEncoded();
		//由私钥密钥字节数组获得密钥规范
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		//实例化密钥工厂，并指定RSA算法
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		//生成私钥
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		//生成公钥
		Key publicKey = keyFactory.generatePublic(pkcs8KeySpec);
	}
}
