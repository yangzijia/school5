package com.b505.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.Grade;
import com.b505.bean.util.GradeUtil;
import com.b505.dao.IBaseDao;
import com.b505.dao.IGradeDao;
import com.b505.service.IGradeService;
@Service("GradeService")
public class GradeServiceImpl extends BaseServiceImpl<Grade> implements IGradeService {
	private IGradeDao igradeDao;
	@Autowired
	@Qualifier("IGradeDao")
	@Override
	public void setIBaseDao(IBaseDao<Grade> igradeDao) {
		// TODO Auto-generated method stub
		this.baseDao = igradeDao;
		this.igradeDao = (IGradeDao)igradeDao;
		
	}
	@Override
	public List<GradeUtil> getGradeByCollege(String collegeName){
		return igradeDao.getGradeByCollege(collegeName);
	}
	@Override
	public String[] getyearClass(){
		return igradeDao.getyearClass();
	}
	@Override
	public String[] getProfesssionByCY(String collegeName, String yearClass){
	 return igradeDao.getProfesssionByCY(collegeName, yearClass);
	}
	@Override
	public String[] getClassIdByCYP(String collegeName, String yearClass,String profession){
		return igradeDao.getClassIdByCYP(collegeName, yearClass, profession);
	}
	@Override
	public Grade getByProperty(String yearClass, String profession ,String classId){
		return igradeDao.getByProperty(yearClass, profession, classId);
		
	}
	@Override
	public void saveGradeByBatch(List<Grade> list) {
		// TODO Auto-generated method stub
		igradeDao.saveGradeByBatch(list);
	}
	@Override
	public void updateGradeByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		igradeDao.updateGradeByBatch(list);
	}
	@Override
	public void deleteGradeByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		igradeDao.deleteGradeByBatch(list);
		
	}
	@Override
	public List<GradeUtil> getGradeUtilsByHeadTeacherNickName(String nickName) {
		// TODO Auto-generated method stub
		return igradeDao.getGradeUtilsByHeadTeacherNickName(nickName);
	}
	@Override
	public void updateGradeUtilsByHeadTeacherNickName(List<GradeUtil> list){
		// TODO Auto-generated method stub
		igradeDao.updateGradeUtilsByHeadTeacherNickName(list);
		
	}
	@Override
	public int getGradeIdByGrade(String yearClass,String profession,String classId){
		
		return igradeDao.getGradeIdByGrade(yearClass, profession, classId);
	}
}
