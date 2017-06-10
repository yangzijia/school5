/*
*@包名：com.b505.web        
*@文档名：RequestCollegeController.java 
*@功能：返回学院、年级、专业、班级信息  
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

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Grade;
import com.b505.bean.Teacher;
import com.b505.bean.LoginUser;
import com.b505.bean.util.GradeHBUtil;
import com.b505.bean.util.GradeUtil;
import com.b505.json.JsonAnalyze;
import com.b505.service.ICollegeService;
import com.b505.service.IGradeService;
import com.b505.service.ITeacherService;
import com.b505.service.ILoginUserService;
import com.b505.tools.ReturnStatus;
import com.b505.tools.SessionGet;
import com.b505.tools.StatusMap;
import com.b505.tools.TryCatchGradeService;

@Controller
public class RequestCollegeController 
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private ICollegeService collegeService;
	@Autowired
	private ILoginUserService userService;
	@Autowired
	private ITeacherService teacherService;
	@Autowired
	private IGradeService gradeService;
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private TryCatchGradeService tryCatchGradeService;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private SessionGet sessionGet;	
	/*
	 * @方法名：getTeacherCollege()
	 * @功能：得到学院信息
	 * @功能说明：注册时，给客户端提交学院信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value = "/requestCollege.html")
	@ResponseBody
	public String getTeacherCollege()throws IOException
	{
		//状态返回
		String Fail = statusMap.status("Fail");
		//返回学院信息的Map
		Map<String,Object> map = new HashMap<String,Object>();
		//学院
		String[] college = null;
		try
		{
			//得到所有的学院信息
			college = collegeService.getAllCollegeName();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("RequestCollegeController_getTeacherCollege_collegeService.getAllCollegeName();出错");
			return Fail;
		}
		if(college==null||"".equals(college)||college.length<1)
		{
			System.err.println("得到的学院信息为空");
			return Fail;
		}
		map.put("college", college);
		String json = jsonAnalyze.map2Json(map);
		if(json==null||"".equals(json))
		{
			return Fail;
		}
		else 
		{
			return json;
		}
	}	
	
	
	/*
	 * @方法名：getTeacherGrade(@RequestBody String requestJsonBody)
	 * @功能：得到年级、专业、班级信息
	 * @功能说明：注册时，给客户端提交年级、专业、班级信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value = "/requestGrade.html")
	@ResponseBody
	public String getTeacherGrade(@RequestBody String requestJsonBody)throws IOException
	{
		//状态返回
		String Fail = statusMap.status("Fail");
		String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
		
		Map<String,Object> collegeMap = new HashMap<String,Object>();
		collegeMap = jsonAnalyze.json2Map(requestJsonBody);
		String collegeName = (String)collegeMap.get("collegeName");
		if(collegeName==null||"".equals(collegeName))
		{
			return cannotAnalyzeData;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		//年级、专业、班级一条龙
		List<GradeUtil> grade = new ArrayList<GradeUtil>();
		try
		{
			grade = gradeService.getGradeByCollege(collegeName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("RequestCollegeController_getTeacherGrade_gradeService.getGradeByCollege(collegeName);出错");
			return Fail;
		}
		if(grade==null||"".equals(grade)||grade.size()<1)
		{
			System.err.println("从数据库得到的年级、专业、班级信息为空");
			return Fail;
		}
		map.put("grade", grade);
		String json = jsonAnalyze.map2Json(map);
		if(json==null||"".equals(json))
		{
			return Fail;
		}
		else 
		{
			return json;
		}
	}
	
	
	/*
	 * @方法名：getGPC(@RequestBody String requestJsonBody)
	 * @功能：根据辅导员的昵称得到年级、专业、班级信息
	 * @功能说明：根据辅导员的昵称给客户端提交年级、专业、班级信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.3
	 */
	@RequestMapping(value = "/getGPC.html")
	@ResponseBody
	public String getGPC(@RequestBody String requestJsonBody) throws Exception
	{
		//状态返回
		String getGPCFail = statusMap.status("getGPCFail");
		String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
		System.out.println("requestJsonBody为: " + requestJsonBody);
		Map<String,Object> collegeMap = new HashMap<String,Object>();
		collegeMap = jsonAnalyze.json2Map(requestJsonBody);
		String headTeacherNickame = (String)collegeMap.get("headTeacherNickname");
		if(headTeacherNickame==null||"".equals(headTeacherNickame))
		{
			return cannotAnalyzeData;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		Teacher headTeacher = new Teacher();
		LoginUser user = new LoginUser();
		List<Grade> gradeList = new ArrayList<Grade>();
		try
		{
			//通过昵称得到User实体
			user = userService.get("nickName",headTeacherNickame);
			//通过user得到Teacher实体
			headTeacher = teacherService.get("user", user);
			//通过headTeacher得到gradeList
			gradeList = headTeacher.getTeacherClass();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("RequestCollegeController_getGPC_userService.get(\"nickName\",headTeacherNickame);出错");
			return getGPCFail;
		}
		if(gradeList==null||"".equals(gradeList)||gradeList.size()<1)
		{
			System.err.println("根据辅导员昵称得到的年级、专业、班级信息为空");
			return getGPCFail;
		}
		//用来装载所有班级的信息
		Map<String,Object>allGradeMap = new HashMap<String,Object>();
		Grade grade = new Grade();
		for(int i = 0; i < gradeList.size(); i++)
		{
			//用来装载一个班级信息
			Map<String,Object>gradeMap = new HashMap<String,Object>();
			grade = gradeList.get(i);
			gradeMap.put("gradeID",grade.getGradeId());
			gradeMap.put("grade",grade.getYearClass());
			gradeMap.put("profession",grade.getProfession());
			gradeMap.put("classID",grade.getClassId());
			allGradeMap.put(""+i, gradeMap);
		}
		map.put("grade", allGradeMap);
		String json = jsonAnalyze.map2Json(map);
		if(json==null||"".equals(json))
		{
			return getGPCFail;
		}
		return json;
	}
	
	
	/*
	 * @方法名：getStudentCollege()
	 * @功能：得到学院、年级信息
	 * @功能说明：注册时，给客户端提交学院、年级信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value = "/requestCollGra.html")
	@ResponseBody
	public String getStudentCollege()throws IOException
	{
		//状态返回
		String Fail = statusMap.status("Fail");
		
		Map<String,Object> map = new HashMap<String,Object>();
		String[] college = null;
		String[] grade = null;
		try
		{
			//学院
			college = collegeService.getAllCollegeName();
			
			//年级
			grade = gradeService.getyearClass();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("RequestCollegeController_getStudentCollege_gradeService.getyearClass();出错");
			return Fail;
		}
		if(college==null||"".equals(college)||college.length<1||grade==null||"".equals(grade)||grade.length<1)
		{
			System.err.println("得到的学院/年级信息为空");
			return Fail;
		}
		map.put("college", college);
		map.put("grade", grade);
		String json = jsonAnalyze.map2Json(map);
		System.out.println("json---->"+json);
		if(json==null||"".equals(json))
		{
			return Fail;
		}
		else 
		{
			return json;
		}
	}
	
	
	/*
	 * @方法名：getStudentProfession(@RequestBody String requestJsonBody)
	 * @功能：得到专业信息
	 * @功能说明：注册时，给客户端提交专业信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value = "/requestProfession.html")
	@ResponseBody
	public String getStudentProfession(@RequestBody String requestJsonBody)throws IOException
	{
		//状态返回
		String Fail = statusMap.status("Fail");
		String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
		
		System.out.println("得到的客户端返回的学院、年级信息requestJsonBody为："+requestJsonBody);
		//得到客户端发送过来的学院，年级信息
		Map<String,Object> map = new HashMap<String,Object>();
		map = jsonAnalyze.json2Map(requestJsonBody);
		String college = (String)map.get("collegeName");
		String grade = (String)map.get("yearClass");
		
		if(college==null||"".equals(college)||grade==null||"".equals(grade))
		{
			return cannotAnalyzeData;
		}
		//专业
		String[] profession = gradeService.getProfesssionByCY(college,grade);
		if(profession==null||"".equals(profession)||profession.length<1)
		{
			System.err.println("得到的专业信息为空");
			return Fail;
		}
		Map<String,Object> professionMap = new HashMap<String,Object>();
		professionMap.put("profession", profession);
		String json = jsonAnalyze.map2Json(professionMap);
		return json;
	}
	
	
	/*
	 * @方法名：getStudentClass(@RequestBody String requestJsonBody)
	 * @功能：得到班级信息
	 * @功能说明：注册时，给客户端提交班级信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value = "/requestClass.html")
	@ResponseBody
	public String getStudentClass(@RequestBody String requestJsonBody)throws IOException
	{
		//状态返回
		String Fail = statusMap.status("Fail");
		String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
		
		//得到客户端发送过来的专业信息
		Map<String,Object> map = new HashMap<String,Object>();
		map = jsonAnalyze.json2Map(requestJsonBody);
		
		String college = (String)map.get("collegeName");
		String grade = (String)map.get("yearClass");
		String profession = (String)map.get("profession");
		if("".equals(college)||college==null||"".equals(grade)||grade==null||"".equals(profession)||profession==null)		
		{
			return cannotAnalyzeData;
		}
		//班级
		String[] classId = gradeService.getClassIdByCYP(college, grade, profession);
		if(classId==null||"".equals(classId)||classId.length<1)
		{
			System.err.println("得到的班级信息为空");
			return Fail;
		}
		else 
		{
			Map<String,Object> professionMap = new HashMap<String,Object>();
			professionMap.put("classID", classId);
			String json = jsonAnalyze.map2Json(professionMap);
			return json;
		}
	}
	
	
	
	@RequestMapping(value="/getGradeUtilByHeadTeachernickName.html")
	@ResponseBody
	public String getGradeUtils(HttpServletRequest request) throws Exception{
		/*HttpSession hp = request.getSession();
		String nickNameString=(String)hp.getAttribute("userName");*/
		String nickNameString = sessionGet.getUserInfo().getUsername();
		List<GradeUtil> gradeUtils = tryCatchGradeService.getGradeUtilsByHeadTeacherNickName(nickNameString);
		//System.out.println("gradeUtils---->"+gradeUtils);
		List<GradeHBUtil> gblist = new ArrayList<GradeHBUtil>();
		String gradeUtilString = null;
		if(gradeUtils==null){
			return returnStatus.Fail;
		}else{
			for(int i=0;i<gradeUtils.size();i++){
				GradeHBUtil gb = new GradeHBUtil();
				gb.setGrade(gradeUtils.get(i).getYearClass()+"级"+gradeUtils.get(i).getProfession()+gradeUtils.get(i).getClassId()+"班");
				gb.setId(gradeUtils.get(i).getGradeId());
				gblist.add(gb);
				//System.out.println("gblist----->"+gblist);
			}
			gradeUtilString = jsonAnalyze.list2Json(gblist);
		}
		return gradeUtilString;
	}
	
	
}

