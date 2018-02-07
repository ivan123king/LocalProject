package com.lw.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * 为ChannelPipeline加密
 * @author lenovo
 *
 */
public class SecureChatServerInitializer extends ChatServerInitializer {
	private final SslContext context;
	public SecureChatServerInitializer(ChannelGroup group,SslContext context){
		super(group);
		this.context = context;
	}

	@Override
	protected void initChannel(Channel channel)throws Exception{
		super.initChannel(channel);
		SSLEngine engine = context.newEngine(channel.alloc());
		engine.setUseClientMode(false);
		channel.pipeline().addFirst(new SslHandler(engine));//注意这里是addFirst，加密是第一位
	}

	
	
	
}
