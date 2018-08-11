package pro.jing.mredis.inaction.request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author JING
 * @Date 2018年8月6日
 * @description 将请求放入队列，不同的ID对应的是不同的队列，由不同的线程处理
 */
public class RequestQueue {

	// 内存队列
	private List<ArrayBlockingQueue<Request>> queue = new ArrayList<ArrayBlockingQueue<Request>>();

	private RequestQueue() {
	}

	public static RequestQueue getRequestQueue() {
		return RequestQueueSingleton.getRequestQueue();
	}

	public void addQueue(ArrayBlockingQueue<Request> queue) {
		this.queue.add(queue);
	}

	public Integer getQueueSize() {
		return queue.size();
	}

	// 应用启动到结束，内存队列只需要存在一个
	private static class RequestQueueSingleton {

		private static RequestQueue reqeustQueue;

		static {
			reqeustQueue = new RequestQueue();
		}

		public static RequestQueue getRequestQueue() {
			return reqeustQueue;
		}
	}

}
