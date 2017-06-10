package com.b505.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.College;
import com.b505.bean.Grade;
import com.b505.bean.Login;
import com.b505.bean.Student;
import com.b505.bean.StudentAsk;
import com.b505.bean.Teacher;
import com.b505.bean.LoginUser;
import com.b505.service.IGradeService;
import com.b505.service.ILoginUserService;
import com.b505.service.IStudentService;
import com.b505.service.ITeacherService;
import com.b505.tools.DigestUtils;
import com.b505.tools.Regex;


@Component
public class UserJsonAnalyze 
{
	private JsonAnalyze jsonAnalyze = new JsonAnalyze();
	@Autowired
	private IGradeService gradeServiceImpl;
	@Autowired
	private Regex regex;
	@Autowired
	//private SSHA ssha;
	private DigestUtils du;
	@Autowired
	private ILoginUserService loginUserService;
	@Autowired
	private ITeacherService teacherService;
	@Autowired
	private DigestUtils digestUtils;
	@Autowired
	private IStudentService studentService;
	
	
	/*
	 * @方法名：newloginJsonAnalyze(String requestJsonBody)
	 * @功能：转换登录页面JSON数据,加上验证码
	 * @功能说明：转换登录页面JSON数据
	 * @作者：lyf
	 * @创建时间：2015.11.22
	 */
	public Login newloginJsonAnalyze(String requestJsonBody)throws  Exception
	{
		//状态标志位
		boolean status = false;
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1 = jsonAnalyze.json2Map(requestJsonBody);
		Login user = new Login();		
		Set<String> key = map1.keySet();
		Iterator<String> iter = key.iterator();
		while(iter.hasNext())
		{
			String str = iter.next();
			//用户昵称
			if("userName".equals(str))
			{
                if(regex.nicknameRegex(str))
                {
    				user.setUserName((String)map1.get(str));
                }
                else
                {
                	status = true;
                }
			}
			//用户密码
			else if("passWord".equals(str)) 
			{
				String pd = digestUtils.digest((String)map1.get(str));
				user.setPassWord(pd);
			}
			//验证码
			else if("verifyCode".equals(str))
			{
				user.setVerifyCode((String)map1.get(str));
			}//Clientid传送
			else if("clientid".equals(str)){
				//客户端或浏览器传来的clientid
				String cid = (String)map1.get(str);
System.out.println("clientid==="+cid);
				//判断是否为空：若为空则为从浏览器传来的值，若不为空则为从安卓或ios客户端传来的值
				if(cid!=null){
					//为从安卓或ios客户端传来的值
					user.setClientid(cid);
				}else{
					//为从浏览器传来的值
					user.setClientid(null);
				}
			}
			else
			{
				System.err.println("客户端登录页面JSON数据的数据格式不正确！！");
				status = true;
				break;
			}
		}
		
		if(status)
		{
			user = null;
		}
		return user;
	}
	
	
	/*
	 * @方法名：loginJsonAnalyze(String requestJsonBody)
	 * @功能：转换登录页面JSON数据
	 * @功能说明：转换登录页面JSON数据
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.28
	 */
	public LoginUser loginJsonAnalyze(String requestJsonBody)throws  Exception
	{
		//状态标志位
		boolean status = false;
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1 = jsonAnalyze.json2Map(requestJsonBody);
		LoginUser user = new LoginUser();		
		Set<String> key = map1.keySet();
		Iterator<String> iter = key.iterator();
		while(iter.hasNext())
		{
			String str = iter.next();
			//用户昵称
			if("userName".equals(str))
			{
                if(regex.nicknameRegex(str))
                {
    				user.setNickName((String)map1.get(str));
                }
                else
                {
                	status = true;
                }
			}
			//用户密码
			else if("password".equals(str))  //2015,11,02去掉验证码时修改. 11.22改回
			{
				//String pd = ssha.digest((String)map1.get(str)); //同上改
				String pd = digestUtils.digest((String)map1.get(str));
				user.setPassword(pd);
			}
			//用户角色
			/*else if("role".equals(str))
			{
				user.setRole((String)map1.get(str));
			}*/
			else
			{
				System.err.println("客户端登录页面JSON数据的数据格式不正确！！");
				status = true;
				break;
			}
		}
		if(status)
		{
			user = null;
		}
		return user;
	}	

	
	/*
	 * @方法名：studentJsonAnalyze(String requestJsonBody)
	 * @功能：转换学生注册页面JSON数据
	 * @功能说明：转换学生注册页面JSON数据
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.28
	 */
	public Map<String,Object> studentJsonAnalyze(String requestJsonBody) throws Exception
	{
		//用户信息验证标志位
		boolean status = false;
		Student student = new Student();
		LoginUser user = new LoginUser();
		Grade grade = new Grade();
		College college = new College();
		Map<String,Object> map1 = new HashMap<String,Object>();
		//解析JSON数据
		map1 = jsonAnalyze.json2Map(requestJsonBody);
		Map<String,Object> map2 = new HashMap<String,Object>();
		Set<String> key = map1.keySet();
		Iterator<String> iter = key.iterator();
		while(iter.hasNext())
		{
			String str = iter.next();
			switch(str)
			{
			//学生姓名
			case"studentName":
								student.setStudentName((String)map1.get(str));
								break;
			//学生昵称					
			case"nickname":
								if(regex.nicknameRegex(str))
								{
									user.setNickName((String)map1.get(str));
								}
								else
								{
									status = true;
								}
								break;
			//学生学号
			case"id":
								student.setId((String)map1.get(str));
								break;
			//学生手机号
			case"studentPhone":
				                if(regex.phoneRegex((String)map1.get(str)))
				                {
				                	student.setStudentPhone((String)map1.get(str));
				                }
				                else
				                {
				                	status = true;
				                }
								break;
			//学生身份证号
			case"studentCardId":
								if(regex.cardIDRegex((String)map1.get(str)))
								{
									student.setStudentCardId((String)map1.get(str));
								}
								else
								{
									status = true;
								}
								break;
			//学生密码
			case"password":
								String pd = du.digest((String)map1.get(str));
								user.setPassword(pd);
								break;
			//学生角色
			case"role":
								if(!"Role_Student".equals(map1.get(str)))
								{
									status = true;
								}
								user.setRole("Role_Student");
								break;
			//学生年级
			case"grade":
								grade.setYearClass((String)map1.get(str));
								break;
			//学生专业
			case"profession":
								grade.setProfession((String)map1.get(str));
								break;
			//学生班级
			case"classId":
								grade.setClassId((String)map1.get(str));
								break;
			//学生学院
			case"college":
								college.setCollegeName((String)map1.get(str));
								break;			
			default:
								System.out.println("无法解析的数据 ：" + (String)map1.get(str));
								break;
			}
			//如果信息验证失败，停止循环
			if(status)
			{
				break;
			}
		}
		//信息验证时出错，就使map2 = null
		if(status)
		{
			map2 = null;
		}
		else
		{
			System.out.println("执行了吗");
			student.setUser(user);
			map2.put("student", student);
			map2.put("user", user);
			map2.put("collegeName", college);
			map2.put("grade", grade);
		}
		return map2;
	}

	
	
	/*
	 * @方法名：headteacherJsonAnalyze(String requestJsonBody)
	 * @功能：转换辅导员注册页面JSON数据
	 * @功能说明：转换辅导员注册页面JSON数据
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.28
	 */
	public Map<String,Object>  headteacherJsonAnalyze(String requestJsonBody) throws Exception
	{
		//用户信息验证标志位
		boolean status = false;
		Teacher headteacher = new Teacher();
		LoginUser user = new LoginUser();
		College college = new College();
		//辅导员的班级信息
		List<Grade>  teacherClass = new ArrayList<Grade>();
		Grade grade;
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1 = jsonAnalyze.json2Map(requestJsonBody);
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		
		Set<String> key = map1.keySet();
		Iterator<String> iter = key.iterator();
		
		while(iter.hasNext())
		{
			String str = iter.next();
			//老师姓名
			if("headTeacherName".equals(str))
			{
				headteacher.setTeacherName((String)map1.get(str));
			}
			//老师昵称
			else if("nickname".equals(str))
			{
				if(regex.nicknameRegex(str))
				{
					user.setNickName((String)map1.get(str));
				}
				else
				{
					status = true;
				}
			}
			//老师手机号
			else if("teacherPhone".equals(str))
			{
				if(regex.phoneRegex((String)map1.get(str)))
				{
					headteacher.setTeacherPhone((String)map1.get(str));
				}
				else
				{
					System.out.println("手机号错误！");
					status = true;
					break;
				}
			}
			//老师身份证号
			else if("teacherCardId".equals(str))
			{
				if(regex.cardIDRegex((String)map1.get(str)))
				{
					headteacher.setTeacherCardId((String)map1.get(str));
				}
				else
				{
					System.out.println("身份证号错误！");
					status = true;
					break;
				}
			}
			//老师密码
			else if("password".equals(str))
			{
				String pd = du.digest((String)map1.get(str));
				user.setPassword(pd);
			}
			//老师的角色
			else if("role".equals(str))
			{
				if(!"Role_HeadTeacher".equals(map1.get(str)))
				{
					status = true;
				}
				user.setRole("Role_HeadTeacher");
			}
			else if("collegeName".equals(str))
			{
				college.setCollegeName((String)map1.get(str));
			}
			//老师班级
			else if("grade".equals(str))
			{
				@SuppressWarnings("unchecked")
				List<Integer > gradeStr=( (List<Integer >)map1.get(str));
				for(int i = 0; i < gradeStr.size(); i++)
				{
					Integer index = gradeStr.get(i);
					grade = gradeServiceImpl.get(index);
					teacherClass.add(grade);
				}
			 }
			 else
			 {
				 map2 = null;
				System.err.println("客户端的辅导员注册信息数据格式不正确！");
				break;
			 }
		}
		//信息验证时出错，就使map2 = null
		if(status)
		{
			map2 = null;
		}
		else
		{
			//辅导员的班级信息
			headteacher.setUser(user);
			headteacher.setTeacherClass(teacherClass);
			map2.put("user",user);
			map2.put("headteacher", headteacher);
			map2.put("college",college );
		}
		return map2;
	}	
	
	
	/*
	 * @方法名：teacherJsonAnalyze(String requestJsonBody)
	 * @功能：转换普通教师注册页面JSON数据
	 * @功能说明：转换普通教师注册页面JSON数据
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.28
	 */
	public Map<String, Object> teacherJsonAnalyze(String requestJsonBody) throws Exception
	{	
		//用户信息验证标志位
		boolean status = false;
		Teacher teacher  = new Teacher();
		LoginUser user = new LoginUser();
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1 = jsonAnalyze.json2Map(requestJsonBody);
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		
		Set<String> key = map1.keySet();
		Iterator<String> iter = key.iterator();
		while(iter.hasNext())
		{
			String str = iter.next();
			//老师姓名
			if("teacherName".equals(str))
			{
				teacher.setTeacherName((String)map1.get(str));
			}
			//老师昵称
			else if("nickname".equals(str))
			{
				if(regex.nicknameRegex(str))
				{
					user.setNickName((String)map1.get(str));
				}
				else
				{
					status = true;
				}
			}
			//老师手机号
			else if("teacherPhone".equals(str))
			{
				if(regex.phoneRegex((String)map1.get(str)))
				{
					teacher.setTeacherPhone((String)map1.get(str));
				}
				else
				{
					System.err.println("手机号验证失败");
					status = true;
					break;
				}
			}
			//老师身份证号
			else if("teacherCardId".equals(str))
			{
				if(regex.cardIDRegex((String)map1.get(str)))
				{
					teacher.setTeacherCardId((String)map1.get(str));
				}
				else
				{
					System.err.println("身份证号验证失败");
					status = true;
					break;
				}
			}
			//老师密码
			else if("password".equals(str))
			{
				String pd = du.digest((String)map1.get(str));
				user.setPassword(pd);
			}
			//老师角色
			else if("role".equals(str))
			{
				if(!"Role_Teacher".equals(map1.get(str)))
				{
					status = true;
				}
				user.setRole("Role_Teacher");
			}
			//出错
			else
			{
				System.err.println("多余的输入项："+str);
				status = true;
				break;
			}
		}
		//信息验证时出错，就使map2 = null
		if(status)
		{
			map2 = null;
		}
		else
		{
			teacher.setUser(user);
			map2.put("user", user);
			map2.put("teacher", teacher);
		}
        return map2;
	}
	
	
	
	/*
	 * @方法名：askJsonAnalyze(String requestJsonBody)
	 * @功能：转换请假页面JSON数据
	 * @功能说明：转换请假页面JSON数据
	 * @创建时间：2015.06.06
	 * @修改时间：2015.11.05
	 */
	public Map<String, Object> askJsonAnalyze(String requestJsonBody)throws  Exception
	{
		//状态标志位
		boolean status = false;
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1 = jsonAnalyze.json2Map(requestJsonBody);
		System.out.println("map1------>"+map1);
		//LoginUser user = new LoginUser();
		Student student = new Student();
		StudentAsk sAsk = new StudentAsk();		
		Map<String,Object> map2 = new HashMap<String,Object>();
		
		Set<String> key = map1.keySet();
		//Iterator 是集合的一个迭代器
		//声明一个迭代器，泛型里面是String类型，表示迭代元素是String类型的
		Iterator<String> iter = key.iterator();
		while(iter.hasNext())
		{
			String str = iter.next();
			if("Nickname".equals(str)){
				
				String nickName = (String)map1.get(str);
				//user = loginUserService.getLoginUserBynickName(nickName);
				student = studentService.getStudentByNickname(nickName);
				//注意。。。这里不能setNickName,因为nickName本身是存在的
				
			}
			else if("teachername".equals(str)){
				
				//请假辅导员姓名
				//给客户端一个url，客户端给服务端学生学号，服务端返回导员姓名
				//headteacher.setTeacherName((String)map1.get(str));
				sAsk.setHeadteacher((String)map1.get(str));
				
				
			}
			//请假开始时间
			else if("StartTime".equals(str))
			{
				sAsk.setTimestart((String)map1.get(str));
				//sAsk.setTimestart("2015-06-09");
			}
			//请假结束时间
			else if("EndTime".equals(str)){
				
				sAsk.setTimeend((String)map1.get(str));
				//sAsk.setTimeend("2015-06-12");
			}
			//父母姓名
			else if("ParentsName".equals(str)){
				
				sAsk.setParentsName((String)map1.get(str));
			}
			//父母手机号验证
			else if ("ParentsPhone".equals(str))
			 {
				sAsk.setParentsPhone((String)map1.get(str));
			}
			//请假类型
			else if("type".equals(str)){
				
				sAsk.setType((String)map1.get(str));
				
			}
			//是否就寝
			else if("sleep".equals(str)){
				sAsk.setSleep((String)map1.get(str));
			}
			//请假原因
			else if("reason".equals(str))
			{
				sAsk.setReason((String)map1.get(str));
			}
											
			else
			{
				System.err.println("客户端请假页面JSON数据的数据格式不正确！！");
				status = true;
				break;
			}		
			
			//自定义请假状态
			String status1 = "申请中";
			sAsk.setStatus(status1);
		
		}
		if(status)
		{
			sAsk = null;
		}
		//sAsk.setUser(user);
		sAsk.setStudent(student);
	
		map2.put("sAsk", sAsk);
		return map2;
	}	
}

