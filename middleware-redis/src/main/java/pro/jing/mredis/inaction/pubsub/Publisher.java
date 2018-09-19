package pro.jing.mredis.inaction.pubsub;

import redis.clients.jedis.Jedis;

public class Publisher {

	public static void main(String[] args) {
		String host = "192.168.1.46";
		Integer port = 6379;
		String channel = "test";
		Jedis jedis = new Jedis(host,port);
		for (int i = 0; i < 10; i++) {
			jedis.publish(channel, "msg -> " + i);
			System.out.println("publish -> " + channel + " -> " + i);
		}
		jedis.close();
	}
}
