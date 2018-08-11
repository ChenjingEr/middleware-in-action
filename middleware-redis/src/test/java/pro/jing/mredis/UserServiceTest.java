package pro.jing.mredis;

import org.junit.Before;
import org.junit.Test;

import pro.jing.mredis.model.User;
import pro.jing.mredis.service.UserService;
import pro.jing.mredis.utils.RedisClient;

public class UserServiceTest {
	
	private UserService service;
	
	@Before 
	public void setUp() {
		service = new UserService(RedisClient.getRedisClient());
	}

	@Test
	public void testCreate() {
		User user = new User(1, "qiqi01");
		service.createUser(user);
		User user01 = new User(2, "qiqi02");
		service.createUser(user01);
	}
	
	@Test
	public void getUse() {
		service.getUser(1);
		service.getUser(2);
	}
	
	@Test
	public void testSetFollowList() {
		service.setFollow(1, 2);
	}
	
	@Test
	public void testGetFollowList() {
		service.getFollow(1);
		service.getFollow(2);
	}
}
