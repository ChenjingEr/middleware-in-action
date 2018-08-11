package pro.jing.mredis.push;

import pro.jing.mredis.model.Message;
import redis.clients.jedis.Jedis;

/**
 * @author JING
 * @Date 2018年7月25日
 * @description 任务生产者，发布新文章异步创建推送任务
 */
public class Producer {
	
	private Jedis jedis;
	
	public Producer() {
		
	}
	
	public Producer(Jedis jedis) {
		this.jedis = jedis;
	}

	public void producer(final Message msg) {
		new Thread(new Runnable() {
			
			public void run() {
				jedis.lpush("pusktask", msg.toString());				
			}
		}).start();
		
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}
	
	
}
