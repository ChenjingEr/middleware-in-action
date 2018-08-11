package pro.jing.zk.api;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooDefs.Ids;

public class ApiTest {

	private ZooKeeper zk = null;
	private CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public ApiTest() {
		init();
	}

	public void init() {
		try {
			zk = new ZooKeeper("192.168.1.46:2181", 5000, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					System.out.println(event);
					if (KeeperState.SyncConnected == event.getState()) {
						connectedSemaphore.countDown();
					}
				}
			});
			connectedSemaphore.await();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void create() {

		try {
			// 同步创建
			String path1 = zk.create("/api-test01", "qiqi".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			byte[] data01 = zk.getData(path1, null, new Stat());
			System.out.println(path1 + " -> " + new String(data01));

//			String path2 = zk.create("/api-test02", "qiqi".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//			byte[] data02 = zk.getData(path2, null, new Stat());
//			System.out.println(path2 + " -> " + new String(data02));
//
//			String path3 = zk.create("/api-test03", "qiqi".getBytes(), Ids.OPEN_ACL_UNSAFE,
//					CreateMode.PERSISTENT_SEQUENTIAL);
//			byte[] data03 = zk.getData(path3, null, new Stat());
//			System.out.println(path3 + " -> " + new String(data03));
//
//			String path4 = zk.create("/api-test04", "qiqi".getBytes(), Ids.OPEN_ACL_UNSAFE,
//					CreateMode.EPHEMERAL_SEQUENTIAL);
//			byte[] data04 = zk.getData(path4, null, new Stat());
//			System.out.println(path4 + " -> " + new String(data04));

			// 异步创建

		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createSequential() {
		try {
			

			String path = zk.create("/seq", "qiqi".getBytes(), Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT_SEQUENTIAL);
			byte[] data = zk.getData(path, null, new Stat());
			System.out.println(path + " -> " + new String(data));
			
			
			String path1 = zk.create("/seq/num", "qiqi".getBytes(), Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT_SEQUENTIAL);
			byte[] data1 = zk.getData(path1, null, new Stat());
			System.out.println(path1 + " -> " + new String(data1));
			
			
			String path2 = zk.create("/seq/num", "qiqi".getBytes(), Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT_SEQUENTIAL);
			byte[] data02 = zk.getData(path2, null, new Stat());
			System.out.println(path2 + " -> " + new String(data02));
			
			String path3 = zk.create("/seq/num", "qiqi".getBytes(), Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT_SEQUENTIAL);
			byte[] data03 = zk.getData(path3, null, new Stat());
			System.out.println(path3 + " -> " + new String(data03));
			
			List<String> children = zk.getChildren("/seq", false);
			System.out.println(children);
			
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete() {

	}
	
	public void get(String path) {
		try {
			byte[] data = zk.getData(path, true, new Stat());
			System.out.println(new String(data));
			List<String> children = zk.getChildren(path, true);
			System.out.println(children);
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		ApiTest test = new ApiTest();
		test.create();
//		while (true) {
//		}
	}
}
