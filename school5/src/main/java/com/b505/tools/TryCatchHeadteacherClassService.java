package com.b505.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Grade;
import com.b505.bean.HeadteacherClass;
import com.b505.service.IHeadteacherClassService;

@Component
public class TryCatchHeadteacherClassService {

	@Autowired
	private IHeadteacherClassService headteacherClassService;
	
	public HeadteacherClass getHeadteacherClassByStudentCalss(Grade grade)throws Exception
	{
		HeadteacherClass hteacherClass;
		try
		{
			hteacherClass =  headteacherClassService.get("studentClass", grade);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			hteacherClass = null;
		}
		return hteacherClass;
	}
}
