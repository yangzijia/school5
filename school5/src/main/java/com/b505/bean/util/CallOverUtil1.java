package com.b505.bean.util;

public class CallOverUtil1 {
	private String date;
	private String teacherName;
	private String className;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}	

	@Override
	public String toString() {
		return "CallOverUtil [date=" + date + ", teacherName=" + teacherName
				+ ", className=" + className + "]";
	}
	
}
