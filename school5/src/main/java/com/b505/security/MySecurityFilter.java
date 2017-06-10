package com.b505.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

/**
 * 自己实现的过滤用户请求类，也可以直接使用 FilterSecurityInterceptor
 * 
 * AbstractSecurityInterceptor有三个派生类：
 * FilterSecurityInterceptor，负责处理FilterInvocation，实现对URL资源的拦截。
 * MethodSecurityInterceptor，负责处理MethodInvocation，实现对方法调用的拦截。
 * AspectJSecurityInterceptor，负责处理JoinPoint，主要是用于对切面方法(AOP)调用的拦截。
 * 
 * 还可以直接使用注解对Action方法进行拦截，例如在方法上加：
 * @PreAuthorize("hasRole('ROLE_SUPER')")
 * 
 * @author Taven.Li
 *
 */
@Service
public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {
	
	
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(MySecurityFilter.class);
	
	@Autowired
	private MySecurityMetadataSource securityMetadataSource;
	@Autowired
	@Qualifier("myAuthenticationManager")
	private AuthenticationManager myAuthenticationManager;
	@Autowired
	private MyAccessDecisionManager accessDecisionManager;
	
	@PostConstruct
	public void init(){
		super.setAuthenticationManager(myAuthenticationManager);
		super.setAccessDecisionManager(accessDecisionManager);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("********************MySecurityFilter**********************");
		FilterInvocation fi = new FilterInvocation(request, response, chain);

		
		System.out.println("requestURL:"+fi.getRequestUrl());
		
		//在执行doFilter之前，进行权限的检查		
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
        	System.out.println("权限验证");
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            System.out.println("权限验证结束");
        } finally {
            super.afterInvocation(token, null);
        }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}
	
	@Override
	public void destroy() {
		
		
	}

	@Override
	public Class<?> getSecureObjectClass() {
		
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		
		return this.securityMetadataSource;
	}

	public MySecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	public void setSecurityMetadataSource(
			MySecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

}
