package com.b505.bean.util;
 
/**
 * @author JSY
 * @代码修改
 * @time 2016.4.5
 */
public class CallOverUtils {

	private String yearClass;
	private String profession;
	private String classId;
	private String studentNumber;
	private String studentName;
	private String teacherName;
	private String status;
	
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
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
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
	@Override
	public String toString()
	{
		return "CallOverUtils [yearClass=" + yearClass + ", profession="
				+ profession + ", classId=" + classId + ", studentNumber="
				+ studentNumber + ", studentName=" + studentName
				+ ", status=" + status + ", teacherName=" + teacherName + "]";
	}
	public CallOverUtils(String yearClass, String profession, String classId,
			String studentNumber, String studentName, String teacherName, String status) {
		super();
		this.yearClass = yearClass;
		this.profession = profession;
		this.classId = classId;
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.status =status;
		this.teacherName = teacherName;
		
	}
}
