package pro.jing.nettyqi.oio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author JING
 * @date 2018年8月15日
 * @describe 阻塞通信JDK API
 */
public class PlainOioServer {

	public void serve(int port) throws IOException {
		final ServerSocket socket = new ServerSocket(port);
		try {
			// 无限循环监听
			for (;;) {
				final Socket clientSocket = socket.accept();
				System.out.println("Accepted connection from " + clientSocket);
				// 开启一个线程处理IO
				new Thread(new Runnable() {

					@Override
					public void run() {
						OutputStream out;
						try {
							out = clientSocket.getOutputStream();
							out.write("hi".getBytes(Charset.forName("UTF-8")));
							out.flush();
							clientSocket.close();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								clientSocket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				}).start();
			}
		} finally {
			socket.close();
		}
	}

}
