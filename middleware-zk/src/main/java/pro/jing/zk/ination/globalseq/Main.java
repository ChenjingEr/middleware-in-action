package pro.jing.zk.ination.globalseq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.zkclient.ZkClient;

public class Main {

	public static void main(String[] args) {

		ExecutorService ex = Executors.newFixedThreadPool(20);
		ZkClient zkClient = new ZkClient("192.168.1.46:2181");
		String seqPath = "/seq";
		for (int i = 0; i < 100; i++) {
			ex.execute(new GlobalSequenceGenerate("task " + i, zkClient, seqPath));
		}
		while (!ex.isTerminated()) {
		}
		zkClient.close();
	}
}
