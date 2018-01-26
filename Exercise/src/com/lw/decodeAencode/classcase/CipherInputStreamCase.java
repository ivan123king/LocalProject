package com.lw.decodeAencode.classcase;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * 密钥输入流
 * @author lenovo
 *
 */
public class CipherInputStreamCase {
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
		//实例化KeyGenerator对象，并指定DES算法
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		//生成SecretKey对象
		SecretKey secretKey = keyGenerator.generateKey();
		//实例化Cipher对象
		Cipher cipher = Cipher.getInstance("DES");
		
		//初始化Cipher对象，用于解密操作
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		//实例化CipherInputStream对象
		//这里不是随便的一个文件，否则会报错：
		// javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher
		String path = "src/com/lw/decodeAencode/classcase/cipherinputstreamtest.txt";
		CipherInputStream cis = new CipherInputStream(new FileInputStream(new File(path)),cipher);
		//使用DataInputStream对象包装CipherInputStream对象
		DataInputStream dis = new DataInputStream(cis);
		//读出解密后的数据
		String output = dis.readUTF();
		//关闭流
		dis.close();
	}
}
