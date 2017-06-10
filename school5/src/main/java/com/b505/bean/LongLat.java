package com.b505.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="longLat")

public class LongLat implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String longlattype;
	//经度
	private  String longitude;
	//纬度
	private String latitude;
	//地理内容
	private String geography;
	//语音内容
	private String  word;
	private Date  longLatDate;
	private Student student;
	private Long mapId;
	private List<LongLatImage> longLatImage = new ArrayList<LongLatImage>();
	private List<LongLatVoice> longLatVoice = new ArrayList<LongLatVoice>();
	private List<SmallLongLatImage> smallLongLatImages = new ArrayList<SmallLongLatImage>();
	private String hteacherNickname;
	
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="mapId")
	public Long getMapId() {
		return  mapId;
	}
	public void setMapId(Long mapId) {
		this. mapId = mapId;
	}
	@Column(name="longlattype")
	public String getLonglattype() {
		return longlattype;
	}
	public void setLonglattype(String longlattype) {
		this.longlattype = longlattype;
	}
	@Column(name="longitude")
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@Column(name="latitude")
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	@Column(name="geography")
	public String getGeography() {
		return geography;
	}
	public void setGeography(String geography) {
		this.geography = geography;
	}
	@ManyToOne
	@JoinColumn(name="studentId")
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	@Column(name="word")
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	@OneToMany(cascade={CascadeType.REMOVE},mappedBy="longLat",fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	public List<LongLatImage> getLongLatImage() {
		return longLatImage;
	}
	public void setLongLatImage(List<LongLatImage> longLatImage) {
		this.longLatImage = longLatImage;
	}
	@OneToMany(cascade={CascadeType.REMOVE},mappedBy="longLat",fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	public List<LongLatVoice> getLongLatVoice() {
		return longLatVoice;
	}
	public void setLongLatVoice(List<LongLatVoice> longLatVoice) {
		this.longLatVoice = longLatVoice;
	}
	@OneToMany(cascade={CascadeType.REMOVE},mappedBy="longLat",fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	public List<SmallLongLatImage> getSmallLongLatImages() {
		return smallLongLatImages;
	}
	public void setSmallLongLatImages(List<SmallLongLatImage> smallLongLatImages) {
		this.smallLongLatImages = smallLongLatImages;
	}
	@Column(name="longLatDate")
	public Date getLongLatDate() {
		return longLatDate;
	}
	public void setLongLatDate(Date longLatDate) {
		this.longLatDate = longLatDate;
	}
	@Column(name="hteacherNickname")
	public String getHteacherNickname() {
		return hteacherNickname;
	}
	public void setHteacherNickname(String hteacherNickname) {
		this.hteacherNickname = hteacherNickname;
	}
	@Override
	public String toString() {
		return "LongLat [longlattype=" + longlattype + ", longitude="
				+ longitude + ", latitude=" + latitude + ", geography="
				+ geography + ", word=" + word + ", longLatDate=" + longLatDate
				+ ", student=" + student + ", mapId=" + mapId
				+ ", longLatImage=" + longLatImage + ", longLatVoice="
				+ longLatVoice + ", smallLongLatImages=" + smallLongLatImages
				+ ", hteacherNickname=" + hteacherNickname + "]";
	}
	
}
