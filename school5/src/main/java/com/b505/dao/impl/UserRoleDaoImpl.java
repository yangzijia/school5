package com.b505.dao.impl;

import org.springframework.stereotype.Repository;

import com.b505.bean.LoginUser;
import com.b505.dao.IUserRoleDao;

@Repository(value="IUserRoleDao")
public class UserRoleDaoImpl extends BaseDaoImpl<LoginUser>  implements IUserRoleDao{

}
