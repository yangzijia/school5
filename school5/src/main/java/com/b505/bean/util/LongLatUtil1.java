package com.b505.bean.util;


public class LongLatUtil1 {

	private long mapId;
	private String longlattype;
	private String  longLatDate;
	private String studentNumber;
	private String studentName;
	private String word;
	private String geography;
	public long getMapId() {
		return mapId;
	}
	public void setMapId(long mapId) {
		this.mapId = mapId;
	}
	public String getLonglattype() {
		return longlattype;
	}
	public void setLonglattype(String longlattype) {
		this.longlattype = longlattype;
	}
	public String getLongLatDate() {
		return longLatDate;
	}
	public void setLongLatDate(String longLatDate) {
		this.longLatDate = longLatDate;
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
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getGeography() {
		return geography;
	}
	public void setGeography(String geography) {
		this.geography = geography;
	}
	@Override
	public String toString() {
		return "LongLatUtil1 [mapId=" + mapId + ", longlattype=" + longlattype
				+ ", longLatDate=" + longLatDate + ", studentNumber="
				+ studentNumber + ", studentName=" + studentName + ", word="
				+ word + ", geography=" + geography + "]";
	}
}
