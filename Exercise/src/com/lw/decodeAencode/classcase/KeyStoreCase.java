package com.lw.decodeAencode.classcase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * 加载密钥库
 * 
 * @author lenovo
 *
 */
public class KeyStoreCase {

	public static void main(String[] args) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableEntryException {
		// 加载密钥库文件 .keystore是密钥库文件
		FileInputStream is = new FileInputStream("d:\\.keystore");
		// 实例化KeyStore对象
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		// 加载密钥库，使用密码password
		ks.load(is, "password".toCharArray());
		// 关闭文件流
		is.close();

		// 方法一：获取别名为“alias”所对应的私钥
		PrivateKey key = (PrivateKey) ks.getKey("alias",
				"password".toCharArray());

		// 方法二：通过获取私钥项获取私钥
		KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) ks
				.getEntry(
						"alias",
						new KeyStore.PasswordProtection("password"
								.toCharArray()));
		//获取私钥
		PrivateKey privateKey = pkEntry.getPrivateKey();

	}
}
