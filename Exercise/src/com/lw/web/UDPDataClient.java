package com.lw.web;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP客户端
 * 
 * @author lenovo
 *
 */
public class UDPDataClient {

	public static void main(String[] args) throws IOException {
		/**
		 * 一、向服务器发送数据
		 */
		// 1.定义服务器地址，端口号，数据
		InetAddress address = InetAddress.getByName("localhost");
		int port = 8900;
		byte[] data = "客户数据".getBytes();
		// 2。 创建数据包
		DatagramPacket packet = new DatagramPacket(data, data.length, address,
				port);
		//3.创建DatagramSocket对象
		DatagramSocket socket = new DatagramSocket();
		//4. 向服务器发送数据
		socket.send(packet);

		/**
		 * 二、接收服务器数据
		 */
		//1.创建数据报
		byte[] data2 = new byte[1024];
		DatagramPacket packet2 = new DatagramPacket(data2,data2.length);
		//2.接收服务器响应数据
		socket.receive(packet2);
		//3.读取数据
		String reply = new String(data2,0,packet2.getLength());
		System.out.println("服务端发送过来的数据为："+reply);
		//4. 关闭资源
		socket.close();
		
		
	}
}
