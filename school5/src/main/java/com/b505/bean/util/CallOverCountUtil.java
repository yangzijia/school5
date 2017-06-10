package com.b505.bean.util;

public class CallOverCountUtil {
	private String studentNumber;
	private String studentName;
	private Integer statusNum;
	private String studentImageUrl;
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
	public Integer getStatusNum() {
		return statusNum;
	}
	public void setStatusNum(Integer statusNum) {
		this.statusNum = statusNum;
	}
	public String getStudentImageUrl() {
		return studentImageUrl;
	}
	public void setStudentImageUrl(String studentImageUrl) {
		this.studentImageUrl = studentImageUrl;
	}
	@Override
	public String toString() {
		return "CallOverCountUtil [studentNumber=" + studentNumber
				+ ", studentName=" + studentName + ", statusNum=" + statusNum
				+ ", studentImageUrl=" + studentImageUrl + "]";
	}
	public CallOverCountUtil(String studentNumber, String studentName,
			Integer statusNum, String studentImageUrl) {
		super();
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.statusNum = statusNum;
		this.studentImageUrl = studentImageUrl;
	}
	
	
	
}
