package com.b505.bean.util;

public class Roll_AlertUtil
{
     private String id;
     private String score;
 	 private String course;
 	 private String coursetype;
 	 private String opinion;
 	 private String name;
 	 public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getScore()
	{
		return score;
	}
	public void setScore(String score)
	{
		this.score = score;
	}
	public String getCourse()
	{
		return course;
	}
	public void setCourse(String course)
	{
		this.course = course;
	}
	public String getCoursetype()
	{
		return coursetype;
	}
	public void setCoursetype(String coursetype)
	{
		this.coursetype = coursetype;
	}
	public String getOpinion()
	{
		return opinion;
	}
	public void setOpinion(String opinion)
	{
		this.opinion = opinion;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Roll_AlertUtil [id=" + id + ", score=" + score + ", course="
				+ course + ", coursetype=" + coursetype + ", opinion="
				+ opinion + ", name=" + name + "]";
	}
	 

}
