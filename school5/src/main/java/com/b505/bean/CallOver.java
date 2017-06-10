package com.b505.bean;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="callover")
@DynamicUpdate(true)
public class CallOver implements Serializable
{		
	private static final long serialVersionUID = 2109720958873643110L;
	private Long id;
	private Student student;
	private String teacherName;
	private Date date;
	private String status;
	//课程名称
	private String className;
	//节数
	private String classTime;
	private String term;

	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="classtime")
	public String getClassTime() {
		return classTime;
	}
	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}
	@Column(name="className")
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@OneToOne
	@JoinColumn(name="studentNumber")
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	@Column(name="teacherName")
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	@Column(name="date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name="status")
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getStatus() {
		return status;
	}
	@Column(name="term")
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	@Override
	public String toString() {
		return "CallOver [id=" + id + ", student=" + student + ", teacherName="
				+ teacherName + ", date=" + date + ", status=" + status
				+ ", className=" + className + ", classTime=" + classTime
				+ ", term=" + term + "]";
	}
	
}
