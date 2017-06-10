package com.b505.bean.util;

public class LongLatClientUtil {
	private String longlattype;
	private String nickname;
	private String studentNumber;
	private String studentName;
	private String geography;
	private String latitude;
	private String longitude;
	private String pictureURL;
	private String  speechURL;
	private String  thumbnailPictureURL;
	private String word;
	private String longLatDate;
	private String headSculpture;
	
	
	public String getLonglattype() {
		return longlattype;
	}
	public void setLonglattype(String longlattype) {
		this.longlattype = longlattype;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getGeography() {
		return geography;
	}
	public void setGeography(String geography) {
		this.geography = geography;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public String getSpeechURL() {
		return speechURL;
	}
	public void setSpeechURL(String speechURL) {
		this.speechURL = speechURL;
	}
	public String getThumbnailPictureURL() {
		return thumbnailPictureURL;
	}
	public void setThumbnailPictureURL(String thumbnailPictureURL) {
		this.thumbnailPictureURL = thumbnailPictureURL;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getLongLatDate() {
		return longLatDate;
	}
	public void setLongLatDate(String longLatDate) {
		this.longLatDate = longLatDate;
	}
	public String getHeadSculpture() {
		return headSculpture;
	}
	public void setHeadSculpture(String headSculpture) {
		this.headSculpture = headSculpture;
	}
	@Override
	public String toString() {
		return "LongLatClientUtil [longlattype=" + longlattype + ", nickname="
				+ nickname + ", studentNumber=" + studentNumber
				+ ", studentName=" + studentName + ", geography=" + geography
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", pictureURL=" + pictureURL + ", speechURL=" + speechURL
				+ ", thumbnailPictureURL=" + thumbnailPictureURL + ", word="
				+ word + ", longLatDate=" + longLatDate + ", headSculpture="
				+ headSculpture + "]";
	}
	
}
