package pro.jing.mredis.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.jing.mredis.model.Article;
import pro.jing.mredis.utils.RedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

/**
 * @author JING
 * @Date 2018年7月25日
 * @description 文章操作
 */
public class ArticleService {

	/**
	 * 创建文章,用hash保存到redis key: article:articleId:userId
	 */
	public void createArticle(Article article) {
		Jedis jedis = RedisClient.getRedisClient();

		String key = "article:" + article.getId() + ":" + article.getUserId();

		Map<String, String> articleMap = new HashMap<String, String>();
		articleMap.put("title", article.getTitle());
		articleMap.put("desc", article.getDesc());
		articleMap.put("updateTime", String.valueOf(article.getUpdateTime()));
		jedis.hmset(key, articleMap);
		jedis.rpush("article:" + article.getUserId(), String.valueOf(article.getId()));

		jedis.close();
	}

	public void getArticleList(Integer userId, Integer size) {
		Jedis jedis = RedisClient.getRedisClient();
		SortingParams sortingParameters = new SortingParams();
		sortingParameters.by("article:*:" + userId + "->updateTime").desc().limit(0, size)
				.get("article:*:" + userId + "->title").get("article:*:" + userId + "->updateTime")
				.get("article:*:" + userId + "->desc").get("#");
		List<String> result = jedis.sort("article:" + userId, sortingParameters);
		System.out.println(result);
		jedis.close();
	}

	public void accessCountIncr(Integer id) {
		Jedis jedis = RedisClient.getRedisClient();
		jedis.incr("Article:" + id);
		System.out.println("文章" + id + "访问量:" + jedis.get("Article:" + id));
	}
}
