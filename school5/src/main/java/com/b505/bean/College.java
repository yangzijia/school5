package com.b505.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="college")
public class College implements Serializable {
	
	private static final long serialVersionUID = -2646603394549047514L;
	private Integer collegeid;
	private String  collegeName;
	
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="collegeId")
	public Integer getCollegeid() {
		return collegeid;
	}
	public void setCollegeid(Integer string) {
		this.collegeid = string;
	}
	@Column(name="collegeName")
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	@Override
	public String toString() {
		return "College [collegeid=" + collegeid + ", collegeName="
				+ collegeName + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((collegeName == null) ? 0 : collegeName.hashCode());
		result = prime * result
				+ ((collegeid == null) ? 0 : collegeid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		College other = (College) obj;
		if (collegeName == null) {
			if (other.collegeName != null)
				return false;
		} else if (!collegeName.equals(other.collegeName))
			return false;
		if (collegeid == null) {
			if (other.collegeid != null)
				return false;
		} else if (!collegeid.equals(other.collegeid))
			return false;
		return true;
	}
	
}
