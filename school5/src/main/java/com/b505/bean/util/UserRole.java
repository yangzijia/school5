package com.b505.bean.util;

import java.util.ArrayList;
import java.util.List;

public class UserRole {
	private String nickName;
	private List<Integer> roles = new ArrayList<Integer>();
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public List<Integer> getRoles() {
		return roles;
	}
	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}

}
