package com.b505.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class GetAuthenticationUser {
	
public String getNickName(){
	 WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 return webUserDetails.getUsername();
   }
}
