package pro.jing.mredis.inaction.dao;

import pro.jing.mredis.inaction.model.Product;

public class RedisDaoImpl implements IRedisDao {

	public void save(Product product) {

		System.out.println("保存product缓存。。。");
		try {
			// 模拟保存操作耗时
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("保存product缓存成功");

	}

	public void delete(Product product) {
		System.out.println("删除product缓存。。。");
		try {
			// 模拟删除操作耗时
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("删除product缓存成功");
	}

	public Product getById(Integer id) {
		int rand = (int) (Math.random() * 100);
		if (rand % 2 == 0) {
			System.out.println("缓存命中。。。");
			Product p = new Product();
			return p;
		}
		return null;
	}

}
