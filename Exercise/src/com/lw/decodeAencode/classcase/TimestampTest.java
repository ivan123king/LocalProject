package com.lw.decodeAencode.classcase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Date;

/**
 * 数字时间戳
 * @author lenovo
 *
 */
public class TimestampTest {
	public static void main(String args[]) throws CertificateException, FileNotFoundException{
		//构建CertificateFactory对象，并指定证书类型为X.509
		CertificateFactory cf = CertificateFactory.getInstance("X509");
		//生成CertPath对象
		CertPath cp = cf.generateCertPath(new FileInputStream("D:\\x.cer"));
		//实例化数字时间戳
		Timestamp t = new Timestamp(new Date(),cp);
		
		
	}
}
