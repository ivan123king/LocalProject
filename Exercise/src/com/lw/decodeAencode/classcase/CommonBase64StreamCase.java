package com.lw.decodeAencode.classcase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

/**
 * commons codec Base64的流支持
 * @author lenovo
 *
 */
public class CommonBase64StreamCase {
	
	public static void main(String[] args) throws FileNotFoundException,IOException{
		
		/**
		 * 文件内容：
		 *  350380030001 
			350380031001 
			dddd
		 */
		InputStream is = new FileInputStream("d:\\ftptest.txt");
		//实例化Base64InputStream，用作Base64编码   true encode;false decode
		Base64InputStream input = new Base64InputStream(is,false);
		//使用DataInputStream包装Base64InputStream
		DataInputStream dis = new DataInputStream(input);
		//信息体
		byte[] data = new byte[dis.available()];
//		System.out.println(dis.available()); //1
		//读入  抛出java.io.EOFException 表示流到达末尾
		dis.readFully(data);
		//关闭流
		dis.close();
		//控制台输出
		System.out.println(new String(data));
		
		
		OutputStream os = new FileOutputStream("d:\\ftptest.txt");
		//实例化Base64OutputStream，用作Base64编码
		Base64OutputStream output = new Base64OutputStream(os,true);
		//使用DataOutputStream包装Base64OutputStream
		DataOutputStream dos = new DataOutputStream(output);
		//写操作
		dos.write(data);//此处会写入文件
		dos.flush();
		dos.close();
		System.out.println(new String(data));
		
	}
}
