package pro.jing.nettyqi.nettydemo.datapack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author JING
 * @date 2018年8月18日
 * @describe netty demo
 */
public class TimeServer {

	public void bind(int port) throws InterruptedException {
		// 设置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			// ServerBootstrap服务端的启动辅助类，设置线程组，设置channel为NioServerSockerChannel(->ServerSocerChannle),设置TCP参数，设置IO时间处理器
			ServerBootstrap b = new ServerBootstrap();

			b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChildChannelHandler());
			//sync()同步等待绑定完成
			ChannelFuture f = b.bind(port).sync();
			
			//等待服务器监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();

		}
	}

	//IO事件处理器，用于处理网络IO事件(记录日志，消息的编解码等)
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new TimeServerHandler());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		int port = 7474;
		new TimeServer().bind(port);
	}
}
