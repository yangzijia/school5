package com.b505.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.CallOver;
import com.b505.bean.util.CallOverUtil;
import com.b505.bean.util.CallOverUtils;
import com.b505.service.ICallOverService;

@Component
public class TryCatchCallOverService
{
	@Autowired
	private ICallOverService callOverService;
	
	
	/*
	 * @方法名：getCourseNameListByGradeID(Integer gradeID)
	 * @功能：根据gradeID得到课程名
	 * @功能说明：根据gradeID得到课程名
	 * @作者：李振强
	 * @创建时间：2014.5.19
	 * @修改时间：2014.5.19
	 */
	public List<String> getCourseNameListByGradeID(Integer gradeID)
	{
		List<String> courseList;
		try
		{
			courseList = callOverService.getClassNameByClassID(gradeID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return courseList = null;
		}
		return courseList;
	}
	
	
	/*
	 * @方法名：getCallOverListByDateGradeIDClassTimeClassName(String date,Integer gradeID,String classTime,String className)
	 * @功能：根据日期、年级、节数得到一个班级的考勤表
	 * @功能说明：根据日期、年级、节数得到一个班级的考勤表
	 * @作者：李振强
	 * @创建时间：2014.5.19
	 * @修改时间：2014.5.19
	 */
	public List<CallOver> getCallOverListByDateGradeIDClassTimeClassName(String date,Integer gradeID,String classTime,String className)
	{
		List<CallOver> calloverList;
		try
		{
			calloverList = callOverService.getStudentListByDGCC(date, gradeID, classTime, className);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return calloverList = null;
		}
		return calloverList;
	}
	
	
	/*
	 * @方法名：getStudentCallOver(String date,Integer gradeID,String classTime,String className)
	 * @功能：根据节数、日期、学号得到一个学生的考勤表
	 * @功能说明：根据节数、日期、学号得到一个学生的考勤表
	 * @作者：李振强
	 * @创建时间：2014.06.01
	 * @修改时间：2014.06.01
	 */
	public CallOver getStudentCallOver(String date, String classTime, String studentNumber)
	{
		CallOver callOver;
		try
		{
			callOver = callOverService.getStudentCallOver(date, classTime, studentNumber);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return callOver = null;
		}
		return callOver;
	}
	
	
	public List<CallOverUtil> getCallOverByStudentNumber(String studentNumber)
	{
		List<CallOverUtil> callOverUtilList;
		try
		{
			callOverUtilList = callOverService.getCallOverByStudentNumber(studentNumber);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return callOverUtilList = null;
		}
		return callOverUtilList;
	}
	
	
	public boolean saveCallOverByBatch(List<CallOver> list)
	{
		boolean status = false;
		try
		{
			callOverService.saveCallOverByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
	
	public boolean updateCallOver(String classTime, String studentNumber, String status, String date) throws Exception
	{
		boolean returnStatus;
		try
		{
			returnStatus = callOverService.updateCallOver(classTime, studentNumber, status, date);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return returnStatus = false;
		}
		return returnStatus;
	}
	
	public boolean deleteCallOver(String date, String classTime,
			String studentNumber){
		boolean returnstatus=false;
		try {
			int b = callOverService.deleteCallOver(date, classTime, studentNumber);
			if(b==1){
				 returnstatus = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return returnstatus = false;
		}
		return returnstatus;
	}
	
	/**
	 * @author lyf
	 * @time 2015.11.19
	 * @功能   领导按日期查询考勤
	 */
	public List<CallOverUtils> getCallOverBycollegeNameAndTime(String collgeName,String STime,String ETime){
		
		List<CallOverUtils> list;
		try{			
			list = callOverService.getCallOverBycollegeNameAndTime(collgeName, STime, ETime);
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}		
		return list;
	}
	
	/**
	 * @author lyf
	 * @time 2015.11.21
	 * @功能   辅导员按日期查询考勤
	 */
	public List<CallOverUtils> getCountUtilsByGradeAndTime(String yearClass,String profession,String classId,
			String stime,String etime){	
		List<CallOverUtils> list;
		try{			
			list = callOverService.getCountUtilsByGradeAndTime(yearClass, profession, classId, stime, etime);
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}		
		return list;
		
	}

    /**
     * @author JSY
     * @function 领导登录后，页面初始化时加载所有考勤数据
     * @time 2016.3.31
     */
	public List<CallOverUtils> getAllCountUtils()
	{
		// TODO Auto-generated method stub
		List<CallOverUtils> list;
		try
		{
			list =callOverService.getAllCountUtils();
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			list = null;
		}
		return list;
	}
}


