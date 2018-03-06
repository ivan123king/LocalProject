package com.lw.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * 日志事件监听器
 * 
 * @author lenovo
 *
 */
public class LogEventMonitor {

	private final EventLoopGroup group;
	private final Bootstrap bootstrap;

	public LogEventMonitor(InetSocketAddress address) {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioDatagramChannel.class)
				.option(ChannelOption.SO_BROADCAST, true)
				.handler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel channel) {
						ChannelPipeline pipeline = channel.pipeline();
						pipeline.addLast(new LogEventDecoder());
						pipeline.addLast(new LogEventHandler());
					}
				}).localAddress(address);
	}

	public Channel bind() {
		return bootstrap.bind().syncUninterruptibly().channel();
	}

	public void stop() {
		group.shutdownGracefully();
	}

	public static void main(String[] args) throws InterruptedException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Usage: LogEventMonitor <port>");
		}
		LogEventMonitor monitor = new LogEventMonitor(new InetSocketAddress(
				Integer.parseInt(args[0])));
		try{
			Channel channel = monitor.bind();
			System.out.println("LogEventMonitor running");
			channel.closeFuture().sync();
		}finally{
			monitor.stop();
		}
	}
}
