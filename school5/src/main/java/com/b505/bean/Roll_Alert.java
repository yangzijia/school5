package com.b505.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="roll_alert")
@DynamicUpdate(true)
public class Roll_Alert implements Serializable
{
	private static final long serialVersionUID = 1903941282069892725L;
	/**
	 * 
	 */
	private int Rid;
	//private String snumber;
	private Student student;
	private String score;
	private String course;
	private String coursetype;
	private String opinion;
	private int headid;
	private String name;
	
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="Rid")
	public int getRid()
	{
		return Rid;
	}
	public void setRid(int id)
	{
		this.Rid = id;
	}
	@OneToOne
	@JoinColumn(name="studentNumber")
	public Student getStudent()
	{
		return student;
	}
	public void setStudent(Student student)
	{
		this.student = student;
	}
//	@Column(name="snumber")
//	public String getSnumber()
//	{
//		return snumber;
//	}
//	public void setSnumber(String snumber)
//	{
//		this.snumber = snumber;
//	}
	@Column(name="name")
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	@Column(name="score")
	public String getScore()
	{
		return score;
	}
	public void setScore(String score)
	{
		this.score = score;
	}
	@Column(name="course")
	public String getCourse()
	{
		return course;
	}
	public void setCourse(String course)
	{
		this.course = course;
	}
	@Column(name="coursetype")
	public String getCoursetype()
	{
		return coursetype;
	}
	public void setCoursetype(String coursetype)
	{
		this.coursetype = coursetype;
	}
	@Column(name="opinion")
	public String getOpinion()
	{
		return opinion;
	}
	public void setOpinion(String opinion)
	{
		this.opinion = opinion;
	}
	@Column(name="headid")
	public int getHeadid()
	{
		return headid;
	}
	public void setHeadid(int headid)
	{
		this.headid = headid;
	}
	@Override
	public String toString()
	{
		return "Roll_Alert [id=" + Rid + ", student=" + student + ", score="
				+ score + ", course=" + course + ", coursetype=" + coursetype
				+ ", opinion=" + opinion + ", headid=" + headid + ", name="
				+ name + "]";
	}
	
	
}
