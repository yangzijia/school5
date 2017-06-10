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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="teacher")
public class Teacher implements Serializable {
	
	private static final long serialVersionUID = 3129856851829040003L;
	
	private Integer id;
	private String teacherName;
	private String teacherPhone;
	private String teacherCardId;
	private College college;
	private LoginUser user;
	private List<Grade>  teacherClass = new ArrayList<Grade>();
	
	@ManyToOne
	@JoinColumn(name="college_c_id")
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	
	
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="teacherId")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="teacherName")
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	@Column(name="teacherPhone")
	public String getTeacherPhone() {
		return teacherPhone;
	}
	public void setTeacherPhone(String string) {
		this.teacherPhone = string;
	}
	@Column(name="teacherCardId")
	public String getTeacherCardId() {
		return teacherCardId;
	}
	public void setTeacherCardId(String teacherCardId) {
		this.teacherCardId = teacherCardId;
	}
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="t_role",unique=true)
	public LoginUser getUser() {
		return user;
	}
	public void setUser(LoginUser user) {
		this.user = user;
	}
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="teacher_class",
			joinColumns = {@JoinColumn(name = "t_id" ,referencedColumnName = "teacherId")},
		inverseJoinColumns = {@JoinColumn(name = "c_id",referencedColumnName = "gradeId")})
	
	public List<Grade> getTeacherClass() {
		return teacherClass;
	}
	public void setTeacherClass(List<Grade> teacherClass) {
		this.teacherClass = teacherClass;
	}
	
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", teacherName=" + teacherName
				+ ", teacherPhone=" + teacherPhone + ", teacherCardId="
				+ teacherCardId + ", college=" + college + ", user=" + user
				+ ", teacherClass=" + teacherClass + "]";
	}
}
