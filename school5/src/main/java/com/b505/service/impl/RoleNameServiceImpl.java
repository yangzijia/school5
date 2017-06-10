package com.b505.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.RoleName;
import com.b505.bean.util.RoleNameUtil;
import com.b505.dao.IBaseDao;
import com.b505.dao.IRoleNameDao;
import com.b505.service.IRoleNameService;
@Service("RoleService")
public class RoleNameServiceImpl extends BaseServiceImpl<RoleName> implements IRoleNameService{
	private IRoleNameDao iroleNameDao;
	@Autowired
	@Qualifier("IRoleDao")
	@Override
	public void setIBaseDao(IBaseDao<RoleName> iroleNameDao) {
		// TODO Auto-generated method stub
		this.baseDao = iroleNameDao;
		this.iroleNameDao = (IRoleNameDao)iroleNameDao;
		
	}
	@Override
	public void saveRoleNameByBatch(List<RoleName> list) {
		// TODO Auto-generated method stub
		iroleNameDao.saveRoleNameByBatch(list);
	}
	@Override
	public void updateRoleNameByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		iroleNameDao.updateRoleNameByBatch(list);
	}
	@Override
	public void deleteRoleNameByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		iroleNameDao.deleteRoleNameByBatch(list);
	}
	@Override
	public List<RoleNameUtil> getRoleNameUtils() {
		// TODO Auto-generated method stub
		return iroleNameDao.getRoleNameUtils();
	}

}
