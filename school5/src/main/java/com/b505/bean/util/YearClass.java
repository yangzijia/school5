package com.b505.bean.util;

public class YearClass {
	private String yearClass;

	public YearClass(String yearClass) {
		super();
		this.yearClass = yearClass;
	}

	public String getYearClass() {
		return yearClass;
	}

	public void setYearClass(String yearClass) {
		this.yearClass = yearClass;
	}

	@Override
	public String toString() {
		return "YearClass [yearClass=" + yearClass + "]";
	}
}
