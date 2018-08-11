package pro.jing.mredis.inaction.dao;

import pro.jing.mredis.inaction.model.Product;

public interface IProductDao {

	void save(Product product);

	void update(Product product);

	Product getById(Integer id);
}
