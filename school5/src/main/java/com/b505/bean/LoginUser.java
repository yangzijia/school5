package com.b505.bean;

import java.io.Serializable;
import java.sql.Blob;
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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="user")
@DynamicUpdate(true)
public class LoginUser implements Serializable {
	private static final long serialVersionUID = 1903941282069892725L;
	/**
	 * nickName 昵称
	 * password 密码
	 * role 角色
	 */
	private String nickName;
	private String password;
	private String  imgUrl;
	private Blob    image;
	private String role;
	private String status;
	private String clientid;
	private List<RoleName> roles = new ArrayList<RoleName>();
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="user_role",
			joinColumns = {@JoinColumn(name = "nickName" )},
		inverseJoinColumns = {@JoinColumn(name = "role_id")})
	@Fetch(FetchMode.SUBSELECT)
	public List<RoleName> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleName> roles) {
		this.roles = roles;
	}
	@Id
	@GeneratedValue(generator="u_assigned")
	@GenericGenerator(name="u_assigned",strategy="assigned")
	@Column(name="nickName")
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	@Column(name="imgurl")
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Column(name="image")
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="role")
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="clientid")
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	
	
}
