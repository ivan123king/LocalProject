package com.lw.decodeAencode.classcase;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

/**
 * 对象加密处理
 * @author lenovo
 *
 */
public class SealedObjectCase {
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, IOException, ClassNotFoundException, BadPaddingException {
		//待加密的字符串对象
		String input = "SealedObject";
		//实例化KeyGenerator对象，并使用DES算法
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		//创建秘密密钥
		SecretKey secretKey = keyGenerator.generateKey();
		//实例化用于加密的Cipher对象cipher1
		Cipher cipher1 = Cipher.getInstance("DES");
		//初始化
		cipher1.init(Cipher.ENCRYPT_MODE, secretKey);
		//构建SealedObject对象   如果在网络中由于实现了Serializable接口，所以可以传输对象，就传输此对象给对方
		SealedObject sealedObject = new SealedObject(input,cipher1);
		//实例化用于解密的Cipher对象cipher2
		Cipher cipher2 = Cipher.getInstance(secretKey.getAlgorithm());
		//初始化
		cipher2.init(Cipher.DECRYPT_MODE, secretKey);
		//获取解密后的字符串对象  output,output2 都可以获取解密数据
		String output = (String)sealedObject.getObject(cipher2);
		String output2 = (String)sealedObject.getObject(secretKey);
		System.out.println(output);
		
		/**
		 * 通过上述output,output2可以看出，如果在网络中传输的加密后保存数据对象SealedObject，
		 * 只要知道是DES加密的通过output2方式就可以获取数据，
		 * 不安全，能被快速破解
		 */
	}
}
