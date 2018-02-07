package com.lw.netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务器主类
 * @author lenovo
 *
 */
public class EchoServer {

	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws InterruptedException {
		if (args.length != 1) {
			System.err.println("Usage: " + EchoServer.class.getSimpleName()
					+ " <port>");
			return;
		}
		int port = Integer.parseInt(args[0]);
		new EchoServer(port).start();// 调用服务器start方法
	}

	public void start() throws InterruptedException {
		final EchoServerHandler serverHandler = new EchoServerHandler();
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(group)
					.channel(NioServerSocketChannel.class)//指定所使用的NIO传输Channel
					.localAddress(new InetSocketAddress(port))//使用指定的端口设置套接字地址
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							//EchoServerHandler被标志位@Sharable，所以总是使用同样的实例
							/*
							 * 对于所有的客户端连接都会使用同一个EchoServerHandler，因为被标志
							 * 为@Sharable
							 */
							ch.pipeline().addLast(serverHandler);
						}
					});
			ChannelFuture f = b.bind().sync();//异步绑定服务器，调用sync阻塞等待直到绑定完成
			f.channel().closeFuture().sync();//获取Channel的closeFuture并且阻塞当前线程，直到它完成
		} finally {
			group.shutdownGracefully().sync();//关闭EventLoopGroup，释放所有资源
		}
	}
}
