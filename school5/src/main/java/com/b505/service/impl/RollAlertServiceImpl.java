package com.b505.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.Roll_Alert;
import com.b505.dao.IBaseDao;
import com.b505.dao.IRollAlertDao;
import com.b505.service.IRollAlertService;
@Service("RollAlertService")
public class RollAlertServiceImpl extends BaseServiceImpl<Roll_Alert> implements IRollAlertService
{
	@SuppressWarnings("unused")
	private IRollAlertDao iRollAlertDao;
	@Autowired
	@Qualifier("IRollAlertDao")
	@Override
	public void setIBaseDao(IBaseDao<Roll_Alert> iRollAlertDao) {
		// TODO Auto-generated method stub
		this.baseDao = iRollAlertDao;
		this.iRollAlertDao = (IRollAlertDao)iRollAlertDao;
	}
}
