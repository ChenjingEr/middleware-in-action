package pro.jing.mredis.inaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.jing.mredis.inaction.dao.IProductDao;
import pro.jing.mredis.inaction.dao.IRedisDao;
import pro.jing.mredis.inaction.model.Product;
import pro.jing.mredis.inaction.request.Request;
import pro.jing.mredis.inaction.request.UpdateRequest;

public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IRedisDao redisDao;

	public void saveProduct(Product product) {
		
	}

	public void updateProduct(Product product) {
		//封装一个UpdateRequest，放到队列
		
		Request request = new UpdateRequest(product);
		
		
	}

	public Product getById(Integer id) {
		//先查缓存中是否存在
		Product product = redisDao.getById(id);
		if (product == null) {
			//封装一个QueryRequest放到队列
			
		}
		
		
		return product;
	}

}
