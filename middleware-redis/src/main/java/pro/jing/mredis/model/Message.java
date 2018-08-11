package pro.jing.mredis.model;

public class Message {

	private Integer autherId;
	private String articleTitle;

	public Message(Integer autherId, String articleTitle) {
		this.autherId = autherId;
		this.articleTitle = articleTitle;
	}

	public Integer getAutherId() {
		return autherId;
	}

	public void setAutherId(Integer autherId) {
		this.autherId = autherId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	@Override
	public String toString() {
		return autherId + ";" + articleTitle;
	}

}
