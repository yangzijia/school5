/*
*@包名：com.b505.web        
*@文档名：ShowUserController.java
*@功能：显示用户（学生、辅导员、领导、普通教师），用于修改用户信息      
*@作者：李振强    
*@创建时间：2014.4.20
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.util.AdministratorUtil;
import com.b505.bean.util.LeaderUtil;
import com.b505.bean.util.StudentUtil;
import com.b505.bean.util.TeacherUtil;
import com.b505.json.JsonAnalyze;
import com.b505.service.IAdministratorService;
import com.b505.service.ILeaderService;
import com.b505.service.IStudentService;
import com.b505.service.ITeacherService;
import com.b505.tools.StatusMap;

@Controller
public class ShowUserController 
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private ILeaderService leaderService;
	@Autowired
	private IStudentService studentService;
	@Autowired
	private ITeacherService teacherService;
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private IAdministratorService administratorService;
	
	
	/*
	 * @方法名：showStudent(@RequestBody String requestJsonBody)
	 * @功能：查看学生
	 * @功能说明：查看学生
	 * @作者：李振强
	 * @创建时间：2014.3.15
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/showStudent.html")
	@ResponseBody
	public String showStudent(@RequestBody String requestJsonBody) throws Exception
	{
		//状态返回
		String Fail = statusMap.status("Fail");	
		String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
		Map<String,Object> map = new HashMap<String,Object>();
		//Integer gradeID = 0;
		String yearClass ;
		String profession ;
		String classId ;
		try
		{
			map = jsonAnalyze.json2Map(requestJsonBody);	
			//解析出gradeID
			 yearClass = (String)map.get("yearClass");
			 profession =(String)map.get("profession");
			 classId = (String)map.get("classId");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("ShowUserController_showStudent_Integer.parseInt((String)map.get(\"gradeID\"));出错");
			return cannotAnalyzeData;
		}

		List<StudentUtil> studentList = new ArrayList<StudentUtil>();
		try
		{
			//通过gradeID得到学生列表
			studentList = studentService.getStudentUtilsByGrade(yearClass, profession, classId);
			System.out.println("studentList:"+studentList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("ShowUserController_showStudent_studentService.getStudentByGrade(gradeID);出错");
			return Fail;
		}
		String json = jsonAnalyze.list2Json(studentList);
		System.out.println("json"+json);
		if(json==null||"".equals(json))
		{
			return Fail;
		}
		return json;
	}
	
	
	/*
	 * @方法名：showHeadteacher(@RequestBody String requestJsonBody)
	 * @功能：查看辅导员
	 * @功能说明：查看辅导员
	 * @作者：李振强
	 * @创建时间：2014.3.15
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/showHeadteacher.html")
	@ResponseBody
	public String showHeadteacher(@RequestBody String requestJsonBody) throws Exception
	{
		//状态返回
		String Fail = statusMap.status("Fail");
		String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
		
		Map<String,Object> map = new HashMap<String,Object>();
		String collegeName = new String();
		try
		{
			map = jsonAnalyze.json2Map(requestJsonBody);
			//解析出collegeName
			collegeName = (String)map.get("collegeName");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("ShowUserController_showHeadteacher_ (String)map.get(\"collegeName\");出错");
			return cannotAnalyzeData;
		}
		List<TeacherUtil> teacherList = new ArrayList<TeacherUtil>();
		try
		{
			//通过collegeName得到教师列表
			teacherList = teacherService.getHeadTeacherUtilByRole(collegeName);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("ShowUserController_showHeadteacher_teacherService.getHeadTeacherListByCollege(collegeName);出错");
			return Fail;
		}
		String json = jsonAnalyze.list2Json(teacherList);
		if(json==null||"".equals(json))
		{
			return Fail;
		}
		
		System.out.println("json:"+json);
		return json;
	}
	
	
	/*
	 * @方法名：showLeader()
	 * @功能：查看领导
	 * @功能说明：查看领导
	 * @作者：李振强
	 * @创建时间：2014.3.15
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/showLeader.html")
	@ResponseBody
	public String showLeader() throws Exception
	{
		//状态返回
		String Fail = statusMap.status("Fail");
		
		List<LeaderUtil> leaderList = new ArrayList<LeaderUtil>();
		try
		{   //得到领导列表
			leaderList = leaderService.getLeaderUtils();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("ShowUserController_showLeader_leaderService.getAll();出错");
			return Fail;
		}
		String json = jsonAnalyze.list2Json(leaderList);
		if(json==null||"".equals(json))
		{
			return Fail;
		}
		return json;
	}
	
	
	/*
	 * @方法名：showTeacher(@RequestBody String requestJsonBody)
	 * @功能：查看普通教师
	 * @功能说明：查看普通教师
	 * @作者：李振强
	 * @创建时间：2014.3.15
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/showTeacher.html")
	@ResponseBody
	public String showTeacher() throws Exception
	{
		//状态返回
		String Fail = statusMap.status("Fail");
		List<TeacherUtil> teacherList = new ArrayList<TeacherUtil>();
		try
		{

			teacherList = teacherService.getTeacherUtilByRole();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("ShowUserController_showTeacher_teacherService. getTeacherListByCollege(collegeName);出错");
			return Fail;
		}
		String json = jsonAnalyze.list2Json(teacherList);
		if(json==null||"".equals(json))
		{
			return Fail;
		}
		return json;
	}
	@RequestMapping(value="/showAdministrator.html")
	@ResponseBody
	public String getAdministratorMehod() throws IOException{
		String Fail = statusMap.status("Fail");
		List<AdministratorUtil> list = new ArrayList<AdministratorUtil>();
		try {
			list = administratorService.getAdministratorUtils();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ShowUserController_showTeacher_administratorService.getAdministratorUtils;出错");
			return Fail;
		}
		String json = jsonAnalyze.list2Json(list);
		if(json==null||"".equals(json))
		{
			return Fail;
		}
		return json;
		
	}
	@RequestMapping(value="/showStudentUnRegistered.html")
	@ResponseBody
	public String getStudentUnregisterMethod() throws IOException{
		String Fail = statusMap.status("Fail");
		List<StudentUtil> list = new ArrayList<StudentUtil>();
		try {
			list = studentService.getStudentUtilUnregistered();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ShowUserController_ studentService.getStudentUtilUnregistered;出错");
			return Fail;
		}
		String json = jsonAnalyze.list2Json(list);
		if(json==null||"".equals(json))
		{
			return Fail;
		}
		return json;
		
	}
}

