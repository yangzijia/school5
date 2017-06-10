package com.b505.tools;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.ParentMenu;
import com.b505.bean.util.ParentMenuUtil;
import com.b505.service.IParentMenuService;

@Component
public class TryCatchParentMenuService 
{
	@Autowired
	private IParentMenuService parentMenuService;
	
	
	public List<ParentMenu> getAllParentMenu()
	{
		List<ParentMenu> parentMenuList;
		try
		{
			parentMenuList = parentMenuService.getAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return parentMenuList = null;
		}
		return parentMenuList;
	}
	public List<ParentMenuUtil> getParentMenuUtils(){
		List<ParentMenuUtil> parentMenuUtilList;
		try
		{
			parentMenuUtilList = parentMenuService.getParentMenuUtils();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return parentMenuUtilList = null;
		}
		return parentMenuUtilList;
	}
	
	public boolean saveParentMenuByBatch(List<ParentMenu> list)
	{
		boolean status = false;
		try
		{
			parentMenuService.saveParentMenuByBatch(list);
			status = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	
	public boolean updateParentMenuByBatch(List<Map<String, Object>> list)
	{
		boolean status = false;
		try
		{
			parentMenuService.updateParentMenuByBatch(list);
			status = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	
	public boolean deleteParentMenuByBatch(List<Integer> list)
	{
		boolean status = false;
		try
		{
			parentMenuService.deleteParentMenuByBatch(list);
			status = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	
	public boolean isHaveParentMenu(String parentMenuName)
	{
		boolean status = false;
		try
		{
			status = parentMenuService.isHaveParentMenu(parentMenuName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
}

