package com.b505.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
@Table(name="ask")
@DynamicUpdate(true)
@DynamicInsert(true)
public class StudentAsk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int askid;
	//private LoginUser user;
	private Student student;
	private String headteacher;
	private String timestart;
	private String timeend;
	private String type;
	private String sleep;
	private String reason;
	private String status;
	private String ParentsName;
	private String ParentsPhone;
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="askid")
	public int getAskid() {
		return askid;
	}
	public void setAskid(int askid) {
		this.askid = askid;
	}
	
	@OneToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name="nickName",unique=true)
	@JoinColumn(name="nickName")
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	
	@Column(name="teachername")
	public String getHeadteacher() {
		return headteacher;
	}	
	public void setHeadteacher(String headteacher) {
		this.headteacher = headteacher;
	}
	@Column(name="StartTime")
	public String getTimestart() {
		return timestart;
	}
	public void setTimestart(String timestart) {
		this.timestart = timestart;
	}
	@Column(name="EndTime")
	public String getTimeend() {
		return timeend;
	}
	public void setTimeend(String timeend) {
		this.timeend = timeend;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="sleep")
	public String getSleep() {
		return sleep;
	}
	public void setSleep(String sleep) {
		this.sleep = sleep;
	}
	@Column(name="reason")
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="ParentsName")
	public String getParentsName()
	{
		return ParentsName;
	}
	public void setParentsName(String parentsName)
	{
		ParentsName = parentsName;
	}
	@Column(name="ParentsPhone")
	public String getParentsPhone()
	{
		return ParentsPhone;
	}
	public void setParentsPhone(String parentsPhone)
	{
		ParentsPhone = parentsPhone;
	}
	@Override
	public String toString()
	{
		return "StudentAsk [askid=" + askid + ", student=" + student
				+ ", headteacher=" + headteacher + ", timestart=" + timestart
				+ ", timeend=" + timeend + ", type=" + type + ", sleep="
				+ sleep + ", reason=" + reason + ", status=" + status
				+ ", ParentsName=" + ParentsName + ", ParentsPhone="
				+ ParentsPhone + "]";
	}
	
			
}
