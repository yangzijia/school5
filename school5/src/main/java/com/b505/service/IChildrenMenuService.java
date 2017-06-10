package com.b505.service;

import java.util.List;
import java.util.Map;

import com.b505.bean.ChildrenMenu;
import com.b505.bean.util.ChildrenMenuUtil;

public interface IChildrenMenuService extends IBaseService<ChildrenMenu>{
	public List<ChildrenMenu> getChildrenMenuByparent(String parentMenuName);
	public  void deleteChildrenMenuByBatch(List<Integer> list);
	public void saveChildrenMenuByBatch(List<ChildrenMenu> list);
	public void updateChildrenMenuByBatch(List<Map<String, Object>> list);
	public boolean isHaveChrildrenMenu(String url);
	public List<ChildrenMenuUtil> getAllChilderUtils();
	public List<ChildrenMenu> getChildrenMenusByRoleAndParent(String roleName,String parentMenu);
}
