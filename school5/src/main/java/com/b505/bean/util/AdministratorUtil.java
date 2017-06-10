package com.b505.bean.util;

public class AdministratorUtil {
	private Integer id;
	private String name;
	private String phone;
	private String nickName;
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
	public AdministratorUtil(Integer id, String name, String phone,
			String nickName) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.nickName = nickName;
	}
	@Override
	public String toString() {
		return "AdministratorUtil [id=" + id + ", name=" + name + ", phone="
				+ phone + ", nickName=" + nickName + "]";
	}
	
}
