package com.b505.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="student")
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties(value={"longlat"})
public class Student implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String studentName;
	private String studentPhone;
	private String studentCardId;
	private Grade studentClass;
	private College college;
	private LoginUser user;
	private List <LongLat> longlat = new ArrayList<LongLat>();
	@Id
	@GeneratedValue(generator="u_assigned")
	@GenericGenerator(name="u_assigned",strategy="assigned")
	@Column(name="snumber")
	public String getId() {
		return id;
	}
	public void setId(String string) {
		this.id = string;
	}

	@Column(name="studentName")
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	@Column(name="studentPhone")
	public String getStudentPhone() {
		return studentPhone;
	}
	public void setStudentPhone(String string) {
		this.studentPhone = string;
	}
	@Column(name="studentCardId")
	public String getStudentCardId() {
		return studentCardId;
	}
	public void setStudentCardId(String studentCardId) {
		this.studentCardId = studentCardId;
	}
	@ManyToOne
	@JoinColumn(name="S_C_ID")
	public Grade getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(Grade studentClass) {
		this.studentClass = studentClass;
	}
	@ManyToOne
	@JoinColumn(name="college_id")
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="t_role",unique=true)
	public LoginUser getUser() {
		return user;
	}
	public void setUser(LoginUser user) {
		this.user = user;
	}
	@OneToMany(cascade={CascadeType.REMOVE},mappedBy="student",fetch=FetchType.EAGER)
	public List<LongLat> getLonglat() {
		return longlat;
	}
	public void setLonglat(List<LongLat> longlat) {
		this.longlat = longlat;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", studentName=" + studentName
				+ ", studentPhone=" + studentPhone + ", studentCardId="
				+ studentCardId + ", studentClass=" + studentClass
				+ ", college=" + college + ", user=" + user + "]";
	}
	
}
