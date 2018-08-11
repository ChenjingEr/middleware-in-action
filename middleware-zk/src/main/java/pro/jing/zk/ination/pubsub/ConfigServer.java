package pro.jing.zk.ination.pubsub;

import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.github.zkclient.IZkStateListener;
import com.github.zkclient.ZkClient;

public class ConfigServer {

	private ZkClient zkClient;

	public ConfigServer(ZkClient zkClient) {
		this.zkClient = zkClient;
		this.zkClient.subscribeStateChanges(new IZkStateListener() {

			@Override
			public void handleStateChanged(KeeperState state) throws Exception {
				System.out.println(state);
			}

			@Override
			public void handleNewSession() throws Exception {

			}
		});
	}

	public void create(String dataPath, byte[] config) {

		if (zkClient.exists(dataPath))
			zkClient.deleteRecursive(dataPath);

		zkClient.createPersistent(dataPath, config);
		System.out.println(zkClient.readData(dataPath));

	}

	public void delete(String dataPath) {
		zkClient.delete(dataPath);
	}

	public void modify(String dataPath, byte[] config) {
		zkClient.writeData(dataPath, config);
	}
}
