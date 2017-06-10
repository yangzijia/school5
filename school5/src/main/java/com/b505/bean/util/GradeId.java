package com.b505.bean.util;

public class GradeId {

	private int gid;

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	
	public GradeId(int gid) {
		//调用父类的方法
		super();
		// TODO Auto-generated constructor stub
		this.gid=gid;
	}
	
	@Override
	public String toString() {
		return "GradeId [gid=" + gid + "]";
	}
	
}
