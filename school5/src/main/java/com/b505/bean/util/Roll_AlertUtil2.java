package com.b505.bean.util;

public class Roll_AlertUtil2
{

	
    private String score;
	 private String course;
	 private String coursetype;
	 private String opinion;
	
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
	public Roll_AlertUtil2(String score, String course, String coursetype,
			String opinion)
	{
		super();
		this.score = score;
		this.course = course;
		this.coursetype = coursetype;
		this.opinion = opinion;
	}
	@Override
	public String toString()
	{
		return "Roll_AlertUtil2 [score=" + score + ", course=" + course
				+ ", coursetype=" + coursetype + ", opinion=" + opinion + "]";
	}
	

	
	 

}
