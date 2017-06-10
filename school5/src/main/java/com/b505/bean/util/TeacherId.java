package com.b505.bean.util;

public class TeacherId {

	private int tid;

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}
	
	

	public TeacherId(int tid) {
		super();
		// TODO Auto-generated constructor stub
		this.tid=tid;
	}

	@Override
	public String toString() {
		return "TeacherId [tid=" + tid + "]";
	}
	
}
