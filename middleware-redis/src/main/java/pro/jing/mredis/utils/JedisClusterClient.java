package pro.jing.mredis.utils;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterClient {

	private static JedisCluster jedisCluster;

	static {
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort("192.168.1.4", 7005));
		jedisClusterNodes.add(new HostAndPort("192.168.1.62", 7007));
		jedisClusterNodes.add(new HostAndPort("192.168.1.64", 7003));
		jedisCluster = new JedisCluster(jedisClusterNodes);
	}

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

}
