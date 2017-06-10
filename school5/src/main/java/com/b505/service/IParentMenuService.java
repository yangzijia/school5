package com.b505.service;

import java.util.List;
import java.util.Map;

import com.b505.bean.ParentMenu;
import com.b505.bean.util.ParentMenuUtil;

public interface IParentMenuService extends IBaseService<ParentMenu> {
	public boolean isHaveParentMenu(String parentMenuName);
	public void saveParentMenuByBatch(List<ParentMenu> list);
	public void deleteParentMenuByBatch(List<Integer> list);
	public void updateParentMenuByBatch(List<Map<String, Object>> list);
	public List<ParentMenuUtil> getParentMenuUtils();
}
