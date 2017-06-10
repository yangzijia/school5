package com.b505.bean.util;

public class Roll_AlertUtils
{

	private String yearClass;
	private String profession;
	private String classId;
	private String studentNumber;
	private String studentName;
	private String opinion;
	public String getYearClass()
	{
		return yearClass;
	}
	public void setYearClass(String yearClass)
	{
		this.yearClass = yearClass;
	}
	public String getProfession()
	{
		return profession;
	}
	public void setProfession(String profession)
	{
		this.profession = profession;
	}
	public String getClassId()
	{
		return classId;
	}
	public void setClassId(String classId)
	{
		this.classId = classId;
	}
	public String getStudentNumber()
	{
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber)
	{
		this.studentNumber = studentNumber;
	}
	public String getStudentName()
	{
		return studentName;
	}
	public void setStudentName(String studentName)
	{
		this.studentName = studentName;
	}
	public String getOpinion()
	{
		return opinion;
	}
	public void setOpinion(String opinion)
	{
		this.opinion = opinion;
	}
	public Roll_AlertUtils(String yearClass, String profession, String classId,
			String studentNumber, String studentName, String opinion)
	{
		super();
		this.yearClass = yearClass;
		this.profession = profession;
		this.classId = classId;
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.opinion = opinion;
	}
	@Override
	public String toString()
	{
		return "Roll_AlertUtils [yearClass=" + yearClass + ", profession="
				+ profession + ", classId=" + classId + ", studentNumber="
				+ studentNumber + ", studentName=" + studentName + ", opinion="
				+ opinion + "]";
	}
}
