package com.lw.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * 日志事件解码器
 * @author lenovo
 *
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {

	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket packet,
			List<Object> list) throws Exception {
		ByteBuf data = packet.content();
		//获取LogEvent.SEPARATOR 的索引
		int idx = data.indexOf(0,data.readableBytes(),LogEvent.SEPARATOR);
		//提取文件名
		String fileName = data.slice(0,idx).toString(CharsetUtil.UTF_8);
		//提取最新信息
		String logMsg = data.slice(idx+1,data.readableBytes()).toString(CharsetUtil.UTF_8);
		LogEvent event = new LogEvent(packet.sender(),
					System.currentTimeMillis(),fileName,logMsg);
		list.add(event);
	}

	
}
