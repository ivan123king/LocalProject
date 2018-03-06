package com.lw.web;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 一个Echo服务器
 * 非阻塞的异步网络编程
 * 
 * @author lenovo
 *
 */
public class EchoServer {
	public static int DEFAULT_PORT = 7;

	public static void main(String[] args) {
		int port = 0;
		try{
			port = Integer.parseInt(args[0]);
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		System.out.println("监听端口:"+port);
		
		ServerSocketChannel serverChannel;
		Selector selector;
		try{
			serverChannel = ServerSocketChannel.open();
			ServerSocket ss = serverChannel.socket();
			InetSocketAddress address = new InetSocketAddress(port);
			ss.bind(address);//绑定端口
			serverChannel.configureBlocking(false);//非阻塞
			selector = Selector.open();
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		}catch(IOException ex){
			ex.printStackTrace();
			return;
		}
		
		while(true){
			try{
				selector.select();//选择一个通信渠道
			}catch(IOException e){
				e.printStackTrace();
				break;
			}
			//获取所有接收事件的SelectionKey实例
			Set<SelectionKey> readKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = readKeys.iterator();
			while(iterator.hasNext()){
				SelectionKey key = iterator.next();
				iterator.remove();
				try{
					if(key.isAcceptable()){
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();
						System.out.println("客户端连接。 "+client);
						client.configureBlocking(false);
						//接受客户端，并将它注册到选择器
						SelectionKey clientKey = client.register(selector, SelectionKey.OP_WRITE|SelectionKey.OP_READ);
						ByteBuffer buffer = ByteBuffer.allocate(100);
						clientKey.attach(buffer);
					}
					if(key.isReadable()){
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer output = (ByteBuffer) key.attachment();
						client.read(output);
					}
					if(key.isWritable()){
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer output = (ByteBuffer) key.attachment();
						output.flip();
						client.write(output);
						output.compact();
					}
				}catch(IOException e){
					key.cancel();
					try{
						key.channel().close();
					}catch(IOException ex){}
				}
			}
		}
		
	}
}
