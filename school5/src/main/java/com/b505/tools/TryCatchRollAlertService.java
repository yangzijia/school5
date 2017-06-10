package com.b505.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Roll_Alert;
import com.b505.service.IRollAlertService;

@Component
public class TryCatchRollAlertService
{
	
	
	@Autowired
	private IRollAlertService rAlertService;
	
	/**
	 * @功能：二级学院教学科录入不及格、重修、留降级的信息 
	 * @time：2016.02.15
	 * @param r_Allert
	 * @return
	 */
	public boolean saveRoleAlert(Roll_Alert r_Alert)
	{
		try
		{
			rAlertService.save(r_Alert);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
