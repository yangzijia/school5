package com.b505.bean.util;

public class TeacherRegisterUtil {
	private String teacherName;
	private String teacherPhone;
	private String teacherCardId;
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
	//创建获取老师姓名，手机号和身份证的构造方法
     public TeacherRegisterUtil(String teacherName, String teacherPhone,
			String teacherCardId) {
		super();
		this.teacherName = teacherName;
		this.teacherPhone = teacherPhone;
		this.teacherCardId = teacherCardId;
	}
	
	//创建获取导员姓名和手机号的构造方法
	public TeacherRegisterUtil(String teacherName, String teacherPhone)
	{
		super();
		this.teacherName = teacherName;
		this.teacherPhone = teacherPhone;
		}
	//创建获取导员姓名的构造方法
	public TeacherRegisterUtil(String teacherName)
	{
		super();
		this.teacherName = teacherName;
		
		}

	
	@Override
	public String toString() {
		return "TeacherRegisterUtil [teacherName=" + teacherName
				+ ", teacherPhone=" + teacherPhone + ", teacherCardId="
				+ teacherCardId + "]";
	}
	
}
