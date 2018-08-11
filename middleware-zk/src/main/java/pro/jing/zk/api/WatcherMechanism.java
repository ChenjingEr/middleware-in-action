package pro.jing.zk.api;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class WatcherMechanism {

	private ZooKeeper zk = null;
	private CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public WatcherMechanism() {
		init();
	}

	public void init() {
		try {
			// 注册一个默认的Watcher
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

	public void create(String path) {

		try {
			String crePath = zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("create ->" +  crePath);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void get(String path) {
		
		try {
			Stat stat = new Stat();
			zk.getData(path, new Watcher() {
				
				@Override
				public void process(WatchedEvent event) {
					System.out.println(event);
					
				}
			}, stat);
			
			System.out.println("get -> " + stat);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void modify(String path) {
		try {
			Stat stat = zk.setData(path, "01".getBytes(), -1);
			System.out.println("modify event -> " + stat);
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deelte(String path) {
		
		try {
			zk.delete(path, -1);
		} catch (InterruptedException | KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		WatcherMechanism wm = new WatcherMechanism();
		String path = "/watcher";
		wm.create(path);
		wm.get(path);
		wm.modify(path);
		wm.deelte(path);
	}
}
