package com.b505.bean.util;

public class AdminControllerUtil
{
    private String yearClass;
    private String profession;
    private String classId;
    private String studentNumber;
    private String name;
    private int rid;
    //private String opinion;
	
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

    public String getName()
	{
		return name;
	}

    public void setName(String name)
	{
		this.name = name;
	}
	

	
	
	
//	public String getOpinion()
//	{
//		return opinion;
//	}
//
//	public void setOpinion(String opinion)
//	{
//		this.opinion = opinion;
//	}

	
	
	public int getRid()
	{
		return rid;
	}

	public void setRid(int rid)
	{
		this.rid = rid;
	}


	public AdminControllerUtil(String yearClass, String profession,
			String classId, String studentNumber, String name, int rid)
	{
		super();
		this.yearClass = yearClass;
		this.profession = profession;
		this.classId = classId;
		this.studentNumber = studentNumber;
		this.name = name;
		this.rid = rid;
	}

	@Override
	public String toString()
	{
		return "AdminControllerUtil [yearClass=" + yearClass + ", profession="
				+ profession + ", classId=" + classId + ", studentNumber="
				+ studentNumber + ", name=" + name + ", rid=" + rid + "]";
	}

	

//	@Override
//	public String toString()
//	{
//		return "AdminControllerUtil [yearClass=" + yearClass + ", profession="
//				+ profession + ", classId=" + classId + ", studentNumber="
//				+ studentNumber + ", name=" + name + "]";
//	}

}
