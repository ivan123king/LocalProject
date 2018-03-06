package com.lw.web;

import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.catalina.tribes.membership.McastServiceImpl.SenderThread;

/**
 * Echo 客户端
 * @author lenovo
 *
 */
public class UDPEchoClient {

	public final static int PORT = 7;
	
	public static void main(String[] args) {
		String hostName = "localhost";
		if(args.length>0){
			hostName = args[0];
		}
		
		try{
			InetAddress ia = InetAddress.getByName(hostName);
			DatagramSocket socket = new DatagramSocket();
			
		}
	}
}
