package pro.jing.zk.ination.globalseq;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.github.zkclient.ZkClient;

/**
 * @author JING
 * @Date 2018年8月10日
 * @description 全局唯一序号生成器
 */
public class GlobalSequenceGenerate implements Runnable {

	private ZkClient zkClient;
	private String threadName;
	private String seqPath;

	public GlobalSequenceGenerate(String threadName, ZkClient zkClient, String seqPath) {
		this.threadName = threadName;
		this.zkClient = zkClient;
		this.seqPath = seqPath;
	}

	@Override
	public void run() {
		//同步处理省略
		if (!zkClient.exists(seqPath)) {
			zkClient.create(seqPath, "".getBytes(), CreateMode.PERSISTENT);
		}
			
		Stat stat = zkClient.writeData(seqPath, new byte[0], -1);
		int seq = stat.getVersion();
		System.out.println(threadName + " obtain seq = " + seq);
	
	}

}
