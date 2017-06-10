/*
*@包名：com.b505.web        
*@文档名：RegisterController.java 
*@功能：用户注册   
*@作者：李振强        
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.College;
import com.b505.bean.Grade;
import com.b505.bean.RoleName;
import com.b505.bean.Student;
import com.b505.bean.Teacher;
import com.b505.bean.LoginUser;
import com.b505.json.UserJsonAnalyze;
import com.b505.service.ICollegeService;
import com.b505.service.IGradeService;
import com.b505.service.IStudentService;
import com.b505.service.ITeacherService;
import com.b505.service.ILoginUserService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchCollegeService;
import com.b505.tools.TryCatchGradeService;
import com.b505.tools.TryCatchStudentService;
import com.b505.tools.TryCatchUserService;

@Controller
public class RegisterController 
{
	@Autowired
	private ILoginUserService userService;
	@Autowired
	private ITeacherService teacherService;
	@Autowired
	private IStudentService studentService;
	@Autowired
	private ICollegeService collegeService;
	@Autowired
	private IGradeService gradeService;
	@Autowired
	private UserJsonAnalyze userJsonAnalyze;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private TryCatchCollegeService tryCatchCollegeService;
	@Autowired
	private TryCatchGradeService tryCatchGradeService;
	@Autowired
	private ReturnObjectByAttribute returnObjectByAttribute;
	
	/*
	 * @方法名：studentRegister(@RequestBody String requestJsonBody)
	 * @功能：保存学生的注册信息
	 * @功能说明：学生注册时将其所填信息插入数据库中
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/registerStudent.html")
	@ResponseBody
	public String studentRegister(@RequestBody String requestJsonBody) throws Exception
	{
		
		Map<String,Object> studentMap = userJsonAnalyze.studentJsonAnalyze(requestJsonBody);	
		LoginUser user = (LoginUser)studentMap.get("user");
		
		List<RoleName>list1=new ArrayList<>();
		RoleName rnName=new RoleName();
		rnName.setId(4);
		list1.add(rnName);
		user.setRoles(list1);
		
		Student student = (Student)studentMap.get("student");
		
		//查看用户昵称是否重复
		if(userService.get(user.getNickName())!=null)
		{
			return returnStatus.UserNicknameRepeat;
		}
		//用户输入的学号
		String studentID = student.getId();
		if(dataProcess.dataIsNull(studentID))
		{
			return returnStatus.Fail;
		}
		//判断该学生是否注册过
		Student regexStudent = tryCatchStudentService.getStudentByStudentID(studentID);
		if(!dataProcess.dataIsNull(regexStudent))
		{
			return returnStatus.UserNicknameRepeat;
		}
		//用户输入的名字
//		System.out.println("执行了吗");
//		String inStudentName = student.getStudentName();
		//数据库中的名字
//		String regexStudentName = regexStudent.getStudentName();
//		if(!inStudentName.equals(regexStudentName))
//		{
//			return returnStatus.UserNameError;
//		}
		//根据"collegeName"键来拿到值
		College college =(College)studentMap.get("collegeName");
		System.out.println("college--->"+college);
		//根据"grade"键来拿到值
		Grade grade = (Grade)studentMap.get("grade");
		System.out.println("grade---->"+grade);
		Grade grade2 = tryCatchGradeService.getGradeByGradeNameAndPropertyAndClassID(grade.getYearClass(), grade.getProfession(), grade.getClassId());
		System.out.println("grade2----->"+grade2);
		if(grade2==null||dataProcess.dataIsNull(grade2))
		{
			return returnStatus.Fail;
		}
		College college2 = tryCatchCollegeService.getCollege("collegeName", college.getCollegeName());
		System.out.println("college2---->"+college2);
		if(college2==null||dataProcess.dataIsNull(college2))
		{
			return returnStatus.Fail;
		}
		System.out.println(college2.getCollegeid());
		student.setCollege(college2);
		student.setStudentClass(grade2);
		
		
//		regexStudent.setCollege(student.getCollege());
//		regexStudent.setId(student.getId());
//		System.out.println(student.getLonglat());
//		regexStudent.setLonglat(student.getLonglat());
//		regexStudent.setStudentCardId(student.getStudentCardId());
//		regexStudent.setStudentClass(student.getStudentClass());
//		regexStudent.setStudentName(student.getStudentName());
//		regexStudent.setStudentPhone(student.getStudentPhone());
//		regexStudent.setUser(student.getUser());
		if(!tryCatchUserService.saveUser(user))
		{
			return returnStatus.Fail;
		}
		if(!tryCatchStudentService.saveStudent(student))
		{
			tryCatchUserService.deletedUser(user);
			return returnStatus.Fail;
		}
		//检测用户是否注册成功
		int registerStatus = studentService.hasRegisterSuccess(student.getId(),user.getNickName());
		if(registerStatus==1)
		{
			return returnStatus.Success;
		}
		return returnStatus.Fail;
	}
	
	
	/*
	 * @方法名：headteacherRegister(@RequestBody String requestJsonBody)
	 * @功能：保存辅导员的注册信息
	 * @功能说明：辅导员注册时将其所填信息插入数据库中
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/registerHeadteacher.html")
	@ResponseBody
	public String headteacherRegister(@RequestBody String requestJsonBody) throws Exception
	{
		System.out.println("requestJsonBody"+requestJsonBody);
		Map<String,Object> map = new HashMap<String,Object>();
		if(requestJsonBody==null||"".equals(requestJsonBody)||requestJsonBody.length()<0)
		{
			return returnStatus.CannotAnalyzeData;
		}
		map = userJsonAnalyze.headteacherJsonAnalyze(requestJsonBody);
		if(dataProcess.dataIsNull(map))
		{
			return returnStatus.CannotAnalyzeData;
		}
		LoginUser user = (LoginUser)map.get("user");
		Teacher headTeacher = (Teacher)map.get("headteacher");
		if(user==null||headTeacher==null)
		{
			System.err.println("RegisterController_headteacherRegister_(User)map.get(\"user\");出错");
			return returnStatus.CannotAnalyzeData;
		}
		if(userService.get(user.getNickName())!=null||teacherService.get("teacherCardId", headTeacher.getTeacherCardId())!=null)
		{
			return returnStatus.UserNicknameRepeat;
		}
		College college = (College)map.get("college");
		College college2;
		try
		{
			//通过学院名字得到college实体
			college2 = collegeService.get("collegeName", college.getCollegeName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("RegisterController_headteacherRegister_collegeService.get(\"collegeName\", college.getCollegeName());出错");
			return returnStatus.CannotAnalyzeData;
		}
		headTeacher.setCollege(college2);
		try
		{
			//用户信息插入user表中
			userService.save(user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("RegisterController_headteacherRegister_userService.save(user);出错");
			return returnStatus.Fail;
		}
		try
		{
			//教师信息插入teacher表中
			teacherService.save(headTeacher);
		}
		catch(Exception e)
		{
			//删除用户在user表中的信息
			userService.delete(user);
			e.printStackTrace();
			System.err.println("RegisterController_headteacherRegister_teacherService.save(headTeacher);出错");
			return returnStatus.Fail;
		}
		int registerStatus = teacherService.hasRegisterSuccess(headTeacher.getTeacherCardId(),user.getNickName(),headTeacher.getCollege());
		if(registerStatus==1)
		{
			return returnStatus.Success;
		}
		else if(registerStatus==2)
		{
			return returnStatus.Fail;
		}
		else
		{
			return returnStatus.Auditing;
		}
	}
	
	
	/*
	 * @方法名：teacherRegister(@RequestBody String requestJsonBody)
	 * @功能：保存普通教师的注册信息
	 * @功能说明：普通教师注册时将其所填信息插入数据库中
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/registerTeacher.html")
	@ResponseBody
	public String teacherRegister(@RequestBody String requestJsonBody) throws Exception
	{
		if(requestJsonBody==null||"".equals(requestJsonBody)||requestJsonBody.length()<0)
		{
			return returnStatus.CannotAnalyzeData;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		try
		{
			map = userJsonAnalyze.teacherJsonAnalyze(requestJsonBody);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("RegisterController_teacherRegister_userJsonAnalyze.teacherJsonAnalyze(requestJsonBody);出错");
			return returnStatus.CannotAnalyzeData;
		}
		if(map == null)
		{
			System.err.println("RegisterController_teacherRegister_if(map == null)");
			return returnStatus.CannotAnalyzeData;
		}
		Teacher teacher = (Teacher)map.get("teacher");
		LoginUser  user = (LoginUser)map.get("user");
		if(user==null||teacher==null)
		{
			System.err.println("RegisterController_teacherRegister_(Teacher)map.get(\"teacher\");出错");
			return returnStatus.CannotAnalyzeData;
		}
		if(userService.get(user.getNickName())!=null||"".equals(userService.get(user.getNickName()))
				||teacherService.get("teacherCardId", teacher.getTeacherCardId())!=null)
		{
			return returnStatus.UserNicknameRepeat;
		}
		try
		{
			//用户信息插入user表中
			userService.save(user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("RegisterController_teacherRegister_userService.save(user);出错");
			return returnStatus.Fail;
		}
		try
		{
			//教师信息插入teacher表中
			teacherService.save(teacher);		
		}
		catch(Exception e)
		{
			//删除用户在user表中的信息
			userService.delete(user);
			e.printStackTrace();
			System.err.println("RegisterController_teacherRegister_teacherService.save(teacher);	出错");
			return returnStatus.Fail;
		}
        int registerStatus = teacherService.hasRegisterSuccess(teacher.getTeacherCardId(),user.getNickName());
        if(registerStatus==1)
		{
			return returnStatus.Success;
		}
		else if(registerStatus==0)
		{
			return returnStatus.Fail;
		}
		else
		{
			return returnStatus.Auditing;
		}
	}	
}

