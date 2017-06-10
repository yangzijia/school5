package com.b505.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.util.AdminControllerUtil;
import com.b505.service.IAdminControllerService;

@Component
public class TryCatchAdminControllerService
{
	@Autowired
	private IAdminControllerService adminControllerService;

	

	public List<AdminControllerUtil> getAdminControllerUtilByGrade(
			String yearClass, String profession, String classId)
	{
		// TODO Auto-generated method stub
		List<AdminControllerUtil> list;
		try
		{
			list=adminControllerService.getAdminControllerUtilByGrade(yearClass,profession,classId);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			list = null;
		}
		return list;
	}

	
	
}
