package com.b505.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.CallOver;
import com.b505.bean.util.CallOverUtil;
import com.b505.bean.util.CallOverUtils;
import com.b505.dao.IBaseDao;
import com.b505.dao.ICallOverDao;
import com.b505.service.ICallOverService;

@Service("CallOverService")
public class CallOverServiceImpl extends BaseServiceImpl<CallOver> implements ICallOverService{ 
	private ICallOverDao icallOverDao;
	@Autowired
	@Qualifier("ICallOverDao")
	@Override
	public void setIBaseDao(IBaseDao<CallOver> icallOverDao) {
		// TODO Auto-generated method stub
		this.baseDao = icallOverDao;
		this.icallOverDao = (ICallOverDao)icallOverDao;
	}
	@Override
	public List<String> getAllDate(){
		return icallOverDao.getAllDate();
	}
	@Override
	public List<String>getAllClass(){
		return icallOverDao.getAllClass();
		
	}
	@Override
	public List<CallOver> getStudentListByDC(String date,String className){
		return icallOverDao.getStudentListByDC(date, className);
	}
	@Override
	public List<CallOver> getStudentListByTN(String teacherName){
		return icallOverDao.getStudentListByTN(teacherName);
	}
	@Override
	public int deleteCallOverByID(CallOver callOver) {
		// TODO Auto-generated method stub
		int b = 0;
		this.delete(callOver);
		if(this.get(callOver).getId()==null){
			b = 1;
		}
		return b;
	}
	@Override
	public List<String> getClassNameByClassID(Integer gradeId) {
		return icallOverDao.getClassNameByClassID(gradeId);
	}
	@Override
	public List<CallOver> getStudentListByDGCC(String date, Integer gradeID,
			String classTime, String className){
		return icallOverDao.getStudentListByDGCC(date, gradeID, classTime, className);
	}
	@Override
	public void saveCallOverByBatch(List<CallOver> list){
		icallOverDao.saveCallOverByBatch(list);
	}
	@Override
	public boolean updateCallOver( String classTime,
			String studentNumber, String status, String date) {
		// TODO Auto-generated method stub
		return icallOverDao.updateCallOver( classTime, studentNumber, status, date);
	}
	@Override
	public List<CallOverUtil> getCallOverByStudentNumber(String studentNumber) {
		// TODO Auto-generated method stub
		return icallOverDao.getCallOverByStudentNumber(studentNumber);
	}
	@Override
	public CallOver getStudentCallOver(String date, String classTime,
			String studentNumber) {
		// TODO Auto-generated method stub
		return icallOverDao.getStudentCallOver(date, classTime, studentNumber);
				
	}
	@Override
	public int deleteCallOver(String date, String classTime,
			String studentNumber) {
		// TODO Auto-generated method stub
		return icallOverDao.deleteCallOver(date, classTime, studentNumber);
	}
	
	@Override
	public List<CallOverUtils> getCallOverBycollegeNameAndTime(String collgeName,String STime,String ETime){
		return icallOverDao.getCallOverBycollegeNameAndTime(collgeName, STime, ETime);
	}
	
	@Override
	public List<CallOverUtils> getCountUtilsByGradeAndTime(String yearClass,String profession,String classId,
			String stime,String etime){
		return icallOverDao.getCountUtilsByGradeAndTime(yearClass, profession, classId, stime, etime);
	}
	
	@Override
	public List<CallOverUtils> getAllCountUtils()
	{
		return  icallOverDao.getAllCountUtils();
	}
	   
}
