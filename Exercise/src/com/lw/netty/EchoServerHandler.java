package com.lw.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;

/**
 * 编写Echo服务器
 * 
 * @author lenovo
 *
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf in = (ByteBuf) msg;
		System.out.println("Server Received:" + in.toString(CharsetUtil.UTF_8));
		ctx.write(in);// 将接收到的消息写给发送者，而不是冲刷出站消息
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		//将未决消息冲刷到远程节点，并关闭该channel
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(
				ChannelFutureListener.CLOSE);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
		//出现异常，打印，并关闭Channel
		cause.printStackTrace();
		ctx.close();
	}
}
