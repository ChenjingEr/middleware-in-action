package pro.jing.zk.api.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author JING
 * @date 2018年9月7日
 * @describe 监听Child节点，且监听器不必重复注册(Zookeeper的Wather是一次性的)
 */
public class CuratorListenerPathCacheAPI {

	private static final int SECOND = 1000;

	public static void main(String[] args) throws Exception {
		
		RetryPolicy rp = new ExponentialBackoffRetry(1 * SECOND, 3);
		// Fluent风格创建
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.1.46:2181")
				.sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
		client.start();

		// 节点
		String root = "/listener";
		String data = "upup";
		String updateData = "upup going";

		// Fluent风格创建
		PathChildrenCache pathCache = null;

		try {

			// PathCache 监听
			pathCache = new PathChildrenCache(client, root, true);
			pathCache.start();
			pathCache.getListenable().addListener(new PathChildrenCacheListener() {

				public void childEvent(CuratorFramework cf, PathChildrenCacheEvent event) throws Exception {
					switch (event.getType()) {
					case INITIALIZED:
					case CONNECTION_RECONNECTED:
					case CONNECTION_SUSPENDED:
					case CONNECTION_LOST:
						System.out.println("[Callback]Event [" + event.getType().toString() + "] ");
						break;
					case CHILD_ADDED:
					case CHILD_REMOVED:
					case CHILD_UPDATED:
						System.out.println("[Callback]Event [" + event.getType().toString() + "] Path ["
								+ event.getData().getPath() + "] data change to :"
								+ new String(event.getData().getData()));
						break;
					default:
						System.out.println("[Callback]Event [Error] ");
						break;
					}

				}
			});
			System.out.println("Listener added success...");

			Thread.sleep(1 * SECOND);
			if (client.checkExists().forPath(root) == null) {
				// 创建节点
				client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(root,
						data.getBytes());
				System.out.println("Created node [" + root + "] with data [" + data + "]");
			}


			Thread.sleep(1 * SECOND);
			if (client.checkExists().forPath(root) != null) {
				// 设置节点内容
				client.setData().forPath(root, updateData.getBytes());
				System.out.println("Set data to node [" + root + "] data : " + updateData);
			}

			Thread.sleep(1 * SECOND);
			if (client.checkExists().forPath(root) != null) {
				// 强制删除节点
				client.delete().guaranteed().forPath(root);
				System.out.println("Delete node [" + root + "].");
			}

			Thread.sleep(1 * SECOND);
			if (client.checkExists().forPath(root) != null) {
				// 递归删除节点
				client.delete().deletingChildrenIfNeeded().forPath(root);
				System.out.println("Delete node [" + root + "] use recursion.");
			}

		} finally {
			Thread.sleep(2 * SECOND);
			if (pathCache != null) {
				pathCache.close();
			}
			if (client != null) {
				client.close();
			}
			System.out.println("Server closed...");
		}
	}

}