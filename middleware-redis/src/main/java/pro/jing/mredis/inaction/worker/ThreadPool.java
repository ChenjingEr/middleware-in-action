package pro.jing.mredis.inaction.worker;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pro.jing.mredis.inaction.request.Request;
import pro.jing.mredis.inaction.request.RequestQueue;


public class ThreadPool {

	private ExecutorService threadPool = Executors.newFixedThreadPool(10);
	
	public ThreadPool() {
		RequestQueue requestQueue = RequestQueue.getRequestQueue();

		for (int i = 0; i < 10; i++) {
			// 队列与worker绑定
			ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(100);
			requestQueue.addQueue(queue);
			threadPool.submit(new Worker(queue));
		}
	}
}
