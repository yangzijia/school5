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
@Table(name="childrenmenu")
//需要在数据库查询在更新这个样才会起做用DynamicUpdate(true)
@DynamicUpdate(true)
//这个属性不进行json的转化，忽略这个属性的json转化
@JsonIgnoreProperties(value={"parentMenu","roleNames"})
public class ChildrenMenu implements Serializable {
	private static final long serialVersionUID = -8508761356980382062L;
	private Integer id;
	private String childrenmenuName;
	private ParentMenu parentMenu;
	private String url;
	private String remark;
	private List<RoleName> roleNames = new ArrayList<RoleName>();
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
	@Column(name="childrenmenuName")
	public String getChildrenmenuName() {
		return childrenmenuName;
	}
	public void setChildrenmenuName(String childrenmenuName) {
		this.childrenmenuName = childrenmenuName;
	}
	@ManyToOne
	@JoinColumn(name="parentMenu_id")  //ChildrenMenu类中对应外键的属性：id
	public ParentMenu getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(ParentMenu parentMenu) {
		this.parentMenu = parentMenu;
	}
	@Column(name="url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@ManyToMany(fetch=FetchType.EAGER,mappedBy="childrenMenus")
	@Fetch(FetchMode.SUBSELECT)
	public List<RoleName> getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(List<RoleName> roleNames) {
		this.roleNames = roleNames;
	}
	@Override
	public String toString() {
		return "ChildrenMenu [id=" + id + ", childrenmenuName="
				+ childrenmenuName + ",url="
				+ url + "]";
	}
	
}
