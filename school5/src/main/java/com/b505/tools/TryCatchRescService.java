package com.b505.tools;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Resc;
import com.b505.service.IRescService;

@Component
public class TryCatchRescService
{
	@Autowired
	private IRescService rescservice;
	
	public boolean saveRescByBatch(List<Resc> rescList)
	{
		boolean status = false;
		try
		{
			rescservice.saveRescByBatch(rescList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
	public boolean deleteRescByBatch(List<Integer> list)
	{
		boolean status = false;
		try
		{
			rescservice.deleteRescByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
	public boolean updateRescByBatch(List<Map<String, Object>> list)
	{
		boolean status = false;
		try
		{
			rescservice.updateRescByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
}
