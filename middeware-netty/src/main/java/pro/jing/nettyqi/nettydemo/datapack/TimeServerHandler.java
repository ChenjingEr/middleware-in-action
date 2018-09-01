package pro.jing.nettyqi.nettydemo.datapack;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author JING
 * @date 2018年8月18日
 * @describe netty 的半包及粘包解决方案
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

	private int counter;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {

		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);

		String body = new String(req, "UTF-8").substring(0, req.length - System.getProperty("line.separator").length());

		System.out.println("The time server receive order : " + body + " the counter is " + (++counter));
		String currentTime = "Query Time Order".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString()
				: "Bad Order";
		currentTime = currentTime + System.getProperty("line.separator");
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		// write将待发送的消息放到发送缓存数值中，flush()将发送缓冲区的消息全部写到SocketChannel中
		ctx.writeAndFlush(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 将消息发送队列中的消息写入到SocketChannel中发送给对方
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
