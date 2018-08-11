package pro.jing.zk.ination.pubsub;

import com.github.zkclient.IZkDataListener;
import com.github.zkclient.ZkClient;

/**
 * 
 * @author JING
 * @Date 2018年8月9日
 * @description 工作线程，监听data变化
 */
public class WorkServer {

	private ZkClient zkClient;
	private IZkDataListener dataListener;
	private String dataPath;

	public WorkServer(ZkClient zkClient,String dataPath) {
		this.zkClient = zkClient;
		this.dataPath = dataPath;
		this.dataListener = new IZkDataListener() {

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println(dataPath + " delete..");

			}

			@Override
			public void handleDataChange(String dataPath, byte[] data) throws Exception {
				System.out.println(dataPath + " -> " + new String(data) + "  changed..");
			}
		};
	}

	public void start() {
		zkClient.subscribeDataChanges(dataPath, dataListener);

	}

	public void stop() {
		zkClient.unsubscribeDataChanges(dataPath, dataListener);
	}
}
