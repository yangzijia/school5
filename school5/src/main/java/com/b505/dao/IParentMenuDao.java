package com.b505.dao;

import java.util.List;
import java.util.Map;

import com.b505.bean.ParentMenu;
import com.b505.bean.util.ParentMenuUtil;

public interface IParentMenuDao extends IBaseDao<ParentMenu> {
	public void saveParentMenuByBatch(List<ParentMenu> list);
	public void deleteParentMenuByBatch(List<Integer> list);
	public void updateParentMenuByBatch(List<Map<String, Object>> list);
	public List<ParentMenuUtil> getParentMenuUtils();
}
