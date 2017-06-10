package com.b505.tools;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.College;
import com.b505.service.ICollegeService;

@Component
public class TryCatchCollegeService
{
	@Autowired
	private ICollegeService  collegeService;
	
	
	public College getCollege(String propertyName1, Object value1)
	{
		College college;
		try
		{
			college = collegeService.get(propertyName1, value1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return college = null;
		}
		return college;
	}
	
	
	public boolean saveCollegeByBatch(List<College> collegeList)
	{
		boolean status = false;
		try
		{
			collegeService.saveCollegeByBatch(collegeList);
			status = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	
	public boolean updateCollegeByBatch(List<Map<String, Object>> list)
	{
		boolean status = false;
		try
		{
			collegeService.updateCollegeByBatch(list);
			status = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	
//	public boolean deleteCollegeByCollegeName(Integer collegeID, String collegeName)
//	{
//		boolean status = false;
//		try
//		{
//			status = collegeService.deleteCollegeByCollegeName(collegeID, collegeName);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			status = false;
//		}
//		return status;
//	}
	
	public boolean deleteCollegeByBatch(List<Integer> list)
	{
		boolean status = false;
		try
		{
			collegeService.deleteCollegeByBatch(list);
			status = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	/**
	 * @功能：学生求救时，用于获取领导所属学院
	 * @author lyf
	 * @param gradeId
	 * @return
	 */
	public int getCollegeIdByGradeId(int gradeId){
		
		int collegeid;
		try{			
			collegeid = collegeService.getCollegeIdByGradeId(gradeId);
		}catch(Exception e){
			e.printStackTrace();
			collegeid = 0;
		}
		
		return collegeid;
	}
}

