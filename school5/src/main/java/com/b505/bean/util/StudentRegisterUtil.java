package com.b505.bean.util;

public class StudentRegisterUtil {
	private String id;
	private String studentName;
	private String studentPhone;
	private String studentCardId;
	private String collegeName;
	private String yearClass;
	private String profession;
	private String classId;
	private String  imgUrl;
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	public String getStudentCardId() {
		return studentCardId;
	}
	public void setStudentCardId(String studentCardId) {
		this.studentCardId = studentCardId;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
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
	public StudentRegisterUtil(String id, String studentName,
			String studentPhone, String studentCardId, String collegeName,
			String yearClass, String profession, String classId, String imgUrl) {
		super();
		this.id = id;
		this.studentName = studentName;
		this.studentPhone = studentPhone;
		this.studentCardId = studentCardId;
		this.collegeName = collegeName;
		this.yearClass = yearClass;
		this.profession = profession;
		this.classId = classId;
		this.imgUrl = imgUrl;
	}
	@Override
	public String toString() {
		return "StudentRegisterUtil [id=" + id + ", studentName=" + studentName
				+ ", studentPhone=" + studentPhone + ", studentCardId="
				+ studentCardId + ", collegeName=" + collegeName
				+ ", yearClass=" + yearClass + ", profession=" + profession
				+ ", classId=" + classId + ", imgUrl=" + imgUrl + "]";
	}
	
}
