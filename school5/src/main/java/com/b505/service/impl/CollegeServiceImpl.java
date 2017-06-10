package com.b505.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.b505.bean.College;
import com.b505.dao.IBaseDao;
import com.b505.dao.ICollegeDao;
import com.b505.service.ICollegeService;

@Service("CollegeService")
public class CollegeServiceImpl extends BaseServiceImpl<College> implements ICollegeService {
	private ICollegeDao icollegeDao;
	@Autowired
	@Qualifier("ICollegeDao")
	
	@Override
	public void setIBaseDao(IBaseDao<College> icollegeDao) {
		// TODO Auto-generated method stub
		this.baseDao = icollegeDao;
		this.icollegeDao = (ICollegeDao)icollegeDao;
	}
	@Override
	public String[] getAllCollegeName(){
		 return icollegeDao.getAllCollegeName();
	}
	@Override
	public String getCollege(Integer id){
	
		return icollegeDao.getCollege(id);
	}
	@Override
	public void saveCollegeByBatch(List<College> list) {
		// TODO Auto-generated method stub
		icollegeDao.saveCollegeByBatch(list);
	}
	@Override
	public void deleteCollegeByBatch(List<Integer> list){
		// TODO Auto-generated method stub
		try {
			icollegeDao.deleteCollegeByBatch(list);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

	}
	@Override
	public void updateCollegeByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		icollegeDao.updateCollegeByBatch(list);
	}
	
	@Override
	public int getCollegeIdByGradeId(int gradeId){
		
		return icollegeDao.getCollegeIdByGradeId(gradeId);
	}
	
	
}
