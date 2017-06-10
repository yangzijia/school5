package com.b505.bean.util;

public class StudentGid {

	private int gid;

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public StudentGid(int gid) {
		super();
		// TODO Auto-generated constructor stub
		this.gid=gid;
	}
	
	@Override
	public String toString() {
		return "StudentGid [gid=" + gid + "]";
	}
	
	
}
