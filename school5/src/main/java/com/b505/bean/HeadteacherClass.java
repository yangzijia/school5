package com.b505.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="headteacher_class")
public class HeadteacherClass implements Serializable{

	private static final long serialVersionUID = -1740172921364145406L;
	private Integer id;
	private Teacher  teacher;
	private Grade studentClass;

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
	
	@ManyToOne
	@JoinColumn(name = "t_id")
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	@ManyToOne
	@JoinColumn(name = "c_id")
	public Grade getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(Grade studentClass) {
		this.studentClass =studentClass;
	}
	
}
