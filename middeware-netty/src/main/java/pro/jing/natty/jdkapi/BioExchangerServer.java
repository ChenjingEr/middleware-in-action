package pro.jing.natty.jdkapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author JING
 * @date 2018年9月5日
 * @describe 传统的JDK网络编程模型 服务端 阻塞IO
 */
public class BioExchangerServer {

    static ServerSocket serverSocket = null;

    public static void main(String[] args) {
        try {
            //1创建服务端对象。
            serverSocket = new ServerSocket(8868);
            //2,获取连接过来的客户端对象。
            Socket socket = serverSocket.accept();
            System.out.println("来自客户端【" + socket.getInetAddress().getHostAddress() + "】的连接");
            BufferedReader bufferedReader = null;
            BufferedReader bufferedReader_SystemIN = null;
            BufferedWriter bufferedWriter = null;
            //3，通过socket对象获取输入流，要读取客户端发来的数据
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //4，通过字符输入流获取键盘输入，要读取控制台写给客户端的数据
            bufferedReader_SystemIN = new BufferedReader(new InputStreamReader(System.in));
            //5.使用客户端socket对象的输出流给客户端返回数据
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String message = "";
            while ((message = bufferedReader.readLine()) != null) {
                System.out.println("Client Say【" + socket.getInetAddress().getHostAddress() + "】" + message);
                System.out.print("请输入：");
                String s = bufferedReader_SystemIN.readLine();
                bufferedWriter.write(s);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
