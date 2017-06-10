package com.b505.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class MyAccessDecisionManager implements AccessDecisionManager
{
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException 
	{
		
		System.out.println("**************MyAccessDecisionManager************8");
		if(configAttributes == null)
		{
			throw new AccessDeniedException(" 没有权限访问！");
		}
		//所请求的资源拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		System.out.println("iterator:"+iterator);
		while(iterator.hasNext()) 
		{
			ConfigAttribute configAttribute = iterator.next();
			
			//访问所请求资源所需要的权限
			String needPermission = configAttribute.getAttribute();
			
			System.out.println("needPermission："+needPermission);
			//用户所拥有的权限authentication
			for(GrantedAuthority ga : authentication.getAuthorities())
			{
				System.out.println("authentication.getAuthorities()："+authentication.getAuthorities());
				if(needPermission.equals(ga.getAuthority())) 
				{
					return;
				}
			}
		}
		
		System.out.println("您没有访问权限！");
		throw new AccessDeniedException(" 没有权限访问！");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) 
	{
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) 
	{
		return true;
	}
}
