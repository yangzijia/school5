package com.b505.bean.util;

public class ParentMenuUtil {
	private Integer id;
	private String menuName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public ParentMenuUtil(Integer id, String menuName) {
		super();
		this.id = id;
		this.menuName = menuName;
	}
	@Override
	public String toString() {
		return "ParentMenuUtil [id=" + id + ", menuName=" + menuName + "]";
	}
	
}
