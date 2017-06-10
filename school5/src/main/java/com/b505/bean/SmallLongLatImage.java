package com.b505.bean;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="smallLongLatImage")
@JsonIgnoreProperties(value={"longLat"})
public class SmallLongLatImage {
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
		return "SmallLongLatImage [mapImgId=" + mapImgId + ", imgurl=" + imgurl
				+ ", imageName=" + imageName + ", image=" + image + "]";
	}
	
}
