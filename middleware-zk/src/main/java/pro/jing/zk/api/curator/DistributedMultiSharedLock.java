package pro.jing.zk.api.curator;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class DistributedMultiSharedLock {

	private static final int SECOND = 1000;

	public static void main(String[] args) throws InterruptedException {
		RetryPolicy rp = new ExponentialBackoffRetry(1 * SECOND, 3);
		// Fluent风格创建
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.1.46:2181")
				.sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
		client.start();
		System.out.println("Server connected...");

		String path1 = "/curator_lock/multi_shared_lock1";
		String path2 = "/curator_lock/multi_shared_lock2";
		String path3 = "/curator_lock/multi_shared_lock3";

		final InterProcessMultiLock lock = new InterProcessMultiLock(client,
				Arrays.asList(new String[] { path1, path2, path3 }));
		final CountDownLatch down = new CountDownLatch(1);
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						down.await();
						lock.acquire();
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
						String orderNo = sdf.format(new Date());
						System.out.println("生成的订单号是:" + orderNo);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							lock.release();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		// 保证所有线程内部逻辑执行时间一致
		down.countDown();
		Thread.sleep(10 * SECOND);
		if (client != null) {
			client.close();
		}
		System.out.println("Server closed...");
	}
}
