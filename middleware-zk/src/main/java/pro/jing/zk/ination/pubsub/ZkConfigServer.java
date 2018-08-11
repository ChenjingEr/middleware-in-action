package pro.jing.zk.ination.pubsub;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ZkConfigServer {

	private ZooKeeper zk;

	public ZkConfigServer(String connectionPath) {
		try {
			zk = new ZooKeeper(connectionPath, 2000, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void create(String path, byte[] data) {
		try {
			Stat stat = zk.exists(path, true);
			System.out.println(stat);
			if (stat != null) {
				zk.delete(path, -1);
			}
			String pt = zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println(pt + " 创建了");
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void modify(String path, byte[] data) {
		try {
			Stat stat = zk.exists(path, true);
			if (stat != null) {
				zk.setData(path, data, stat.getVersion());
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete(String path) {
		try {
			Stat stat = zk.exists(path, false);
			if (stat != null) {
				zk.delete(path, -1);
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
