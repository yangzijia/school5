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
@Table(name="longLatImg")
@JsonIgnoreProperties(value={"longLat"})
public class LongLatImage implements Serializable {
	private static final long serialVersionUID = -5317374598848654441L;
	private Long mapImgId;
	private String imgurl;
	private LongLat longLat;
	private String imageName;
	private Blob   image;
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="mapImgId")
	public Long getMapImgId() {
		return mapImgId;
	}
	public void setMapImgId(Long mapImgId) {
		this.mapImgId = mapImgId;
	}
	@Column(name="imgurl")
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	@ManyToOne
	@JoinColumn(name="mapId")
	public LongLat getLongLat() {
		return longLat;
	}
	public void setLongLat(LongLat longLat) {
		this.longLat = longLat;
	}
	@Column(name="imageName")
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	@Column(name="image")
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "LongLatImage [mapImgId=" + mapImgId + ", imgurl=" + imgurl
				+ ", imageName=" + imageName + ", image=" + image + "]";
	}
	
}
