package pro.jing.mredis.service;

import javax.swing.plaf.synth.SynthSpinnerUI;

import pro.jing.mredis.model.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class UserService {

	private Jedis jedis;

	public UserService(Jedis jedis) {
		this.jedis = jedis;
	}

	/**
	 * 创建用户
	 * 
	 * @param user
	 */
	public void createUser(User user) {
		jedis.set("user:" + user.getUserId(), user.getName());
	}

	/**
	 * 获取用户
	 * 
	 * @param id
	 */
	public void getUser(Integer id) {
		String userName = jedis.get("user:" + id);
		System.out.println("user : " + id + ", name :" + userName);
	}

	/**
	 * @param followee
	 *            关注人ID
	 * @param follower
	 *            被关注人ID 关注列表 & 被关注列表需要一起设置
	 */
	public void setFollow(Integer followee, Integer follower) {
		Transaction multi = jedis.multi();
		multi.rpush("followee:" + followee, String.valueOf(follower));
		multi.rpush("follower:" + follower, String.valueOf(followee));
		multi.exec();
	}

	public void getFollow(Integer user) {
		System.out.println("user : " + user + " 关注列表:" + jedis.lrange("followee:" + user, 0, 100));
		System.out.println("user : " + user + " 被关注列表:" + jedis.lrange("follower:" + user, 0, 100));
	}
}
