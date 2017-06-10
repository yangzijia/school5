package com.b505.tools;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Teacher;

import com.b505.service.ITeacherService;

@Component
public class TryCatchTeacherService 
{
	@Autowired
	private ITeacherService teacherService;
	
	public Teacher getTeacherByNickname(String nickName)throws Exception
	{
		Teacher teacher;
		try
		{
			teacher = teacherService.getTeacherByNickname(nickName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			teacher = null;
		}
		return teacher;
	}
	public List<Teacher> getHeadTeacherListByCollege(String collegeName)throws Exception
	{
		List<Teacher> teacherList;
		try
		{
			teacherList = teacherService.getHeadTeacherListByCollege(collegeName);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			teacherList = null;
		}
		return teacherList;
	}
	
	/*
	 * @方法名：update(Teacher teacher)
	 * @功能：update teacher
	 * @功能说明：update teacher
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public boolean update(Teacher teacher)
	{
		try
		{
			teacherService.update(teacher);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateTeacherByBatch(List<Map<String, Object>> list)
	{
		try
		{
			teacherService.updateTeacherByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updatePhoneByNickName(String phone, String nickName)
	{
		boolean status = false;
		try
		{
			status = teacherService.updatePhoneByNickName(phone, nickName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status;
	}
	
	public boolean deleteTeacherByPhone(String phone)
	{
		try
		{
			teacherService.deleteTeacherByPhone(phone);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteTeacherByBatch(List<Integer>list)
	{
		try
		{
			teacherService.deleteTeacherByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public String getTeachernameByNickName(String nickName){
		String teacherName=null;
		try {
			 teacherName=teacherService.getTeacherNameByNickName(nickName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return teacherName;
	}	
	
	//得到辅导员的姓名和手机号
	public Map<String, Object> getTeaNameByTid(int tid){
		
		Map<String, Object> teacherName=null; 
		try {
			 teacherName=teacherService.getTeaNameByTid(tid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return teacherName;
	}
	
	//得到辅导员的姓名
	public String getTeaNameByTid1(int tid)
	{
		// TODO Auto-generated method stub
		
		String teacherName=null;
		try
		{
			teacherName=teacherService.getTeaNameByTid1(tid);
			
		} catch (Exception e)
		{
			// TODO: handle exception
			return null;
		}
		
		return teacherName;
	}
	
}

