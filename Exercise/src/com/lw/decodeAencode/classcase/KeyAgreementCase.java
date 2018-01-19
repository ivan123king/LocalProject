package com.lw.decodeAencode.classcase;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

/**
 * DH算法密钥对生成
 * @author lenovo
 *
 */
public class KeyAgreementCase {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
		//实例化KeyPairGenerator对象，并指定DH算法
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DH");
		//生成KeyPair对象
		KeyPair kp1 = kpg.generateKeyPair();
		//生成KeyPair对象
		KeyPair kp2 = kpg.generateKeyPair();
		
		//实例化KeyAgreement对象
		KeyAgreement keyAgree = KeyAgreement.getInstance(kpg.getAlgorithm());
		//初始化KeyAgreement对象
		keyAgree.init(kp2.getPrivate());
		//执行计划	用给定密钥执行KeyAgreement的下一个阶段
		keyAgree.doPhase(kp1.getPublic(), true);
		//生成SecretKey对象
		SecretKey secretKey = keyAgree.generateSecret("DES");
	}
}
