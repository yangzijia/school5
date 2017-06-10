package com.b505.bean.util;

public class DormcalloverUtil {
	private int id;
	private String studentName;
	private String phone;
	private String name;
	private String status;
	private String date;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "DormcalloverUtil [id=" + id + ", studentName=" + studentName
				+ ", phone=" + phone + ", name=" + name + ", status=" + status
				+ ", date=" + date + "]";
	}

}
