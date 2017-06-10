package com.b505.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.b505.bean.LoginUser;
import com.b505.bean.RoleName;
import com.b505.service.IRoleNameService;
import com.b505.tools.TryCatchUserService;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函�数.
 * 
 * @author calvin  edit by meetrice
 */


@Service
public class MyUserDetailServiceImpl implements UserDetailsService
{
	@Autowired
	private IRoleNameService roleNameService;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	/**
     * 获取用户Detail信息的回调函�数.
     */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		System.out.println("******************MyUserDetailServiceImpl.java********************");
		
		//这里应该可以不用再查了
		LoginUser users = tryCatchUserService.getUserByNickname(username);
		if (null == users) {
			throw new UsernameNotFoundException(
							messages.getMessage("User.notFound", new Object[] { username }, "Username {0} not found"));
		}
		
		//读取当前用户有哪些角色权限
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<RoleName> userRoles = users.getRoles();
		for (RoleName userRole : userRoles) {
			//这里的 role 参数为自己定义的，要和 SecurityMetadataSource 中的 SecurityConfig 参数对应
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRoleName());

			authorities.add(authority);
			System.out.println("authorities===>"+authorities);
		}
		
//		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);	
//		System.out.println("grantedAuths:"+grantedAuths);
		boolean enables = true;
//		boolean accountNonExpired = true;
//		boolean credentialsNonExpired = true;
//		boolean accountNonLocked = true;
		//封装成spring security的user
		
		WebUserDetails webUserDetails = new WebUserDetails(username, users.getPassword(), enables, authorities);
		//User userdetail = new User(users.getNickName(), users.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
		return webUserDetails;
	}
	
//	//取得用户的权限
//	@SuppressWarnings("deprecation")
//	private Set<GrantedAuthority> obtionGrantedAuthorities(LoginUser user)
//	{
//		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
//		String roleName = user.getRole();
//		
//		
//		System.out.println("roleName:"+roleName);
//		//角色
//		RoleName role = roleNameService.get("roleName", roleName);	
//		System.out.println("role:"+role);
//		//资源的集合
//		List<Resc> rescList = role.getResc();
//		for(Resc res : rescList) 
//		{
//			authSet.add(new GrantedAuthorityImpl(role.getRoleName()));
//		}
//		
//		System.out.println("authSet:"+authSet);
//		return authSet;
//	}
}

