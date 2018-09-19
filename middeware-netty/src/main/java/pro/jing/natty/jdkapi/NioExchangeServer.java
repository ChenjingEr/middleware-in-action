package pro.jing.natty.jdkapi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioExchangeServer {

	public static void main(String[] args) {
		ServerSocketChannel serverSocketChannel = null;
		try {
			// 1.打开一个通道
			serverSocketChannel = ServerSocketChannel.open();
			// 2.设置监听端口
			serverSocketChannel.socket().bind(new InetSocketAddress(7474));
			// 3.设置非阻塞
			serverSocketChannel.configureBlocking(false);
			// 4.注册到多路复用器上
			Selector selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_CONNECT);
			// 5.等待至少1个实现发生
			int select = selector.select();
			if (select > 0) {
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				while(it.hasNext()) {
					SelectionKey key = it.next();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
