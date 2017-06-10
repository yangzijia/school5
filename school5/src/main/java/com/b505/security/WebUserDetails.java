package com.b505.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 该类实现 UserDetails 接口，该类在验证成功后会被保存在当前回话的principal对象中
 * 
 * 获得对象的方式：
 * WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 
 * 或在JSP中：
 * <sec:authentication property="principal.username"/>
 * 
 * 如果需要包括用户的其他属性，可以在该类中增加相应属性即可
 * 
 * @author Taven.Li
 *
 */
public class WebUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8242940190960961504L;
	
	private String username;
	private String password;	
	private boolean userEnabled;
	private Collection<GrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	
	
	
	public WebUserDetails(String username, String password, boolean userEnabled, Collection<GrantedAuthority> authorities) {
		System.out.println("**************WebUserDetails******");
		System.out.println("username------>"+username);
		System.out.println("password------>"+password);
		this.username = username;
		this.password = password;
		this.userEnabled = userEnabled;		
		this.authorities = authorities;
		
		//这里先初始都为true，如果需要深度控制，可完善
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		
	
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {		
		return this.userEnabled;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

}
