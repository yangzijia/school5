package com.b505.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="news")
public class News implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String theme;
	private Date reDate;
	private String author;
	private String url;
	private String content;
	private String checker;
	private Section section;
	private List<NewsImage> newsImage = new ArrayList<NewsImage>();
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="theme")
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	@Column(name="releaseDate")
	public Date getReDate() {
		return reDate;
	}
	public void setReDate(Date reDate) {
		this.reDate = reDate;
	}
	@Column(name="author")
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Column(name="url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name="content",columnDefinition="text")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="checker")
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	@ManyToOne
	@JoinColumn(name="section_Id")
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	@OneToMany(cascade={CascadeType.REMOVE},mappedBy="news",fetch= FetchType.EAGER)
	public List<NewsImage> getNewsImage() {
		return newsImage;
	}
	@Override
	public String toString() {
		return "News [id=" + id + ", theme=" + theme + ", reDate=" + reDate
				+ ", author=" + author + ", url=" + url + ", content="
				+ content + ", checker=" + checker + ", section=" + section
				+ ", newsImage=" + newsImage + "]";
	}
	public void setNewsImage(List<NewsImage> newsImage) {
		this.newsImage = newsImage;
	}
	
	
	
}
