package com.lw.decodeAencode.classcase;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

/**
 * 加密，解密
 * 加包，解包
 * @author lenovo
 *
 */
public class CipherCase {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		//实例化KeyGenerator对象，并指定DES算法
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		//生成SecretKey对象
		SecretKey secretKey = keyGenerator.generateKey();
		//实例化Cipher对象
		Cipher cipher = Cipher.getInstance("DES");
		
		//包装和解包是一组操作，加密和解密是一组操作
		
		/*
		 * 一、包装
		 */
		//初始化Cipher对象，用于包装
		cipher.init(Cipher.WRAP_MODE, secretKey);
		//包装秘密密钥
		byte[] k = cipher.wrap(secretKey);
		
		/*
		 * 二、解包
		 */
		//初始化Cipher对象，用于解包
		cipher.init(Cipher.UNWRAP_MODE, secretKey);
		//解包秘密密钥
		Key key = cipher.unwrap(k, "DES", Cipher.SECRET_KEY);
		
		/*
		 * com.sun.crypto.provider.DESKey@1840e
			javax.crypto.spec.SecretKeySpec@1840e
		 */
		System.out.println(secretKey+"\r\n"+key);
		System.out.println(Base64.encodeBase64String(secretKey.getEncoded())+"\r\n"+
				new String(key.getEncoded()));
		System.out.println(secretKey.getAlgorithm());
		
		/*
		 * a、加密
		 */
		//初始化Cipher对象，用于加密
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		//加密  DES DATA 需要加密的数据
		byte[] input = cipher.doFinal("DES DATA".getBytes());
		
		/*
		 * b.解密
		 */
		//初始化Cipher对象，用于解密
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		//解密
		byte[] output = cipher.doFinal(input);
		
		System.out.println(new String(output)); //DES DATA
		
		
	}
}
