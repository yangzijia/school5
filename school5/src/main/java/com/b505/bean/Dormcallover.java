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
@Table(name="dormcallover")
@DynamicUpdate(true)
public class Dormcallover implements Serializable{
	
	private static final long serialVersionUID = -2499856475490663262L;

	private int id;
	private Student student;
	private String name;
	private Date date;
	private String status;
	
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@OneToOne
	@JoinColumn(name="studentNumber")
	public Student getStudent() {
		return student;
	}	
	public void setStudent(Student student) {
		this.student = student;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Dormcallover [id=" + id + ", student=" + student + ", name="
				+ name + ", date=" + date + ", status=" + status + "]";
	}
}
