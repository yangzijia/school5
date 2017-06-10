package com.b505.bean.util;

import java.util.Date;

public class CallOverUtil {
	
	private Date date;
	private String teacherName;
	private String className;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
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
	
	public CallOverUtil(Date date, String teacherName, String className) {
		super();
		this.date = date;
		this.teacherName = teacherName;
		this.className = className;
	}
	@Override
	public String toString() {
		return "CallOverUtil [date=" + date + ", teacherName=" + teacherName
				+ ", className=" + className + "]";
	}

	
}
