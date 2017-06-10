package com.b505.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="parentmenu")
public class ParentMenu implements Serializable {
	
	private static final long serialVersionUID = -398290201200277970L;
	private Integer id;
	private String menuName;
	private List<ChildrenMenu> childrenMenus = new ArrayList<ChildrenMenu>(); 
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
	@Column(name="menuname")
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	@OneToMany(cascade=CascadeType.REMOVE,mappedBy="parentMenu",fetch=FetchType.EAGER)
	public List<ChildrenMenu> getChildrenMenus() {
		return childrenMenus;
	}
	public void setChildrenMenus(List<ChildrenMenu> childrenMenus) {
		this.childrenMenus = childrenMenus;
	}
	
	@Override
	public String toString() {
		return "ParentMenu [id=" + id + ", menuName=" + menuName
				+ ", childrenMenus="+" childrenMenus "+"]";
	}

	
}
