package com.b505.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="dormcallovercount")
@DynamicUpdate(true)
@DynamicInsert(true)
public class DormcalloverCount implements Serializable{

	private static final long serialVersionUID = 3614480239437372091L;
	
	private  Integer  id;
	private  Student student;
	private  Integer num;
	
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@OneToOne
	@JoinColumn(name="student_id",unique=true)
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	@Column(name="num",columnDefinition=" int(11) default 0")
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "DormcalloverCount [id=" + id + ", student=" + student
				+ ", num=" + num + "]";
	}
	
}
