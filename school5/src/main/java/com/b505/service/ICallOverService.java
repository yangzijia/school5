package com.b505.service;

import java.util.List;

import com.b505.bean.CallOver;
import com.b505.bean.util.CallOverUtil;
import com.b505.bean.util.CallOverUtils;
import com.b505.dao.IBaseDao;

public interface ICallOverService extends IBaseService<CallOver >{
	public void setIBaseDao(IBaseDao<CallOver> icallOverDao);
	public List<String> getAllDate();
	public List<String>getAllClass();
	public List<CallOver> getStudentListByDC(String date,String className);
	public List<CallOver> getStudentListByTN(String teacherName);
	public int deleteCallOverByID(CallOver callOver);
	public List<String> getClassNameByClassID(Integer gradeId) ;
	public List<CallOver> getStudentListByDGCC(String date, Integer gradeID,
			String classTime, String className);
	public void saveCallOverByBatch(List<CallOver> list);
	public boolean updateCallOver( String classTime,
			String studentNumber, String status,String  date);
	public List<CallOverUtil> getCallOverByStudentNumber(String studentNumber);
	public CallOver getStudentCallOver(String date, String classTime,
			String studentNumber);
	public int deleteCallOver(String date, String classTime,
			String studentNumber);
	public List<CallOverUtils> getCallOverBycollegeNameAndTime(String collgeName,String STime,String ETime);
	public List<CallOverUtils> getCountUtilsByGradeAndTime(String yearClass,String profession,String classId,
			String stime,String etime);
	public List<CallOverUtils> getAllCountUtils();
}
