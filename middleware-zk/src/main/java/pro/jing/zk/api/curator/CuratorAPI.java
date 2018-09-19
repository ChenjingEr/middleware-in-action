package pro.jing.zk.api.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author JING
 * @date 2018年9月6日
 * @describe Curator API
 */
public class CuratorAPI {

	public static void main(String[] args) throws Exception {
		Integer SECOND = 1000;
		String root = "/curator_api";
		String data = "upup";

		// 创建连接
		RetryPolicy rp = new ExponentialBackoffRetry(1 * SECOND, 3);
		CuratorFramework cfFluent = CuratorFrameworkFactory.builder().connectString("192.168.1.46:2181")
				.sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
		cfFluent.start();
		System.out.println("Server connected...");

		// 设置监听
		cfFluent.getCuratorListenable().addListener(new CuratorListener() {

			@Override
			public void eventReceived(CuratorFramework cf, CuratorEvent event) throws Exception {
				System.out.println("Curator framework operations : " + event.getType().toString());
			}
		});

		// 添加连接信息监听事件
		cfFluent.getConnectionStateListenable().addListener(new ConnectionStateListener() {
			@Override
			public void stateChanged(CuratorFramework arg0, ConnectionState arg1) {
				System.out.println("Connection state changed to : " + arg1.name());
			}
		});
		System.out.println("Listener added success...");

		if (cfFluent.checkExists().forPath(root) == null) {
			// 创建节点
			cfFluent.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(root, data.getBytes());
//			System.out.println("Created node [" + root + "] with data [" + data + "]");
			
			//获取节点
			Stat stat = new Stat();
			cfFluent.getData().storingStatIn(stat).forPath(root);
			System.out.println(stat.toString());
			
			//更改节点
			cfFluent.setData().forPath(root,"upupgong".getBytes());
			System.out.println(cfFluent.getData().storingStatIn(stat).forPath(root));
			
			//删除节点
			cfFluent.delete().forPath(root);
		}
		
		Thread.sleep(1000);

	}
}
