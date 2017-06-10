package com.b505.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Blob;
@Entity
@Table(name="newsImage")
@JsonIgnoreProperties(value={"news"})
public class NewsImage implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer imgId;
	private String  imgUrl;
	private String  imgName;
	private Blob    image;
	private News  news;
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="imageId")
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}
	@Column(name="imgName")
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	@Column(name="image")
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	
	@Column(name="imgurl")
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@ManyToOne
	@JoinColumn(name="news_id")
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	
}
