package com.b505.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.b505.bean.util.AdminControllerUtil;
import com.b505.dao.IAdminControllerDao;
import com.b505.dao.IBaseDao;
import com.b505.service.IAdminControllerService;
@Service("AdminControllerService")
public class AdminControllerServiceImpl extends BaseServiceImpl<AdminControllerUtil> implements IAdminControllerService
{   
	private IAdminControllerDao iAdminControllerDao;
	@Autowired
	@Qualifier("IAdminControllerDao")
	@Override
	public void setIBaseDao(IBaseDao<AdminControllerUtil> iAdminControllerDao) {
		// TODO Auto-generated method stub
		this.baseDao = iAdminControllerDao;
		this.iAdminControllerDao = (IAdminControllerDao) iAdminControllerDao;
	}
	@Override
	public List<AdminControllerUtil> getAdminControllerUtilByGrade(
			String yearClass, String profession, String classId)
	{
		// TODO Auto-generated method stub
		return iAdminControllerDao.getAdminControllerUtilByGrade(yearClass,profession,classId);
	}
}
