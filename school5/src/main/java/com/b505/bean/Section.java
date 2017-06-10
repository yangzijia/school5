package com.b505.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="section")
public class Section implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private Integer sectionId;//当前栏目id
	private String sectionName;
	

	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="sectionId")
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	@Column(name="sectionName")
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	@Override
	public String toString() {
		return "Section [sectionId=" + sectionId + ", sectionName="
				+ sectionName +"]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sectionId == null) ? 0 : sectionId.hashCode());
		result = prime * result
				+ ((sectionName == null) ? 0 : sectionName.hashCode());
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
		Section other = (Section) obj;
		if (sectionId == null) {
			if (other.sectionId != null)
				return false;
		} else if (!sectionId.equals(other.sectionId))
			return false;
		if (sectionName == null) {
			if (other.sectionName != null)
				return false;
		} else if (!sectionName.equals(other.sectionName))
			return false;
		return true;
	}
	
	
}
