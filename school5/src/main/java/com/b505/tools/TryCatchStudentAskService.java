package com.b505.tools;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.StudentAsk;
import com.b505.bean.Type;
import com.b505.bean.util.GradeId;
import com.b505.service.IStudentAskService;

@Component
public class TryCatchStudentAskService {

	@Autowired
	private IStudentAskService studentAskService;
	
	/*
	 * @方法名：saveStudentAsk(StudentAsk sAsk)
	 * @功能：保存请假信息
	 * @功能说明：保存学生请假时的请假信息
	 * @创建时间：2015.06.08
	 */
	public boolean saveStudentAsk(StudentAsk sAsk)throws Exception
	{
		try
		{
			studentAskService.save(sAsk);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public int hasAskSuccess(String nickName,String teacherName)
	{
		int askSuccess;
		try
		{
			askSuccess = studentAskService.hasAskSuccess(nickName,teacherName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return askSuccess = 0;
		}
		return askSuccess;
	}

	/**
	 * @功能：辅导员按班级和请假类型查看请假信息
	 * @param gradeId
	 * @param status
	 * @return 学生请假信息列表
	 */
	public List<StudentAsk> getSAskByGradeIdandStatus(int gradeId,String status){
		
		List<StudentAsk> sList;
		try{
			sList = studentAskService.getSAskByGradeIdandStatus(gradeId, status);
			
		}catch(Exception e){
			e.printStackTrace();
			sList = null;
		}
		
		return sList;
	}
	
	public GradeId getClassIdByNickname(String nickName)
	{
		GradeId gid;
		try
		{
			gid = studentAskService.getClassIdByNickname(nickName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			gid = null;
		}
		return gid;
	}
	
	public List<Type> getType(){
		
		List<Type> typeList;
		
		try
		{
			typeList = studentAskService.getType();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			typeList = null;
		}
		return typeList;
		
	}
	
	public boolean deleteAskByBatch(List<Integer> list)
	{
		try
		{
			studentAskService.deleteAskByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateAskByBatch(List<Map<String, Object>> list)
	{
		boolean b = false;
		try
		{
			studentAskService.updateAskByBatch(list);
			b = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	
	public StudentAsk getSAskBynickNameAndType(String nickName,String type){
		StudentAsk sAsk;
		try{
			sAsk = studentAskService.getSAskBynickNameAndType(nickName, type);
		}catch(Exception e){
			e.printStackTrace();
			sAsk = null;
		}
		return sAsk;
	}
}
