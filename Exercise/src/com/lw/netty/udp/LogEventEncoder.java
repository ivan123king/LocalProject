package com.lw.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 编码，加密
 * @author lenovo
 *
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent>{

	private final InetSocketAddress remoteAddress;
	public LogEventEncoder(InetSocketAddress remoteAddress){
		this.remoteAddress = remoteAddress;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, LogEvent event,
			List<Object> list) throws Exception {
		byte[] file = event.getLogfile().getBytes(CharsetUtil.UTF_8);
		byte[] msg = event.getMsg().getBytes(CharsetUtil.UTF_8);
		ByteBuf buf = ctx.alloc().buffer(file.length+msg.length);
		buf.writeBytes(file);//将文件名写入到ByteBuf中
		buf.writeByte(LogEvent.SEPARATOR);//写入一个分隔符
		buf.writeBytes(msg);//将日志消息写入ByteBuf
		//添加到出站消息列表中
		list.add(new DatagramPacket(buf,remoteAddress));
	}

}
