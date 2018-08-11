package pro.jing.mredis.inaction.dao;

import pro.jing.mredis.inaction.model.Product;

public interface IRedisDao {
	
	/**
	 * 保存缓存
	 * @param product
	 */
	public void save(Product product);
	
	/**
	 * 删除缓存
	 * @param product
	 */
	public void delete(Product product);
	
	/**
	 * 获取缓存中的product
	 * @param id
	 * @return
	 */
	public Product getById(Integer id);
}
