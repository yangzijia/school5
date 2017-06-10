package com.b505.bean.util;

public class StudentUtil {
	private String nickName;
	private String id;
	private String studentName;
	private String studentPhone;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentPhone() {
		return studentPhone;
	}
	public void setStudentPhone(String studentPhone) {
		this.studentPhone = studentPhone;
	}
	public StudentUtil(String nickName, String id, String studentName,
			String studentPhone) {
		super();
		this.nickName = nickName;
		this.id = id;
		this.studentName = studentName;
		this.studentPhone = studentPhone;
	}
	
	public StudentUtil(String id, String studentName) {
		super();
		this.id = id;
		this.studentName = studentName;
	}
	@Override
	public String toString() {
		return "StudentUtil [nickName=" + nickName + ", id=" + id
				+ ", studentName=" + studentName + ", studentPhone="
				+ studentPhone + "]";
	}
	
}
