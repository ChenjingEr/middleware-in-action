package pro.jing.zk.api;

import java.util.List;

import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.github.zkclient.IZkChildListener;
import com.github.zkclient.IZkClient.DataUpdater;
import com.github.zkclient.IZkDataListener;
import com.github.zkclient.IZkStateListener;
import com.github.zkclient.ZkClient;

/**
 * @author JING
 * @date 2018年9月6日
 * @describe ZkClient API
 */
public class ZkclientAPI {

	public static void main(String[] args) {

		// 创建客户端
		ZkClient zkClient = new ZkClient("192.168.1.46:2181");
		System.out.println("连接到zk服务器...");

		// 创建节点
		String root = "/zkroot";

		zkClient.subscribeStateChanges(new IZkStateListener() {

			@Override
			public void handleStateChanged(KeeperState state) throws Exception {
				System.out.println("state listener -> " + state);
			}

			@Override
			public void handleNewSession() throws Exception {
				System.out.println("state new session");

			}
		});

		// 添加监听器
		zkClient.subscribeDataChanges(root, new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("listener [" + dataPath + " -> deleted....]");
			}
			
			@Override
			public void handleDataChange(String dataPath, byte[] data) throws Exception {
				System.out.println("listener [" +dataPath + "  data change to " + new String(data) + "]");
			}
		});

		// 查看节点是否存在,不存在就创建
		if (!zkClient.exists(root)) {
			zkClient.createPersistent(root);
		}

		// 写入数据
		if (zkClient.exists(root)) {
			String data = "upup";
			zkClient.writeData(root, data.getBytes());
		}

		// 读取数据
		byte[] data = zkClient.readData(root);
		System.out.println("read date -> " + new String(data));
		
		//更改节点
		zkClient.writeData(root, "upup going".getBytes());
		
		// 删除节点
		zkClient.delete(root);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		zkClient.close();

	}
}
