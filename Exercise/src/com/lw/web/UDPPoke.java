package com.lw.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 一个UDP数据包的客户端案列
 * @author lenovo
 *
 */
public class UDPPoke {

	private int bufferSize ;
	private int timeout;
	private InetAddress host;
	private int port;
	
	public UDPPoke(InetAddress host,int port,int bufferSize,int timeout) {
		this.bufferSize = bufferSize;
		if(port<1||port>65535) throw new IllegalArgumentException("Port out of range.");
		this.port = port;
		this.host = host;
		this.timeout = timeout;
	}
	
	public UDPPoke(InetAddress host,int port,int bufferSize) {
		this(host,port,bufferSize,30000);
	}
	
	public UDPPoke(InetAddress host,int port) {
		this(host, port, 8192);
	}
	
	public byte[] poke(){
		try(DatagramSocket socket = new DatagramSocket(0)){
			DatagramPacket outgoing = new DatagramPacket(new byte[1],1,host,port);
			socket.connect(host,port);
			socket.setSoTimeout(timeout);
			socket.send(outgoing);
			DatagramPacket incoming = new DatagramPacket(new byte[bufferSize],bufferSize);
			//阻塞，直到接收到响应
			socket.receive(incoming);
			int numBytes = incoming.getLength();
			byte[] response = new byte[numBytes];
			System.arraycopy(incoming.getData(), 0, response, 0, numBytes);
			return response;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		InetAddress host;
		int port = 0;
		try{
			host = InetAddress.getByName(args[0]);
			port = Integer.parseInt(args[1]);
			System.out.println("host:"+host+",port:"+port);
		}catch(RuntimeException | UnknownHostException e){
			System.out.println("Usage: java UDPPoke host port");
			return;
		}
		try{
			UDPPoke poker = new UDPPoke(host,port);
			byte[] response = poker.poke();
			if(response==null){
				System.out.println("no response");
				return;
			}
			String result = new String(response,"US-ASCII");
			System.out.println(result);
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
}
