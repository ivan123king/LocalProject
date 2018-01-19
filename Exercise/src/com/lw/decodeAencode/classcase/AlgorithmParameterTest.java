package com.lw.decodeAencode.classcase;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameterGeneratorSpi;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;

/**
 * 获取算法参数的测试类
 * @author lenovo
 *
 */
public class AlgorithmParameterTest {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
//		for(Provider p: Security.getProviders()){
//			 for(Service s:p.getServices()){
//	                System.out.println("类型:"+s.getType()+"，算法："+s.getAlgorithm());
//	          }
//		}
		/**
		 * 报错： java.security.NoSuchAlgorithmException：DES AlgorithmParameterGenerator not available
		 * 如果是用DSA，那么就一切正常，说明在jdk中没有实现对应的DES算法
		 */
		AlgorithmParameterGenerator apg = AlgorithmParameterGenerator.getInstance("DES");
		apg.init(512);
		AlgorithmParameters ap = apg.generateParameters();
		byte[] b = ap.getEncoded();
		System.out.println(new BigInteger(b).toString());
		
		
		
	}
}
