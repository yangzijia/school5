package com.b505.bean.util;

public class TeacherUtil {
	private Integer id;
	private String teacherName;
	private String teacherPhone;
	private String teacherCardId;
	private String nickName;
	private String password;
	
	public TeacherUtil(Integer id, String teacherName, String teacherPhone,
			String teacherCardId, String nickName, String password) {
		super();
		this.id = id;
		this.teacherName = teacherName;
		this.teacherPhone = teacherPhone;
		this.teacherCardId = teacherCardId;
		this.nickName = nickName;
		this.password = password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	

}
