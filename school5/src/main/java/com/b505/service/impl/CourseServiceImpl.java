package com.b505.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.Course;
import com.b505.dao.IBaseDao;
import com.b505.dao.ICourseDao;
import com.b505.service.ICourseService;

@Service ("CourseService")
public class CourseServiceImpl extends BaseServiceImpl<Course> implements ICourseService{

	private ICourseDao icourseDao;
	@Autowired
	@Qualifier("ICourseDao")
	@Override
	public void setIBaseDao(IBaseDao<Course> icourseDao) {
		// TODO Auto-generated method stub
		this.baseDao = icourseDao;
		this.icourseDao = (ICourseDao)icourseDao;
	}
	
	@Override
	public Course getCourseByClassId(int id){
		
		return icourseDao.getCourseByClassId(id);
	}
}
