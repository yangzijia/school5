package com.b505.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.stereotype.Service;

import com.b505.bean.Resc;
import com.b505.bean.RoleName;
import com.b505.json.JsonAnalyze;
import com.b505.service.IChildrenMenuService;
import com.b505.service.IRescService;
import com.b505.service.IRoleNameService;


@Service
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource
{
	@Autowired
	private IRescService rescService;
	@Autowired
	private IRoleNameService roleNameService;
	@Autowired
	private IChildrenMenuService childrenMenuSerivce;
	@Autowired
	private JsonAnalyze jsonAnalyze;

	private static Map<String, Collection<ConfigAttribute>> resourceMap1 = new HashMap<String, Collection<ConfigAttribute>>();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();


	@PostConstruct
	public void initResource() throws IOException{

		resourceMap.clear();

		//取得当前系统所有可用角色
		resourceMap1.clear();
		List<RoleName> roleList = roleNameService.getAll();
		for (RoleName role : roleList)
		{
			//调用加载资源的方法
			this.loadRole(role);
		}

	}
	
	private void loadRole(RoleName role){

		//这里的 role 参数为自己定义的，要和 UserDetailsService 中的 SimpleGrantedAuthority 参数对应
		//role 参数也可以直接使用角色名
		ConfigAttribute ca = new SecurityConfig(role.getRoleName());
		//取角色有哪些资源的权限
		List<Resc> resources = role.getResc();
		final int resourcesSize = resources.size();
		for(int i = 0; i < resourcesSize; i++)
		{

			String url=resources.get(i).getName();
			if (StringUtils.isBlank(url)) {
				continue;
			} 

			if (resourceMap.containsKey(url)) {
				resourceMap.get(url).add(ca);
			} else {
				Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
				atts.add(ca);
				resourceMap.put(url, atts);
			}
		}
		System.out.println("resoucMap:"+resourceMap);

	}


	public Map<String, Collection<ConfigAttribute>> getResourceMap()
	{
		return resourceMap;
	}
	public Map<String, Collection<ConfigAttribute>> getResourceMap1()
	{
		return resourceMap1;
	}
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

		HttpServletRequest request = ((FilterInvocation)object).getRequest();
		Iterator<String> ite = resourceMap.keySet().iterator();

		while (ite.hasNext()) {
			String resourceURL = ite.next();
			//AntPathRequestMatcher : 来自于Ant项目，是一种简单易懂的路径匹配策略。
			//RegexRequestMatcher : 如果 AntPathRequestMatcher 无法满足需求，
			//还可以选择使用更强大的RegexRequestMatcher，它支持使用正则表达式对URL地址进行匹配
			RequestMatcher requestMatcher = new AntPathRequestMatcher(resourceURL);
			if (requestMatcher.matches(request)) {
				return resourceMap.get(resourceURL);
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

		for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}

		return allAttributes;
	}

	public void reloadResource() throws IOException{
		//
		this.initResource();
	}
}

