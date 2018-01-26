package com.lw.decodeAencode.classcase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * 加载证书
 * @author lenovo
 *
 */
public class CertificateCase {
	public static void main(String[] args) throws CertificateException, IOException {
		//实例化，并指定证书类型为X.509
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		//获取证书输入流
		FileInputStream in = new FileInputStream("D:\\x.keystore");
		//获取证书
		Certificate certificate = certificateFactory.generateCertificate(in);
		//关闭流
		in.close();
	}
}
