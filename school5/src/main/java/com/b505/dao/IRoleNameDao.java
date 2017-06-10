package com.b505.dao;

import java.util.List;
import java.util.Map;

import com.b505.bean.RoleName;
import com.b505.bean.util.RoleNameUtil;

public interface IRoleNameDao extends IBaseDao<RoleName> {
	public void saveRoleNameByBatch(List<RoleName> list);
	public void updateRoleNameByBatch(List<Map<String, Object>> list);
	public void deleteRoleNameByBatch(List<Integer> list);
	public List<RoleNameUtil> getRoleNameUtils();
	
}
