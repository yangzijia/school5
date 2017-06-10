package com.b505.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.LoginUser;
import com.b505.bean.RoleName;
import com.b505.bean.util.UserRole;
import com.b505.dao.IBaseDao;
import com.b505.dao.IUserRoleDao;
import com.b505.service.IRoleNameService;
import com.b505.service.IUserRoleService;

@Service("userRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl<LoginUser> implements IUserRoleService {
	@Autowired
	private IRoleNameService iRoleNameService;
	
	private IUserRoleDao iuserRoleDao;
	@Autowired
	@Qualifier("IUserRoleDao")

	
	@Override
	public void setIBaseDao(IBaseDao<LoginUser> iuserRoleDao) {
		// TODO Auto-generated method stub
		this.baseDao = iuserRoleDao;
		this.iuserRoleDao = (IUserRoleDao)iuserRoleDao;
	}
	@Override
	public boolean saveUserRole(UserRole userRole) {
		String nickName = userRole.getNickName();
		//取用户
		LoginUser loginUser = this.get("nickName", nickName);
		List<RoleName> userRoles = new ArrayList<>();
		//提交的角色ID
	    List<Integer> roles = userRole.getRoles();
	    for (Integer role : roles) {
	    	RoleName roleEntity = iRoleNameService.get(role);
			userRoles.add(roleEntity);
		}
	      loginUser.setRoles((List<RoleName>) userRoles);
	      
	      try {
				this.iuserRoleDao.update(loginUser);
			} catch (Exception e) {
				
				return false;
			}
		return true;
	}
}
