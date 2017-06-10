package com.b505.service;

import java.util.List;

import com.b505.bean.util.AdminControllerUtil;

public interface IAdminControllerService extends IBaseService<AdminControllerUtil>
{

	public List<AdminControllerUtil> getAdminControllerUtilByGrade(String yearClass,
			String profession, String classId);

}
