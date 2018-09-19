package pro.jing.zk.api.curator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author JING
 * @date 2018年9月7日
 * @describe LeaderLacth，支持监听成功失败
 */
public class CuratorLeaderLatchAPI {

	private static final int SECOND = 1000;

	public static void main(String[] args) throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 3; i++) {
			final int index = i;
			service.submit(new Runnable() {
				public void run() {
					try {
						new CuratorLeaderLatchAPI().schedule(index);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		Thread.sleep(10 * SECOND);
		service.shutdownNow();
	}

	private void schedule(final int thread) throws Exception {
		
		RetryPolicy rp = new ExponentialBackoffRetry(1 * SECOND, 3);
		// Fluent风格创建
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.1.46:2181")
				.sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
		client.start();
		System.out.println("Thread [" + thread + "] Server connected...");
		
//		CuratorFramework client = this.getStartedClient(thread);
		String path = "/leader_latch";
		if (client.checkExists().forPath(path) == null) {
			client.create().creatingParentsIfNeeded().forPath(path);
		}

		LeaderLatch latch = new LeaderLatch(client, path);
		latch.addListener(new LeaderLatchListener() {

			public void notLeader() {
				System.out.println("竞选失败。。。");
			}

			public void isLeader() {
				System.out.println("竞选成功。。。");
			}
		});

		latch.start();

		Thread.sleep(2 * (thread + 1) * SECOND);
		if (latch != null) {
			latch.close();
		}
		if (client != null) {
			client.close();
		}
		System.out.println("Thread [" + thread + "] Server closed...");
	}

}
