package com.b505.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="leader")
@DynamicUpdate(true)
public class Leader implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String phone;
	private LoginUser user;
	private College college;
	private String rank;
	
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="l_id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="l_name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="l_phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="l_role",unique=true)
	public LoginUser getUser() {
		return user;
	}
	public void setUser(LoginUser user) {
		this.user = user;
	}
	@ManyToOne
	@JoinColumn(name="l_college_id")
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	@Column(name="l_rank")
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	
}
