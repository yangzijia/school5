package com.b505.tools;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.ChildrenMenu;
import com.b505.bean.util.ChildrenMenuUtil;
import com.b505.service.IChildrenMenuService;

@Component
public class TryCatchChildrenMenuService
{
	@Autowired
	private IChildrenMenuService  childrenMenuService;
	
	/*
	 * @方法名：getChildrenMenuByparent(String parentMenuName)
	 * @功能：通过父菜单名字得到子菜单
	 * @功能说明：通过父菜单名字得到子菜单
	 * @作者：李振强
	 * @创建时间：2014.5.19
	 * @修改时间：2014.5.19
	 * @修改说明：代码重构
	 */
	public List<ChildrenMenu> getChildrenMenuByparent(String parentMenuName)
	{
		List<ChildrenMenu> childrenMenuList;
		try
		{
			childrenMenuList = childrenMenuService.getChildrenMenuByparent(parentMenuName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return childrenMenuList = null;
		}
		return childrenMenuList;
	}
	
	public List<ChildrenMenuUtil> getChildrenMenuUtils(){
		List<ChildrenMenuUtil> ChildrenMenuUtilList;
		try
		{
			ChildrenMenuUtilList = childrenMenuService.getAllChilderUtils();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ChildrenMenuUtilList = null;
		}
		return ChildrenMenuUtilList;
	}

	public boolean deleteChildrenMenuByBatch(List<Integer> list)
	{
		boolean status = false;
		try
		{
			childrenMenuService.deleteChildrenMenuByBatch(list);
			status = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	
	public boolean updateChildrenMenuByBatch(List<Map<String, Object>> list)
	{
		boolean status = false;
		try
		{
			childrenMenuService.updateChildrenMenuByBatch(list);
			status = true;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	
	public boolean saveChildrenMenuByBatch(List<ChildrenMenu> list)
	{
		boolean status = false;
		try
		{
			childrenMenuService.saveChildrenMenuByBatch(list);
			status = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	
	public boolean isHaveChrildrenMenu(String url)
	{
		boolean status = false;
		try
		{
			status = childrenMenuService.isHaveChrildrenMenu(url);		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
}

