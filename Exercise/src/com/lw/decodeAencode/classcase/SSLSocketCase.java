package com.lw.decodeAencode.classcase;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * 获取数字证书
 * @author lenovo
 *
 */
public class SSLSocketCase {
	public static void main(String[] args) throws UnknownHostException, IOException {
		String hostname = "www.baidu.com";
		int port = 80;
		//获得SSLSocketFactory实例
		SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
		//构建SSLSocket实例
		SSLSocket socket = (SSLSocket)factory.createSocket(hostname, port);
		//SSL握手
		socket.startHandshake();
		//获取SSLSession实例
		SSLSession session = socket.getSession();
		//关闭Socket
		socket.close();
		//获得数字证书
		session.getPeerCertificates();
		
	}
}
