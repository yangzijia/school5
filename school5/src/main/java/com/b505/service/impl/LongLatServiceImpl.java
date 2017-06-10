package com.b505.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatType;
import com.b505.bean.util.HteacherLongLatUtil;
import com.b505.bean.util.LongLatUtil;
import com.b505.dao.IBaseDao;
import com.b505.dao.ILongLatDao;
import com.b505.service.ILongLatService;
@Service("LongLatService")
public class LongLatServiceImpl extends BaseServiceImpl< LongLat> implements ILongLatService {
	private ILongLatDao ilongLatDao;
	@Autowired
	@Qualifier("ILongLatDao")
	@Override
	public void setIBaseDao(IBaseDao<LongLat> ilongLatDao) {
		// TODO Auto-generated method stub
		this.baseDao = ilongLatDao;
		this.ilongLatDao = (ILongLatDao)ilongLatDao;
	}
	@Override
	public List<LongLat> getLongLatByStudentId(String id) {
		
		return ilongLatDao.getLongLatByStudentId(id);
	}
	@Override
	public Long getLongLatLengthByGrade(String yearClass,String profession, String classId) {
	    return ilongLatDao.getLongLatLengthByGrade(yearClass, profession, classId);
	}
	@Override
	public int deletedLongLatByID(LongLat longLat) {
		// TODO Auto-generated method stub
		this.delete(longLat);
		int b = 0;
		if(ilongLatDao.get(longLat.getMapId())==null){
			b = 1;
		}
		return b;
	}
	@Override
	public List<LongLatUtil> getLongLatUtilByGrade(int gradeId) {
		// TODO Auto-generated method stub
		return ilongLatDao.getLongLatUtilByGrade(gradeId);
	}
	@Override
	public List<LongLat> getLongLatClientUtilByGrade(int gradeId) {
		// TODO Auto-generated method stub
		return ilongLatDao.getLongLatClientUtilByGrade(gradeId);
	}
	@Override
	public List<LongLatUtil> getAllLongLatUtil() {
		// TODO Auto-generated method stub
		return ilongLatDao.getAllLongLatUtil();
	}
	@Override
	public List<HteacherLongLatUtil> getHteacherAllLongLatUtil(){
		return ilongLatDao.getHteacherAllLongLatUtil();
	}
	@Override
	public List<LongLatType> getAllLatTypes(){
		
		return ilongLatDao.getAllLatTypes();
	}	
	@Override
	public List<LongLatUtil> getLongLatUtilByStudentAdmin(){
		return ilongLatDao.getLongLatUtilByStudentAdmin();
	}	
	@Override
	public List<LongLatUtil> getLongLatUtilByCollegeAdmin(){
		return ilongLatDao.getLongLatUtilByCollegeAdmin();
	}	
	@Override
	public List<LongLatUtil> getLongLatUtilByLogisticsAdmin(){
		return ilongLatDao.getLongLatUtilByLogisticsAdmin();
	}	
	@Override
	public List<LongLatUtil> getLongLatUtilByDormAdmin(){
		return ilongLatDao.getLongLatUtilByDormAdmin();
	}
	@Override
	public List<LongLat> getLongLatClientUtilByStudentAdmin(){
		return ilongLatDao.getLongLatClientUtilByStudentAdmin();
	}
	@Override
	public List<LongLat> getLongLatClientUtilByCollegeAdmin(){
		return ilongLatDao.getLongLatClientUtilByCollegeAdmin();
	}
	@Override
	public List<LongLat> getLongLatClientUtilByLogisticsAdmin(){
		return ilongLatDao.getLongLatClientUtilByLogisticsAdmin();
	}
	@Override
	public List<LongLat> getLongLatClientUtilByDormAdmin(){
		return ilongLatDao.getLongLatClientUtilByDormAdmin();
	}
	@Override
	public List<LongLatUtil> getLongLatUtilByLongLattype(String longlattype){
		return ilongLatDao.getLongLatUtilByLongLattype(longlattype);
	}
	@Override
	public List<LongLatUtil> getTeacherLongLatUtilByLongLattype(String longlattype){
		return ilongLatDao.getTeacherLongLatUtilByLongLattype(longlattype);
	}
	@Override
	public List<LongLatUtil> getLongLatUtilByDate(String date1,String date2){
		return ilongLatDao.getLongLatUtilByDate(date1,date2);
	}
	@Override
	public List<LongLatUtil> getTeacherLongLatUtilByDate(String date1,String date2){
		return ilongLatDao.getTeacherLongLatUtilByDate(date1, date2);
	}
	
	
	
	@Override
	public List<LongLat> getAllLongLat() {
		// TODO Auto-generated method stub
		return ilongLatDao.getAllLongLat();
	}
}
