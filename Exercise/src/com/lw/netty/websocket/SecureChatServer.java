package com.lw.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

/**
 * 向ChatServer添加加密
 * @author lenovo
 *
 */
public class SecureChatServer extends ChatServer{

	private final SslContext context;
	
	public SecureChatServer(SslContext context){
		this.context = context;
	}

	@Override
	protected ChannelInitializer<Channel> createInitializer(ChannelGroup group){
		return new SecureChatServerInitializer(group,context);
	}
	
	public static void main(String[] args) throws CertificateException, SSLException {
		if(args.length!=1){
			System.err.println("Please give port as argument.");
			System.exit(1);
		}
		int port = Integer.parseInt(args[0]);
		SelfSignedCertificate cert = new SelfSignedCertificate();
		//这里就涉及到证书加密解密，可以看java加密解密的艺术的笔记
		SslContext sslContext = SslContext.newServerContext(cert.certificate(),cert.privateKey());
		final SecureChatServer endpoint = new SecureChatServer(sslContext);
		ChannelFuture future = endpoint.start(new InetSocketAddress(port));
		Runtime.getRuntime().addShutdownHook(new Thread(){
			
			@Override
			public void run(){
				endpoint.destroy();
			}
		});
		future.channel().closeFuture().syncUninterruptibly();
	}
}
