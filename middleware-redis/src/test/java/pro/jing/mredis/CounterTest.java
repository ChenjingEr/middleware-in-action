package pro.jing.mredis;

import org.junit.Test;

import pro.jing.mredis.inaction.counter.Counter;

public class CounterTest {

	@Test
	public void testCounter() {
		String key = "jing:counter";
		String host = "192.168.1.46";
		Integer port = 6379;
		Counter counter = new Counter(key, host, port);
		
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					counter.incrementAndGet();
				}
			}).start();;
		}
	
		System.out.println(counter.getNow());
		counter.getJedis().close();
		
	}
}
