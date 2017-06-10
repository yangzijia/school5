package com.b505.tools;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Grade;
import com.b505.bean.Roll_Alert;
import com.b505.bean.util.GradeUtil;
import com.b505.service.IGradeService;
import com.b505.service.IStudentService;

@Component
public class TryCatchGradeService 
{
	@Autowired
	private IGradeService gradeService;
	@Autowired
	private IStudentService studentService;
	
	
	public  Integer getGradeIDByGradePropertyClassID(String yearClass, String profession, String classID)
	{
		Integer gradeID;
		try
		{
			gradeID = gradeService.getByProperty(yearClass, profession, classID).getGradeId();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return gradeID = 0;
		}
		return gradeID;
	}
	
	
	public boolean saveGradeByBatch(List<Grade> list)
	{
		try
		{
			gradeService.saveGradeByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean updateGradeByBatch(List<Map<String, Object>> list)
	{
		try
		{
			gradeService.updateGradeByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean deleteGradeByBatch(List<Map<String, Object>> list)
	{
		try
		{
			gradeService.deleteGradeByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/*
	 * @方法名：getGradeByGradeID(Integer gradeID)
	 * @功能：通过gradeID得到Grade
	 * @功能说明：通过gradeID得到Grade
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public Grade getGradeByGradeID(Integer gradeID)
	{
		Grade grade;
		try
		{
			grade = gradeService.get(gradeID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return grade = null;
		}
		return grade;
	}
	
	public GradeUtil getGradeUtilByStudentNumber(String studentNumber)
	{
		GradeUtil gradeUtil;
		try
		{
			gradeUtil = studentService.getGradeUtilByStudentNumber(studentNumber);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return gradeUtil = null;
		}
		return gradeUtil;
	}
	
	/*
	 * @方法名：getGradeByGradeNameAndPropertyAndClassID(String gradeName,String profession,String classID)
	 * @功能：通过gradeName，profession，classID得到Grade
	 * @功能说明：通过gradeName，profession，classID得到Grade
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public Grade getGradeByGradeNameAndPropertyAndClassID(String gradeName,String profession,String classID)
	{
		Grade grade;
		try
		{
			grade = gradeService.getByProperty(gradeName, profession, classID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return grade = null;
		}
		return grade;
	}
	
	
	public List<GradeUtil> getGradeUtilsByHeadTeacherNickName(String nickName){
		List <GradeUtil> gradeUtils;
		try {
			gradeUtils=gradeService.getGradeUtilsByHeadTeacherNickName(nickName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return gradeUtils = null;
		}
		return gradeUtils;
	}
	
	
	public boolean updateGradeUtilsByHeadTeacherNickName(List<GradeUtil> list)
	{
		try
		{
			gradeService.updateGradeUtilsByHeadTeacherNickName(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*
	 * @方法名：getGradeIdByGrade(String yearClass,String profession,String classId)
	 * @功能：通过yearClass，profession，classID得到gradeID
	 * @功能说明：通过yearClass，profession，classID得到gradeID
	 * @作者：lyf
	 * @创建时间：2015.10.25
	 */
	public int getGradeIdByGrade(String yearClass,String profession,String classId){
		
		int gradeId;
		try{
			
			gradeId = gradeService.getGradeIdByGrade(yearClass, profession, classId);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return gradeId = 0;
		}
		
		return gradeId;
	}


	
	
	public Grade getClassByGradeID(int gradeID)
	{
		Grade grade;
		try
		{
			grade = gradeService.get(gradeID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return grade = null;
		}
		return grade;
	}


	public GradeUtil getGradeBystudentNumber(String studentNumber)
	{
		// TODO Auto-generated method stub
		GradeUtil gradeUtil;
		try
		{
			gradeUtil=studentService.getGradeBystudentNumber(studentNumber);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			return gradeUtil=null;
		}
		return gradeUtil;
	}


	public Roll_Alert getRoll_AlertBystudentNumber(String studentNumber,
			String studentName)
	{
		Roll_Alert raAlert;
		try
		{
			raAlert=studentService.getRoll_AlertBystudentNumber(studentNumber,studentName);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			return raAlert=null;
		}
		return raAlert;
	}


	
}
