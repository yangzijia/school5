package com.b505.service;

import com.b505.bean.LoginUser;
import com.b505.bean.util.UserRole;

public interface IUserRoleService extends IBaseService<LoginUser>{
	public boolean saveUserRole(UserRole user);
}
