package com.lw.decodeAencode.classcase;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 获取秘密密钥的密钥编码字节数组
 * @author lenovo
 *
 */
public class SecretKeyFactoryCase {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		//实例化KeyGenerator对象，并指定DES算法
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		//生成SecretKey对象
		SecretKey secretKey = keyGenerator.generateKey();
		//获取秘密密钥的密钥编码字节数组
		byte[] key = secretKey.getEncoded();
		
		/*
		 * 如果key被别人知道就可以通过以下方式还原出密钥编码字节数组
		 */
		//由获得的秘密密钥编码字节数组构建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		//实例化SecretKeyFactory对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		//生成SecretKey对象
		SecretKey secretKey02 = keyFactory.generateSecret(dks);
		
		if(new String(secretKey.getEncoded()).equals(new String(secretKey02.getEncoded()))) 
			System.out.println("秘密密钥还原成功");
		
		
	}
}
