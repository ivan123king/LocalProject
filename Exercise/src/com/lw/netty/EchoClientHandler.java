package com.lw.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * 编写客户端处理类
 * @author lenovo
 *
 */
@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
	
	@Override
	public void channelActive(ChannelHandlerContext ctx){
		//当被通知Channel是活跃时，发送一条消息给服务器
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, ByteBuf arg1)
			throws Exception {
		System.out.println("Client Received: "+arg1.toString(CharsetUtil.UTF_8));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable t){
		t.printStackTrace();
		ctx.close();
	}
}
