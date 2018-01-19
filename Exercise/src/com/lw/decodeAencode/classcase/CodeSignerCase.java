package com.lw.decodeAencode.classcase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.CodeSigner;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Date;

/**
 *  代码签名例子
 * @author lenovo
 *
 */
public class CodeSignerCase {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException{
		//构建CertificateFactory对象，并指定证书类型为X.509
		CertificateFactory cf = CertificateFactory.getInstance("X509");
		//生成CertPath对象
		CertPath cp = cf.generateCertPath(new FileInputStream("d:\\x.cer"));
		//实例化Timestamp对象
		Timestamp t = new Timestamp(new Date(),cp);
		//实例化CodeSigner对象
		CodeSigner cs = new CodeSigner(cp,t);
		//获取比对结果
		boolean status = cs.equals(new CodeSigner(cp,t));
	}
}
