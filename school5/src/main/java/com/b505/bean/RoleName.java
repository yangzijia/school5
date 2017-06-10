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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="role")
@DynamicUpdate(true)
@DynamicInsert(true)
public class RoleName implements Serializable {
	private static final long serialVersionUID = -1960237228407396802L;
	private Integer id;
	private String roleName;
	private List<ParentMenu> parentMenus = new ArrayList<ParentMenu>();
	private List<ChildrenMenu> childrenMenus = new ArrayList<ChildrenMenu>();
	private List<Resc> resc = new ArrayList<Resc>();
	private String remark;
	
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
	@Column(name="roleName")
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="role_parentMenu",
			joinColumns = {@JoinColumn(name = "r_id" ,referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "p_id",referencedColumnName = "id")})
	@Fetch(FetchMode.SUBSELECT)
	public List<ParentMenu> getParentMenus() {
		return parentMenus;
	}
	public void setParentMenus(List<ParentMenu> parentMenus) {
		this.parentMenus = parentMenus;
	}
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="role_childrenMenu",
			joinColumns = {@JoinColumn(name = "r_id" ,referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "c_id",referencedColumnName = "id")})
	@Fetch(FetchMode.SUBSELECT)
	public List<ChildrenMenu> getChildrenMenus() {
		return childrenMenus;
	}
	public void setChildrenMenus(List<ChildrenMenu> childrenMenus) {
		this.childrenMenus = childrenMenus;
	}
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="role_resc",
			joinColumns = {@JoinColumn(name = "role_id" ,referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "resc_id",referencedColumnName = "id")})
	@Fetch(FetchMode.SUBSELECT)
	public List<Resc> getResc() {
		return resc;
	}
	public void setResc(List<Resc> resc) {
		this.resc = resc;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "RoleName [id=" + id + ", roleName=" + roleName
				+ ", parentMenus=" + parentMenus + ", childrenMenus="
				+ childrenMenus + ", resc=" + resc + ", remark=" + remark + "]";
	}
	
}
