package pro.jing.mredis.inaction.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Subscriber {

	public static void main(String[] args) {
		String host = "192.168.1.46";
		Integer port = 6379;
		String channel = "test";
		Jedis jedis = new Jedis(host, port);
		jedis.subscribe(new JedisPubSub() {

			@Override
			public void onMessage(String channel, String message) {
				System.out.println("subscriber -> " + channel + " -> " + message);
			}

			@Override
			public void subscribe(String... channels) {
				System.out.println("subscriber -> " +channels);
			}

		}, channel);
		
		jedis.close();
	}

}
