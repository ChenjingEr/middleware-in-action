package pro.jing.natty.nettyapi.doc01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author JING
 * @date 2018年9月19日
 * @describe 实现一个DiscardServer ChannelInboundHandlerAdapter 是Channel处理器
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

	// This method is called with the received message
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		try {
			while (in.isReadable()) { // (1)
				System.out.print((char) in.readByte());
				System.out.flush();
			}
		} finally {
			// Please keep in mind that it is the handler's
			// responsibility to release any reference-counted object passed to the handler.
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
