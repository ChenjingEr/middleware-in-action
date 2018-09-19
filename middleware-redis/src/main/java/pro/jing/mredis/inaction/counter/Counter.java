package pro.jing.mredis.inaction.counter;

import redis.clients.jedis.Jedis;

/**
 * @author JING
 * @date 2018年9月5日
 * @describe redis实现计数器功能
 */
public class Counter {

	private String key;
	private Jedis jedis;

	public Counter(String key, String host, Integer port) {
		this.key = key;
		jedis = new Jedis(host, port);
		jedis.set(key, String.valueOf(0));
	}

	public long incrementAndGet() {
		return jedis.incr(key);
	}
	
	public long getNow() {
		String c = jedis.get(key);
		return Long.valueOf(c);
	}

	public void reset() {
		jedis.set(key, String.valueOf(0));
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}
	
	

}
