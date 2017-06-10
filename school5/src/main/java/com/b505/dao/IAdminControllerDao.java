package com.b505.dao;

import java.util.List;

import com.b505.bean.util.AdminControllerUtil;

public interface IAdminControllerDao extends IBaseDao<AdminControllerUtil>
{


	public List<AdminControllerUtil> getAdminControllerUtilByGrade(
			String yearClass, String profession, String classId);

}
