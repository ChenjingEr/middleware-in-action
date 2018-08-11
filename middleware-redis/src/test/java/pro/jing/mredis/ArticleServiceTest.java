package pro.jing.mredis;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import pro.jing.mredis.model.Article;
import pro.jing.mredis.service.ArticleService;

public class ArticleServiceTest extends TestCase {
	
	private ArticleService service;
	
	@Before
	public void setUp() {
		service = new ArticleService();
	}

	@Test
	public void testCreateArticle() {
		Article article = new Article(2, "故事01", new Date().getTime(), "这是一个不恐怖的鬼故事01", 1);
		service.createArticle(article);
	}
	
	@Test
	public void testGetArticle() {
		service.getArticleList(1, 3);
	}
	
	@Test
	public void testAccessCount() {
		for (int i = 0; i < 10; i++) {
			service.accessCountIncr(1);
		}
		
	}
	
}
