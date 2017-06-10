package com.b505.dao;



import java.util.List;
import java.util.Map;

import com.b505.bean.ChildrenMenu;
import com.b505.bean.util.ChildrenMenuUtil;

public interface IChildrenMenuDao extends IBaseDao<ChildrenMenu> {
	public List <ChildrenMenu> getChildrenMenuByparent(String parentMenuName);
	public  void deleteChildrenMenuByBatch(List<Integer> list);
	public void saveChildrenMenuByBatch(List<ChildrenMenu> list);
	public void updateChildrenMenuByBatch(List<Map<String, Object>> list);
	public List<ChildrenMenu> getByUrlOrByChildrenMenuname(String url);
	public List <ChildrenMenuUtil> getAllChilderUtils();
	public List<ChildrenMenu> getChildrenMenusByRoleAndParent(String roleName,String parentMenu);
}
