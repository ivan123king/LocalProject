package com.lw.web;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * UDP服务端
 * @author lenovo
 *
 */
public class UDPDataServer {

	public static void main(String[] args) throws IOException {
		
		/**
		 * 一、接收客户端数据
		 */
		//1. 指定监听端口
		DatagramSocket datagramSocket = new DatagramSocket(8900);
		//2. 创建数据报，接收客户端发送的数据
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data,data.length);
		//3. 接收客户端发送的数据  此处阻塞等待数据
		datagramSocket.receive(packet);
		//4. 读取客户端发送的数据
		String info = new String(data,0,packet.getLength());
		System.out.println("客户端数据："+info);
		
		/**
		 * 二、发送数据给客户端
		 */
		//1. 获取客户端地址，端口号，数据
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		byte[] data2 = "欢迎".getBytes();
		//2.创建数据包
		DatagramPacket outPacket = new DatagramPacket(data2,data2.length,address,port);
		//3.响应客户端
		datagramSocket.send(outPacket);
		//4. 关闭资源
		datagramSocket.close();
		
	}
}
