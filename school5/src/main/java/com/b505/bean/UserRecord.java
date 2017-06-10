package com.b505.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="userrecord")
@DynamicUpdate(true)
public class UserRecord implements Serializable {

	private static final long serialVersionUID = -8678191282412661860L;

	/**
	 * nickName 昵称
	 * createTime 创建时间
	 * ip 登录ip
	 * lastTime 退出时间
	 */
	private Integer id;
	private String nickName;
	private String createTime;
	private String  ip;
	private String lastTime;
	
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
	@Column(name="nickName")
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	@Column(name="createTime")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name="ip")
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column(name="lastTime")
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	
	@Override
	public String toString() {
		return "UserRecord [id=" + id + ", nickName=" + nickName
				+ ", createTime=" + createTime + ", ip=" + ip + ", lastTime="
				+ lastTime + "]";
	}

}
