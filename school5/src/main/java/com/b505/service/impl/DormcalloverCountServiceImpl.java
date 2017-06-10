package com.b505.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.DormcalloverCount;
import com.b505.dao.IBaseDao;
import com.b505.dao.IDormcalloverCountDao;
import com.b505.service.IDormcalloverCountService;

@Service("DormcalloverCountService")
public class DormcalloverCountServiceImpl extends BaseServiceImpl<DormcalloverCount> implements IDormcalloverCountService{

	private IDormcalloverCountDao iDormcallOverCountDao;
	@Autowired
	@Qualifier("IDormcalloverCountDao")
	@Override
	public void setIBaseDao(IBaseDao<DormcalloverCount> iDormcallOverCountDao) {
		// TODO Auto-generated method stub
		this.baseDao = iDormcallOverCountDao;
		this.iDormcallOverCountDao = (IDormcalloverCountDao)iDormcallOverCountDao;
	}
	
	@Override
	public DormcalloverCount getDormcalloverCountBysnumber(String studentNumber){
		return iDormcallOverCountDao.getDormcalloverCountBysnumber(studentNumber);
	}
}
