package com.b505.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.CallOverCount;
import com.b505.bean.util.CallOverCountUtil;
import com.b505.bean.util.CallOverCountUtils;
import com.b505.dao.IBaseDao;
import com.b505.dao.ICallOverCountDao;
import com.b505.service.ICallOverCountService;
@Service("CallOverCountService")
public class CallOverCountServiceImpl extends BaseServiceImpl<CallOverCount> implements ICallOverCountService {
	private ICallOverCountDao icallOverCountDao;
	@Autowired
	@Qualifier("ICallOverCountDao")
	@Override
	public void setIBaseDao(IBaseDao<CallOverCount> icallOverCountDao) {
		// TODO Auto-generated method stub
		this.baseDao = icallOverCountDao;
		this.icallOverCountDao = (ICallOverCountDao)icallOverCountDao;
	}
	@Override
	public CallOverCount getCallOverCountBysnumber(String studentNumber) {
		// TODO Auto-generated method stub
		return icallOverCountDao.getCallOverCountBysnumber(studentNumber);
	}
	@Override
	public List<CallOverCountUtil> getCountUtilsByCollege(String collgeName) {
		// TODO Auto-generated method stub
		return icallOverCountDao.getCountUtilsByCollege(collgeName);
	}
	@Override
	public List<CallOverCountUtil> getCountUtilsByGrade(String yearClass,
			String profession, String classId) {
		// TODO Auto-generated method stub
		return icallOverCountDao.getCountUtilsByGrade(yearClass, profession, classId);
	}
	@Override
	public Long getTotalCountByCollegeAndStatus(String collegeName) {
		// TODO Auto-generated method stub
		return icallOverCountDao.getTotalCountByCollegeAndStatus(collegeName);
	}
	@Override
	public List<CallOverCountUtil> getCountUtilsByPagerAndCollege(
			String collgeName, Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		return icallOverCountDao.getCountUtilsByPagerAndCollege(collgeName, currentPage, pageSize);
	}
	@Override
	public List<CallOverCountUtils> getWarnCountUtilsByGrade(String yearClass,String profession,String classId ){
		return 	icallOverCountDao.getWarnCountUtilsByGrade(yearClass, profession, classId);
	}
}
