package pro.jing.zk.api.curator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author JING
 * @date 2018年9月7日
 * @describe 分布式锁
 */
public class DistirbutedLock {

	private static final int SECOND = 1000;

	public static void main(String[] args) throws InterruptedException {
		RetryPolicy rp = new ExponentialBackoffRetry(1 * SECOND, 3);
		// Fluent风格创建
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.1.46:2181")
				.sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
		client.start();
		System.out.println("Server connected...");

		String path = "/curator_lock/shared_lock";
		final InterProcessSemaphoreMutex lock = new InterProcessSemaphoreMutex(client, path);
		final CountDownLatch down = new CountDownLatch(1);

		for (int i = 0; i < 30; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						down.await();
						// 非重入锁 - 只可以获取一次
						lock.acquire();
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
						String orderNo = sdf.format(new Date());
						System.out.println("生成的订单号是:" + orderNo);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							// 由于获取一次，所以释放一次
							lock.release();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		down.countDown();
		Thread.sleep(10 * SECOND);
		if (client != null) {
			client.close();
		}
		System.out.println("Server closed...");

	}
}
