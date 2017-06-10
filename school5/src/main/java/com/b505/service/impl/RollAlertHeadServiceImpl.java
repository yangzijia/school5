package com.b505.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.Roll_Alert_Head;
import com.b505.dao.IBaseDao;
import com.b505.dao.IRollAlertHeadDao;

import com.b505.service.IRollAlertHeadService;

@Service("RollAlertHeadService")
public class RollAlertHeadServiceImpl extends BaseServiceImpl<Roll_Alert_Head> implements IRollAlertHeadService
{
	private IRollAlertHeadDao iRollAlertHeadDao;
	@Autowired
	@Qualifier("IRollAlertHeadDao")
	@Override
	public void setIBaseDao(IBaseDao<Roll_Alert_Head> iRollAlertHeadDao)
	{
		// TODO Auto-generated method stub
		this.baseDao = iRollAlertHeadDao;
		this.iRollAlertHeadDao = (IRollAlertHeadDao) iRollAlertHeadDao;
	}
	@Override
	public int getMaxid(){
		
		return 	iRollAlertHeadDao.getMaxid();
	}
	
}
