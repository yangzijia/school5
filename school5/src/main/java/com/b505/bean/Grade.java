package com.b505.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="grade")
@DynamicUpdate(true)
@JsonIgnoreProperties(value={"teacherList"})
public class Grade implements Serializable {

	private static final long serialVersionUID = -2047673692716971976L;
	
	private Integer gradeId;
	private String yearClass;
	private String profession;
	private String classId;
	
	private College college;
	private List<Teacher> teacherList = new ArrayList<Teacher>();
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="gradeId")
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer string) {
		this.gradeId = string;
	}
	@ManyToOne
	@JoinColumn(name="college_id")
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	@Column(name="yearClass")
	public String getYearClass() {
		return yearClass;
	}
	public void setYearClass(String yearClass) {
		this.yearClass = yearClass;
	}
	@Column(name="profession")
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	@Column(name="classId")
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	@ManyToMany(fetch=FetchType.EAGER,mappedBy="teacherClass")
	@Fetch(FetchMode.SUBSELECT)
	public List<Teacher> getTeacherList() {
		return teacherList;
	}
	public void setTeacherList(List<Teacher> teacherList) {
		this.teacherList = teacherList;
	}
	@Override
	public String toString() {
		return "Grade [gradeId=" + gradeId + ", yearClass=" + yearClass
				+ ", profession=" + profession + ", classId=" + classId
				+ ", college=" + college + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + ((college == null) ? 0 : college.hashCode());
		result = prime * result + ((gradeId == null) ? 0 : gradeId.hashCode());
		result = prime * result
				+ ((profession == null) ? 0 : profession.hashCode());
		result = prime * result
				+ ((yearClass == null) ? 0 : yearClass.hashCode());
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
		Grade other = (Grade) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (college == null) {
			if (other.college != null)
				return false;
		} else if (!college.equals(other.college))
			return false;
		if (gradeId == null) {
			if (other.gradeId != null)
				return false;
		} else if (!gradeId.equals(other.gradeId))
			return false;
		if (profession == null) {
			if (other.profession != null)
				return false;
		} else if (!profession.equals(other.profession))
			return false;
		if (yearClass == null) {
			if (other.yearClass != null)
				return false;
		} else if (!yearClass.equals(other.yearClass))
			return false;
		return true;
	}
	
}
