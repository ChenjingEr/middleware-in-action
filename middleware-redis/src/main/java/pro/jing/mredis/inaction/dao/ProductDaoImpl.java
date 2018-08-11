package pro.jing.mredis.inaction.dao;

import pro.jing.mredis.inaction.model.Product;

public class ProductDaoImpl implements IProductDao {

	public void save(Product product) {
		System.out.println("保存product数据库。。。");
		try {
			// 模拟保存操作耗时
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("保存product数据库成功");
	}

	public void update(Product product) {

		System.out.println("更新product数据库。。。");
		try {
			// 模拟保存操作耗时
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("更新product数据库成功");
	}

	public Product getById(Integer id) {
		return new Product();
	}

}
