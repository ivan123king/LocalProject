package com.lw.decodeAencode.classcase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * 获取证书撤销列表
 * @author lenovo
 *
 */
public class CRLCase {
	public static void main(String[] args) throws CertificateException {
		//实例化，并指定证书类型为X509
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		try {
			//获取证书输入流
			FileInputStream in = new FileInputStream("D:\\x.keystore");
			//获取证书撤销列表
			CRL crl = certificateFactory.generateCRL(in);
			//关闭流
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CRLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
