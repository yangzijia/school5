package com.b505.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.HeadteacherClass;
import com.b505.dao.IBaseDao;
import com.b505.dao.IHeadteacherClassDao;
import com.b505.service.IHeadteacherClassService;

@Service("HeadteacherClassService")
public class HeadteacherClassServiceImpl extends BaseServiceImpl<HeadteacherClass> implements IHeadteacherClassService{

	@SuppressWarnings("unused")
	private IHeadteacherClassDao iheadTeacherClassDao;
	@Autowired
	@Qualifier("IHeadteacherClassDao")
	@Override
	public void setIBaseDao(IBaseDao<HeadteacherClass> iheadTeacherClassDao) {
		// TODO Auto-generated method stub
		this.baseDao = iheadTeacherClassDao;
		this.iheadTeacherClassDao = (IHeadteacherClassDao)iheadTeacherClassDao;
	}
	
}
