package com.b505.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Dormcallover;
import com.b505.service.IDormcalloverService;

@Component
public class TryCatchDormcalloverService {
	
	@Autowired
	private IDormcalloverService iDormcalloverService;

	/**
	 * @author lyf
	 * @time 2015.11.22
	 * @function 保存班委点名信息
	 * @param list
	 * @return
	 */
	public boolean saveDormcalloverByBatch(List<Dormcallover> list)
	{
		boolean status = false;
		try
		{
			iDormcalloverService.saveDormcalloverByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 服务端辅导员查询宿舍考勤信息，按班级和日期
	 * @param yearClass
	 * @param profession
	 * @param classId
	 * @param date
	 * @return
	 */
	public List<Dormcallover> getDormcalloversByGradeAndDate(int gradeId,String date){
		List<Dormcallover> list;
		try{
			list = iDormcalloverService.getDormcalloversByGradeAndDate(gradeId, date);
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}
		return list;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 服务端辅导员查询宿舍考勤信息,按班级
	 */
	public List<Dormcallover> getDormcalloversByGrade(int gradeId){
		List<Dormcallover> list;
		try{
			list = iDormcalloverService.getDormcalloversByGrade(gradeId);
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}
		return list;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-24
	 * @function 辅导员批量删除宿舍考勤信息
	 */
	public boolean delDormcalloverByBatch(List<Integer> list){
		boolean status = false;
		try
		{
			iDormcalloverService.delDormcalloverByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
}
