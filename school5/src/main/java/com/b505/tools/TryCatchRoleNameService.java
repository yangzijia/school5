package com.b505.tools;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.RoleName;
import com.b505.service.IRoleNameService;

@Component
public class TryCatchRoleNameService 
{

	@Autowired
	private IRoleNameService roleNameService;
	
	
	public boolean saveRoleNameByBatch(List<RoleName> list) throws Exception
	{
		boolean status = false;
		try
		{
			roleNameService.saveRoleNameByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
	
	public boolean updateRoleNameByBatch(List<Map<String, Object>> list) throws Exception
	{
		boolean status = false;
		try
		{
			roleNameService.updateRoleNameByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
	
	public boolean deleteRoleNameByBatch(List<Integer> list) throws Exception
	{
		boolean status = false;
		try
		{
			roleNameService.deleteRoleNameByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
}

