package pro.jing.nettyqi.netty.inaction.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks !", CharsetUtil.UTF_8));
	}

//	@Override
//	public void channelRead(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
//		System.out.println("Client received : " + in.toString(CharsetUtil.UTF_8));
//	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext arg0, ByteBuf arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
