package com.b505.tools;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.b505.security.WebUserDetails;

@Service
public class SessionGet {
	//该方法用来读取保存在session里的登录用户的一些信息
	public WebUserDetails getUserInfo(){
		WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	      return webUserDetails;
	}

}
