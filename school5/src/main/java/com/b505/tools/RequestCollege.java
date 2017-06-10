package com.b505.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.b505.bean.util.GradeUtil;
import com.b505.json.JsonAnalyze;
import com.b505.service.ICollegeService;
import com.b505.service.IGradeService;

@Component
public class RequestCollege 
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private ICollegeService collegeService;
	@Autowired
	private IGradeService gradeService;
	@Autowired
	private ReturnStatus returnStatus;
	
	//得到学院信息，单独的只有学院信息
	public String getCollege()throws IOException
	{
		Map<String,Object> map = new HashMap<String,Object>();
		//学院
		String[] college = collegeService.getAllCollegeName();
		if(college==null
				||"".equals(college)
				||college.length<1)
		{
			System.err.println("得到的学院信息为空");
			return returnStatus.Fail;
		}
		map.put("college", college);
		String json = jsonAnalyze.map2Json(map);
		
		System.out.println("注册时提交到客户端的Json数据为："+json);
		
		if(json==null||"".equals(json))
		{
			return returnStatus.Fail;
		}
		else 
		{
			return json;
		}
	}
	
	//根据学院得到年级、专业、班级信息
	public String getGrade(@RequestBody String requestJsonBody)throws IOException
	{
		Map<String,Object> collegeMap = new HashMap<String,Object>();
		collegeMap = jsonAnalyze.json2Map(requestJsonBody);
		String college = (String)collegeMap.get("collegeName");
		Map<String,Object> map = new HashMap<String,Object>();
		//年级、专业、班级一条龙
		List<GradeUtil> grade = new ArrayList<GradeUtil>();
		grade = gradeService.getGradeByCollege(college);
		System.out.println(grade);
		if(grade==null
				||"".equals(grade)
				||grade.size()<1)
		{
			System.err.println("从数据库得到的年级、专业、班级信息为空");
			return returnStatus.Fail;
		}
		map.put("grade", grade);
		String json = jsonAnalyze.map2Json(map);
		
		System.out.println("注册时提交到客户端的Json数据为："+json);
		
		if(json==null||"".equals(json))
		{
			return returnStatus.Fail;
		}
		else 
		{
			return json;
		}
	}
	
	//得到学院、年级信息
	public String getCollegeGrade()throws IOException
	{
		Map<String,Object> map = new HashMap<String,Object>();
		//学院
		String[] college = collegeService.getAllCollegeName();
		//年级
		String[] grade = gradeService.getyearClass();
		if(college==null
				||"".equals(college)
				||college.length<1
				||grade==null
				||"".equals(grade)
				||grade.length<1)
		{
			System.err.println("得到的学院/年级信息为空");
			return returnStatus.Fail;
		}
		map.put("college", college);
		map.put("grade", grade);
		String json = jsonAnalyze.map2Json(map);
		
		System.out.println("注册时提交到客户端的Json数据为："+json);
		
		if(json==null||"".equals(json))
		{
			return returnStatus.Fail;
		}
		else 
		{
			return json;
		}
	}
	
	//根据学院、年级得到专业信息
	public String getProfession(@RequestBody String requestJsonBody)throws IOException
	{
		//得到客户端发送过来的学院，年级信息
		Map<String,Object> map = new HashMap<String,Object>();
		map = jsonAnalyze.json2Map(requestJsonBody);
		String college = (String)map.get("collegeName");
		String grade = (String)map.get("yearClass");
		if(college==null||"".equals(college)||grade==null||"".equals(grade))
		{
			System.err.println("客户端发送的数据中key值不匹配");
			return returnStatus.Fail;
		}
		//专业
		String[] profession = gradeService.getProfesssionByCY(college,grade);
		if(profession==null||"".equals(profession)||profession.length<1)
		{
			System.err.println("得到的专业信息为空");
			return returnStatus.Fail;
		}
		else 
		{
			Map<String,Object> professionMap = new HashMap<String,Object>();
			professionMap.put("profession", profession);
			String json = jsonAnalyze.map2Json(professionMap);
			System.out.println("注册时提交到客户端的Json数据为："+json);
			return json;
		}
	}
	
	//根据专业得到班级信息 
	public String getClass(@RequestBody String requestJsonBody)throws IOException
	{
		System.out.println("得到的客户端返回的专业信息requestJsonBody为："+requestJsonBody);
		//得到客户端发送过来的专业信息
		Map<String,Object> map = new HashMap<String,Object>();
		map = jsonAnalyze.json2Map(requestJsonBody);
		
		String college = (String)map.get("collegeName");
		String grade = (String)map.get("yearClass");
		String profession = (String)map.get("profession");
		if("".equals(college)
				||college==null
				||"".equals(grade)
				||grade==null
				||"".equals(profession)
				||profession==null)		
		{
			System.err.println("得到的学院/年级/专业信息为空");
			return returnStatus.Fail;
		}
		//班级
		String[] classId = gradeService.getClassIdByCYP(college, grade, profession);
		if(classId==null||"".equals(classId)||classId.length<1)
		{
			System.err.println("得到的班级信息为空");
			return returnStatus.Fail;
		}
		else 
		{
			Map<String,Object> professionMap = new HashMap<String,Object>();
			professionMap.put("classID", classId);
			String json = jsonAnalyze.map2Json(professionMap);
			System.out.println("注册时提交到客户端的Json数据为："+json);
			return json;
		}
	}
}
