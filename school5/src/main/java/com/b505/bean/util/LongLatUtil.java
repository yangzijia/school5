package com.b505.bean.util;

import java.util.Date;

public class LongLatUtil {
	private long mapId;
	private String longlattype;
	private Date  longLatDate;
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
	public Date getLongLatDate() {
		return longLatDate;
	}
	public void setLongLatDate(Date longLatDate) {
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
	public LongLatUtil(long mapId, String longlattype, Date longLatDate,
			String studentNumber, String studentName, String word,
			String geography) {
		super();
		this.mapId = mapId;
		this.longlattype = longlattype;
		this.longLatDate = longLatDate;
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.word = word;
		this.geography = geography;
	}
	@Override
	public String toString() {
		return "LongLatUtil [mapId=" + mapId + ", longlattype=" + longlattype
				+ ", longLatDate=" + longLatDate + ", studentNumber="
				+ studentNumber + ", studentName=" + studentName + ", word="
				+ word + ", geography=" + geography + "]";
	}
}
