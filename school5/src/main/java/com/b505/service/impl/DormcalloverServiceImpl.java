package com.b505.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.Dormcallover;
import com.b505.dao.IBaseDao;
import com.b505.dao.IDormcalloverDao;
import com.b505.service.IDormcalloverService;
@Service("DormcalloverService")
public class DormcalloverServiceImpl extends BaseServiceImpl<Dormcallover> implements IDormcalloverService{

	private IDormcalloverDao iDormcalloverDao;
	@Autowired
	@Qualifier("IDormcalloverDao")
	@Override
	public void setIBaseDao(IBaseDao<Dormcallover> iDormcalloverDao) {
		// TODO Auto-generated method stub
		this.baseDao = iDormcalloverDao;
		this.iDormcalloverDao = (IDormcalloverDao)iDormcalloverDao;
	}
	
	@Override
	public void saveDormcalloverByBatch(List<Dormcallover> list){
		iDormcalloverDao.saveDormcalloverByBatch(list);
	}
	
	@Override
	public List<Dormcallover> getDormcalloversByGradeAndDate(int gradeId,String date){
		return iDormcalloverDao.getDormcalloversByGradeAndDate(gradeId, date);
	}
	
	@Override
	public List<Dormcallover> getDormcalloversByGrade(int gradeId){
		return iDormcalloverDao.getDormcalloversByGrade(gradeId);
	}
	
	@Override
	public void delDormcalloverByBatch(List<Integer> list){
		iDormcalloverDao.delDormcalloverByBatch(list);
	}
}
