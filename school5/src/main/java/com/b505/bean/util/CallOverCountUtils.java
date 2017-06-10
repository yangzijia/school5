package com.b505.bean.util;

public class CallOverCountUtils {
	private String studentNumber;
	private String studentName;
	private Integer statusNum;
	private Integer lateNum;
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
	public Integer getLateNum() {
		return lateNum;
	}
	public void setLateNum(Integer lateNum) {
		this.lateNum = lateNum;
	}
	public CallOverCountUtils(String studentNumber, String studentName,
			Integer statusNum, Integer lateNum) {
		super();
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.statusNum = statusNum;
		this.lateNum = lateNum;
	}
	@Override
	public String toString() {
		return "CallOverCountUtils [studentNumber=" + studentNumber
				+ ", studentName=" + studentName + ", statusNum=" + statusNum
				+ ", lateNum=" + lateNum + "]";
	}
}
