package com.b505.bean.util;

import java.util.Date;

public class HteacherLongLatUtil {

	private long mapId;
	private String longlattype;
	private Date  longLatDate;
	private String teacherNumber;
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
	public String getTeacherNumber() {
		return teacherNumber;
	}
	public void setTeacherNumber(String teacherNumber) {
		this.teacherNumber = teacherNumber;
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
	public HteacherLongLatUtil(long mapId, String longlattype,
			Date longLatDate, String teacherNumber, String word,
			String geography) {
		super();
		this.mapId = mapId;
		this.longlattype = longlattype;
		this.longLatDate = longLatDate;
		this.teacherNumber = teacherNumber;
		this.word = word;
		this.geography = geography;
	}
	@Override
	public String toString() {
		return "HteacherLongLatUtil [mapId=" + mapId + ", longlattype="
				+ longlattype + ", longLatDate=" + longLatDate
				+ ", teacherNumber=" + teacherNumber + ", word=" + word
				+ ", geography=" + geography + "]";
	}
	
}
