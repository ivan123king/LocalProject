package com.lw.decodeAencode.classcase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要
 * @author lenovo
 *
 */
public class MessageDigestTest {

	public static void main(String args[]) throws NoSuchAlgorithmException{
		//待做消息摘要算法的原始信息
		byte[] input = "sha".getBytes();
		//初始化MessageDigest对象，将使用SHA算法
		MessageDigest sha = MessageDigest.getInstance("MD5");
		//更新摘要信息
		sha.update(input);
		//获取消息摘要结果
		byte[] output = sha.digest();
		
//		sha.update("sha".getBytes());
//		byte[] output2 = sha.digest();
//		if(new String(output).equals(new String(output2))) 
//			System.out.println("success");
		
		/*
		 * DigestInputStream实现和MessageDigest一样的功能
		 * 只不过是通过流来操作，最后获取摘要信息依旧通过MessageDisgest的digest()方法
		 */
		//构建DigestInputStream对象
		DigestInputStream dis = new DigestInputStream(new ByteArrayInputStream(input),sha);
		try {
			//流输入
			dis.read(input,0,input.length);
			//获取摘要信息
			byte[] output3 = dis.getMessageDigest().digest();
			//关闭流
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 *DigestOutputStream 流式消息摘要，同DigestInputStream类，区别在于使用write方法摘要 
		 */
		//构建DigestOutputStream
		DigestOutputStream dos = new DigestOutputStream(new ByteArrayOutputStream(),sha);
		try {
			//流输出
			dos.write(input,0,input.length);
			//获取摘要信息
			byte[] output4 = dos.getMessageDigest().digest();
			//清空流
			dos.flush();
			//关闭流
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
