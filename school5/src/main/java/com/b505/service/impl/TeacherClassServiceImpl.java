package com.b505.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.TeacherClass;
import com.b505.bean.util.TeacherId;
import com.b505.dao.IBaseDao;
import com.b505.dao.ITeacherClassDao;
import com.b505.service.ITeacherClassService;
@Service("TeacherClassService")
public class TeacherClassServiceImpl extends BaseServiceImpl<TeacherClass> implements ITeacherClassService {
	private ITeacherClassDao iteacherClassDao;
	@Autowired
	@Qualifier("ITeacherClassDao")
	@Override
	public void setIBaseDao(IBaseDao<TeacherClass> iteacherClassDao) {
		// TODO Auto-generated method stub
		this.baseDao = iteacherClassDao;
		this.iteacherClassDao = (ITeacherClassDao)iteacherClassDao;
	}
	
	@Override
	public TeacherId getTidByS_C_ID(int gradeId){
		
		return 	iteacherClassDao.getTidByS_C_ID(gradeId);
	}

}
