package com.lw.netty.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 日志事件处理器，解码并打印出最新的日志内容
 * @author lenovo
 *
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LogEvent event)
			throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append(event.getReceived());
		builder.append("[");
		builder.append(event.getSource().toString());
		builder.append("] [");
		builder.append(event.getLogfile());
		builder.append("] :");
		builder.append(event.getMsg());
		System.out.println(builder.toString());//打印LogEvent的数据
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable t){
		t.printStackTrace();
		ctx.close();
	}
	
}
