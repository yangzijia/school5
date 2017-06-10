package com.b505.security;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
/**
 * 
 * @author 少游
 *@功能：自定义一个CustomLogoutFilter类，实现用户的退出
 *@2016.9.16
 */

public class CustomLogoutFilter extends LogoutFilter {

    public CustomLogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
        super(logoutSuccessUrl, handlers);
    }

    public CustomLogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
            LogoutHandler[] handlers) {
        super(logoutSuccessHandler, handlers);
    }

}