package com.b505.dao;

import java.util.List;

import com.b505.bean.CallOver;
import com.b505.bean.util.CallOverUtil;
import com.b505.bean.util.CallOverUtils;

public interface ICallOverDao extends IBaseDao<CallOver>{
	public List<String> getAllDate();
	public List<String>getAllClass();
	public List<CallOver> getStudentListByDC(String date,String className);
	public List<CallOver> getStudentListByTN(String teacherName);
	public List<String>  getClassNameByClassID(Integer gradeId);
	public List<CallOver> getStudentListByDGCC(String date,Integer gradeID,String classTime,String className);
	public void saveCallOverByBatch(List <CallOver> list);
	public boolean updateCallOver(String classTime,String studentNumber,String status,String date);
	public List<CallOverUtil> getCallOverByStudentNumber(String studentNumber);
	public CallOver getStudentCallOver(String date, String classTime, String studentNumber);
	public int deleteCallOver(String date,String classTime,String studentNumber);
	public List<CallOverUtils> getCallOverBycollegeNameAndTime(String collgeName,String STime,String ETime);
	public List<CallOverUtils> getCountUtilsByGradeAndTime(String yearClass,String profession,String classId,
			String stime,String etime);
	
	public List<CallOverUtils> getAllCountUtils();
	
	
}
