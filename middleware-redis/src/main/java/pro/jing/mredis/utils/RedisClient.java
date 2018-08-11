package pro.jing.mredis.utils;

import redis.clients.jedis.Jedis;

/**
 * @author JING
 * @Date 2018年7月26日
 * @description 连接客户端 api实现
 */
public class RedisClient {

	private static Jedis jedis;

	static {
		jedis = new Jedis("192.168.1.46", 6379);
	}

	private RedisClient() {
	}

	public static Jedis getRedisClient() {
		return jedis;
	}
}
