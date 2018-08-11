
package pro.jing.zk.ination.pubsub;

import com.github.zkclient.ZkClient;

/**
 * 
 * @author JING
 * @Date 2018年8月9日
 * @description 简单实现发布订阅功能
 */
public class Main {

	public static void main(String[] args) {
		String serverPath = "192.168.1.46:2181";
		ZkClient zkClient = new ZkClient(serverPath);
		ZkClient zkClient01 = new ZkClient(serverPath);
		String dataPath = "/config";
		String config = "qiqi";
		ConfigServer cfgServer = new ConfigServer(zkClient);
		WorkServer workServer = new WorkServer(zkClient01, dataPath);
		workServer.start();
		cfgServer.create(dataPath, config.getBytes());
		String config2 = "qiqi02";
		cfgServer.modify(dataPath, config2.getBytes());
		cfgServer.delete(dataPath);

	}
}
