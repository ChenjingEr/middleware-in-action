package pro.jing.mredis.inaction.request;

import org.springframework.beans.factory.annotation.Autowired;

import pro.jing.mredis.inaction.dao.IRedisDao;
import pro.jing.mredis.inaction.model.Product;

public class UpdateRequest implements Request{
	
	private Product product;
	

	public void process() {
		
	}

}
