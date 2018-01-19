package com.lw.decodeAencode.classcase;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;


/**
 * 创建数字签名的类，同DigitalSignature
 * @author lenovo
 *
 */
public class SignedObjectTest {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		//待做数字签名的原始信息
		byte[] data = "Data Signature".getBytes();
		//实例化KeyPairGenerator对象，并指定DSA算法
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
		//初始化KeyPairGenerator对象
		keyPairGen.initialize(1024);
		//生成KeyPair对象
		KeyPair keyPair = keyPairGen.generateKeyPair();
		//实例化Signature对象
		Signature signature = Signature.getInstance(keyPairGen.getAlgorithm());
		//---------与DigitalSignature不同之处    获得数字签名的另一种方式
		//实例化SignedObject对象
		SignedObject s = new SignedObject(data,keyPair.getPrivate(),signature);
		//获得数字签名
		byte[] sign = s.getSignature();
		//验证签名
		boolean status = s.verify(keyPair.getPublic(), signature);
	}
}
