package com.b505.service;

import java.util.List;
import java.util.Map;

import com.b505.bean.RoleName;
import com.b505.bean.util.RoleNameUtil;

public interface IRoleNameService extends IBaseService<RoleName>{
	public void saveRoleNameByBatch(List<RoleName> list);
	public void updateRoleNameByBatch(List<Map<String, Object>> list);
	public void deleteRoleNameByBatch(List<Integer> list);
	public List<RoleNameUtil> getRoleNameUtils();
}
