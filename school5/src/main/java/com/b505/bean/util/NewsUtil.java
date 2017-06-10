package com.b505.bean.util;

import java.util.Date;

public class NewsUtil {
	private Integer id;
	private String theme;
	private Date reDate;
	private String author;
	private String url;
	private String content;
	private String checker;
	public NewsUtil(Integer id,String theme,String author,String checker,Date reDate ,String url){
		super();
		this.id=id;
		this.theme = theme;
		this.author=author;
		this.checker = checker;
		this.reDate = reDate;
		this.url = url;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public Date getReDate() {
		return reDate;
	}
	public void setReDate(Date reDate) {
		this.reDate = reDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "NewsUtil [id=" + id + ", theme=" + theme + ", reDate=" + reDate
				+ ", author=" + author + ", url=" + url + ", content="
				+ content + ", checker=" + checker + "]";
	}
	
}
