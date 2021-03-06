package com.b505.dao;

import java.util.List;
import java.util.Map;

import com.b505.bean.Administrator;
import com.b505.bean.util.AdministratorUtil;

public interface IAdministratorDao extends IBaseDao<Administrator> {
	public List<AdministratorUtil> getAdministratorUtils();
	public void deleteAdministratorByBatch(List<Integer> list);
	public void updateAdministratorByBatch(List<Map<String, Object>> list);
	public void saveAdministratorBayBatch(List<Administrator> list);
	public Administrator getAdministratorBynickName(String nickName);
	public boolean updatePhoneByNickName(String phone, String nickName);
}
