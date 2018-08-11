package pro.jing.zk.ination.pubsub;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZkWorkServer {

	private ZooKeeper zk;

	public ZkWorkServer(String connectionString) {
		try {
			zk = new ZooKeeper(connectionString, 2000, new Watcher() {

				@Override
				public void process(WatchedEvent event) {
					//貌似只是生效了一次
					System.out.println("workder server get event ->" + event);
//					if ("/config".equals(event.getPath())
//							|| Watcher.Event.EventType.NodeDataChanged.equals(event.getType())) {
//						try {
//							byte[] data = zk.getData(event.getPath(), true, new Stat());
//							System.out.println(new String(data));
//						} catch (KeeperException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}

				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
