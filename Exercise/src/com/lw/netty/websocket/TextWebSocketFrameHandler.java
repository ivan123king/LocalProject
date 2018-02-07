package com.lw.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * 处理WebSocket帧
 * @author lenovo
 *
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private final ChannelGroup group;
	
	public TextWebSocketFrameHandler(ChannelGroup group){
		this.group = group;
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx,Object evt)throws Exception{
		/*
		 *通知所有已经连接的WebSocket客户端新的客户端连接上了 
		 */
		if(evt==WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE){
			ctx.pipeline().remove(HttpRequestHandler.class);
			group.writeAndFlush(new TextWebSocketFrame("Client "+ctx.channel()+" joined."));
			//将新的WebSocket Channel添加到ChannelGroup中，以便他能接收到所有消息
			group.add(ctx.channel());
		}else{
			super.userEventTriggered(ctx, evt);
		}
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			TextWebSocketFrame msg) throws Exception {
		//增加消息的引用计数，并将它写到ChannelGroup中所有已经连接的客户端
		group.writeAndFlush(msg.retain());//这里将接收到index.html中发送过来的消息，然后回写给页面
	}

	
}
