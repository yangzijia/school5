package com.b505.bean.util;

import java.util.Arrays;

public class RegisterBean {
	private String teacherName;
	private int teacherPhone;
	private String teacherCardId;
	private String nickName;
	private String password;
	private String role;
	private String  [] grade;
	public String[] getGrade() {
		return grade;
	}
	public void setGrade(String[] grade) {
		this.grade = grade;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public int getTeacherPhone() {
		return teacherPhone;
	}
	public void setTeacherPhone(int teacherPhone) {
		this.teacherPhone = teacherPhone;
	}
	public String getTeacherCardId() {
		return teacherCardId;
	}
	public void setTeacherCardId(String teacherCardId) {
		this.teacherCardId = teacherCardId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "RegisterBean [teacherName=" + teacherName + ", teacherPhone="
				+ teacherPhone + ", teacherCardId=" + teacherCardId
				+ ", nickName=" + nickName + ", password=" + password
				+ ", role=" + role + ", grade=" + Arrays.toString(grade) + "]";
	}

}
