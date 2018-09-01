package pro.jing.nettyqi.nettydemo;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {

		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");

		System.out.println("The time server receive order : " + body);
		String currentTime = "Query Time Order".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString()
				: "Bad Order";
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		//write将待发送的消息放到发送缓存数值中，flush()将发送缓冲区的消息全部写到SocketChannel中
		ctx.write(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//将消息发送队列中的消息写入到SocketChannel中发送给对方
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
