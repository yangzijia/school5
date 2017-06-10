package com.b505.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Roll_Alert_Head;
import com.b505.service.IRollAlertHeadService;

@Component
public class TryCatchRollAlertHeadService
{
	@Autowired
	private IRollAlertHeadService rAlertHdService;
	/**
	 * @功能：二级学院教学科录入不及格、重修、留降级的信息表的标题 
	 * @time：2016.02.20
	 * @param r_Allert_Hd
	 * @return
	 */
	public boolean saveRoleAlertHead(Roll_Alert_Head r_Allert_Hd)
	{
		try
		{
			rAlertHdService.save(r_Allert_Hd);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public int getMaxid()
	{
		int id=0;
		try
		{
			id=rAlertHdService.getMaxid();
		} catch (Exception e)
		{
			// TODO: handle exception
			return id;
		}
		// TODO Auto-generated method stub
		return id;
	}
	
}
