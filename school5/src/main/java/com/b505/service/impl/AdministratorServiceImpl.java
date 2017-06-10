package com.b505.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.Administrator;
import com.b505.bean.util.AdministratorUtil;
import com.b505.dao.IAdministratorDao;
import com.b505.dao.IBaseDao;
import com.b505.service.IAdministratorService;
@Service ("AdministratorService")
public class AdministratorServiceImpl extends BaseServiceImpl<Administrator> implements IAdministratorService{
	private IAdministratorDao iadministratorDao;
	@Autowired
	@Qualifier("IAdministratorDao")
	@Override
	public void setIBaseDao(IBaseDao<Administrator> iadministratorDao) {
		// TODO Auto-generated method stub
		this.baseDao = iadministratorDao;
		this.iadministratorDao = (IAdministratorDao)iadministratorDao;
	}
	@Override
	public List<AdministratorUtil> getAdministratorUtils() {
		// TODO Auto-generated method stub
		return iadministratorDao.getAdministratorUtils();
	}
	@Override
	public void deleteAdministratorByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		iadministratorDao.deleteAdministratorByBatch(list);
	}
	@Override
	public void updateAdministratorByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		iadministratorDao.updateAdministratorByBatch(list);
	}
	@Override
	public void saveAdministratorBayBatch(List<Administrator> list) {
		// TODO Auto-generated method stub
		iadministratorDao.saveAdministratorBayBatch(list);
		
	}
	@Override
	public Administrator getAdministratorBynickName(String nickName) {
		// TODO Auto-generated method stub
		return iadministratorDao.getAdministratorBynickName(nickName);
	}
	@Override
	public boolean updatePhoneByNickName(String phone, String nickName) {
		// TODO Auto-generated method stub
		return iadministratorDao.updatePhoneByNickName(phone, nickName);
	}

}
