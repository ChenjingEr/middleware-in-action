package pro.jing.zk.api.curator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author JING
 * @date 2018年9月7日
 * @describe 分布式信号量
 */
public class DistributedSemaphore {

	private static final int SECOND = 1000;

	public static void main(String[] args) throws InterruptedException {
		RetryPolicy rp = new ExponentialBackoffRetry(1 * SECOND, 3);
		// Fluent风格创建
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
				.sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
		client.start();
		System.out.println("Server connected...");

		String path = "/curator_lock/shared_semaphore_lock";

		final InterProcessSemaphoreV2 lock = new InterProcessSemaphoreV2(client, path, 3);
		final CountDownLatch down = new CountDownLatch(1);
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {
				public void run() {
					Lease lease = null;
					try {
						down.await();
						// 排队等候分配资源
						lease = lock.acquire();
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
						String orderNo = sdf.format(new Date());
						System.out.println("生成的订单号是:" + orderNo);
						Thread.sleep(1 * SECOND);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							if (lease != null) {
								// 归还正在使用的资源
								lock.returnLease(lease);
							}
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
