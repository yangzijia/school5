package com.b505.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.UserRecord;
import com.b505.dao.IBaseDao;
import com.b505.dao.IUserRecordDao;
import com.b505.service.IUserRecordService;

@Service("IUserRecordService")
public class UserRecordServiceImpl extends BaseServiceImpl<UserRecord> implements IUserRecordService {

	@SuppressWarnings("unused")
	private IUserRecordDao iUserRecordDao;
	@Autowired
	@Qualifier("IUserRecordDao")
	@Override
	public void setIBaseDao(IBaseDao<UserRecord> iUserRecordDao) {
		// TODO Auto-generated method stub
		this.baseDao = iUserRecordDao;
		this.iUserRecordDao = (IUserRecordDao)iUserRecordDao;
	}

}
