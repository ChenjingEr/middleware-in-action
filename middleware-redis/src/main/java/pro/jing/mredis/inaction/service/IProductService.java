package pro.jing.mredis.inaction.service;

import pro.jing.mredis.inaction.model.Product;

public interface IProductService {

	void saveProduct(Product product);
	
	void updateProduct(Product product);
	
	Product getById(Integer id);
	
}
