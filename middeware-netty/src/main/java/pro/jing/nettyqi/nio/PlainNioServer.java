package pro.jing.nettyqi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author JING
 * @date 2018年8月15日
 * @describe
 */
public class PlainNioServer {

	public void serve(int port) throws IOException {

		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		ServerSocket ssocket = serverChannel.socket();
		InetSocketAddress address = new InetSocketAddress(port);
		ssocket.bind(address);

		Selector selector = Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		final ByteBuffer msg = ByteBuffer.wrap("Hi".getBytes());

		// 等待处理新事件
		for (;;) {
			try {
				// 等待需要传入的数据；阻塞，一直到有事件发生
				selector.select();

			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

			Set<SelectionKey> readKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = readKeys.iterator();
			while (it.hasNext()) {
				SelectionKey key = it.next();
				it.remove();

				try {
					if (key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();
						client.configureBlocking(false);
						client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
						System.out.println("Accpeted Connection From " + client);

						if (key.isWritable()) {
							SocketChannel c = (SocketChannel) key.channel();
							ByteBuffer buffer = (ByteBuffer) key.attachment();
							while (buffer.hasRemaining()) {
								if (c.write(buffer) == 0) {
									break;
								}
							}
							client.close();
						}
					}

				} catch (IOException ex) {
					ex.printStackTrace();
					key.cancel();
					key.channel().close();
				} finally {
				}
			}
		}
	}
}
