package com.b505.bean.util;

public class LeaderUtil {
	private String name;
	private String phone;
	private String nickName;
	private String password;
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LeaderUtil [name=" + name + ", phone=" + phone + ", nickName="
				+ nickName + ", password=" + password + ", id=" + id + "]";
	}
	public LeaderUtil(Integer id,String name, String phone, String nickName,
			String password ) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.nickName = nickName;
		this.password = password;
		
	}
	
	

}
