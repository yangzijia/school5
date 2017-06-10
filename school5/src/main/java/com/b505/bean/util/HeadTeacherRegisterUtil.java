package com.b505.bean.util;

import java.util.ArrayList;
import java.util.List;

import com.b505.bean.Grade;

public class HeadTeacherRegisterUtil {
	private String teacherName;
	private String teacherPhone;
	private String teacherCardId;
	private String collegeName;
	private List<Grade>  teacherClass = new ArrayList<Grade>();
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherPhone() {
		return teacherPhone;
	}
	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}
	public String getTeacherCardId() {
		return teacherCardId;
	}
	public void setTeacherCardId(String teacherCardId) {
		this.teacherCardId = teacherCardId;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public List<Grade> getTeacherClass() {
		return teacherClass;
	}
	public void setTeacherClass(List<Grade> teacherClass) {
		this.teacherClass = teacherClass;
	}
	public HeadTeacherRegisterUtil(String teacherName, String teacherPhone,
			String teacherCardId, String collegeName, List<Grade> teacherClass) {
		super();
		this.teacherName = teacherName;
		this.teacherPhone = teacherPhone;
		this.teacherCardId = teacherCardId;
		this.collegeName = collegeName;
		this.teacherClass = teacherClass;
	}
	public HeadTeacherRegisterUtil(String teacherName, String teacherPhone,
			String teacherCardId, String collegeName) {
		super();
		this.teacherName = teacherName;
		this.teacherPhone = teacherPhone;
		this.teacherCardId = teacherCardId;
		this.collegeName = collegeName;
		
	}
	@Override
	public String toString() {
		return "HeadTeacherRegisterUtil [teacherName=" + teacherName
				+ ", teacherPhone=" + teacherPhone + ", teacherCardId="
				+ teacherCardId + ", collegeName=" + collegeName
				+ ", teacherClass=" + teacherClass + "]";
	}
	
	
}
