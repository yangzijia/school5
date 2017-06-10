package com.b505.bean.util;

public class studentAndstudetAsk {

	private int askid;
	private String studentName;
	private String type;
	private String starttime;
	private String endtime;
	private String status;
	
	
	
	public int getAskid() {
		return askid;
	}
	public void setAskid(int askid) {
		this.askid = askid;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "studentAndstudetAsk [askid=" + askid + ", studentName="
				+ studentName + ", type=" + type + ", starttime=" + starttime
				+ ", endtime=" + endtime + ", status=" + status + "]";
	}
	

}
