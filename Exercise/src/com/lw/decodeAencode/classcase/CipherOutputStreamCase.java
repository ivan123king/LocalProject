package com.lw.decodeAencode.classcase;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class CipherOutputStreamCase {
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, FileNotFoundException,IOException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		SecretKey secretKey = keyGenerator.generateKey();
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		//待加密的原始数据
		String input = "1234567890";
		//实例化CipherOutputStream
		@SuppressWarnings("resource")
		CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(new File("secret")),cipher);
		DataOutputStream dos = new DataOutputStream(cos);
		//向输出流写待加密的数据
		dos.writeUTF(input);
		//清空流
		dos.flush();
		//关闭流
		dos.close();
		
	}
}
