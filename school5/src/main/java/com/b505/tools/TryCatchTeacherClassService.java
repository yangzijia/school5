package com.b505.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Grade;
import com.b505.bean.TeacherClass;
import com.b505.bean.util.TeacherId;
import com.b505.service.ITeacherClassService;

@Component
public class TryCatchTeacherClassService 
{
	@Autowired
	private ITeacherClassService teacherClassService;
	
	
	public TeacherClass getTeacherClassByStudentCalss(Grade grade)throws Exception
	{
		TeacherClass teacherClass;
		try
		{
			teacherClass =  teacherClassService.get("studentClass", grade);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			teacherClass = null;
		}
		return teacherClass;
	}
	
	
	public TeacherId getTidByS_C_ID(int gradeId)throws Exception
	{
		TeacherId tId;
		
		try
		{
			tId =  teacherClassService.getTidByS_C_ID(gradeId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tId = null;
		}
		return tId;
	}
}
