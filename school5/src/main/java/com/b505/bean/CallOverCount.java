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
@Table(name="callovercount")
@DynamicUpdate(true)
@DynamicInsert(true)
public class CallOverCount implements Serializable {
	private static final long serialVersionUID = -2198760883512636982L;
	private  Integer  id;
	//缺勤
	private  Integer truant;
	//病假
	private  Integer sickLeave;
	//迟到次数
	private  Integer lateNumber;
	private  Student student;
	private String term;
	
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
	@Column(name="truant",columnDefinition=" int(11) default 0")
	public Integer getTruant() {
		return truant;
	}
	public void setTruant(Integer truant) {
		this.truant = truant;
	}
	@Column(name="sickLeave",columnDefinition=" int(11) default 0")
	public Integer getSickLeave() {
		return sickLeave;
	}
	public void setSickLeave(Integer sickLeave) {
		this.sickLeave = sickLeave;
	}
	@Column(name="lateNumber", columnDefinition=" int(11) default 0")
	public Integer getLateNumber() {
		return lateNumber;
	}
	public void setLateNumber(Integer lateNumber ) {
		this.lateNumber = lateNumber;
	}
	@OneToOne
	@JoinColumn(name="student_id",unique=true)
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	@Column(name="term")
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	
}
