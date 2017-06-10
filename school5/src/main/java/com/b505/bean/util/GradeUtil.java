package com.b505.bean.util;

public class GradeUtil {
	private Integer gradeId;
	private String  yearClass;
	private String  profession;
	private String  classId;
	
	public GradeUtil(Integer  gradeId, String yearClass, String profession,
			String classId) {
		super();
		this.gradeId = gradeId;
		this.yearClass = yearClass;
		this.profession = profession;
		this.classId = classId;
	}
	
	public GradeUtil(String yearClass, String profession, String classId) {
		super();
		this.yearClass = yearClass;
		this.profession = profession;
		this.classId = classId;
	}

	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	public String getYearClass() {
		return yearClass;
	}
	public void setYearClass(String yearClass) {
		this.yearClass = yearClass;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	@Override
	public String toString() {
		return "GradeUtil [gradeId=" + gradeId + ", yearClass=" + yearClass
				+ ", profession=" + profession + ", classId=" + classId + "]";
	}
}
