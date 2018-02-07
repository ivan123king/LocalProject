package com.lw.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 初始化ChannelPipeline
 * @author lenovo
 *
 */
public class ChatServerInitializer extends ChannelInitializer<Channel>{

	private final ChannelGroup group;
	
	public ChatServerInitializer(ChannelGroup group){
		this.group = group;
	}
	
	
	
	
	@Override
	protected void initChannel(Channel channel) throws Exception {
		/*
		 * 将所有需要的ChannelHandler添加到ChannelPipeline中
		 */
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new HttpObjectAggregator(64*10241));
		pipeline.addLast(new HttpRequestHandler("/ws"));
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		pipeline.addLast(new TextWebSocketFrameHandler(group));
		
	}
	
}
