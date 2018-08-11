package pro.jing.mredis.model;

public class Article {

	/**
	 * 文章ID
	 */
	private Integer id;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章更新时间 毫秒级别
	 */
	private long updateTime;
	/**
	 * 文章描述
	 */
	private String desc;
	/**
	 * 作者ID
	 */
	private Integer userId;

	public Article() {
	}

	public Article(Integer id, String title, long updateTime, String desc, Integer userId) {
		super();
		this.id = id;
		this.title = title;
		this.updateTime = updateTime;
		this.desc = desc;
		this.userId = userId;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
