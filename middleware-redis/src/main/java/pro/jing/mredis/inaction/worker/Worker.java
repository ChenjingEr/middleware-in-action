package pro.jing.mredis.inaction.worker;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

import pro.jing.mredis.inaction.request.Request;

public class Worker implements Callable<Boolean>{
	
	private ArrayBlockingQueue<Request> queue;
	
	public Worker(ArrayBlockingQueue<Request> queue) {
		this.queue = queue;
	}

	public Boolean call() throws Exception {
		while(true) {
			Request request = queue.take();
			request.process();
		}
	}

}
