package com.b505.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Course;
import com.b505.bean.util.GradeId;
import com.b505.json.JsonAnalyze;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.SessionGet;
import com.b505.tools.TryCatchCourseService;
import com.b505.tools.TryCatchStudentAskService;

@Controller
public class CourseController {

	
	@Autowired
	private SessionGet sessionGet;
	@Autowired
	private TryCatchStudentAskService tryCatchStudentAskService;
	@Autowired
	private TryCatchCourseService tryCatchCourseService;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private AnalyzeData analyzeData;
	
	/*
	 * @方法名：getstudentCourse()
	 * @功能：学生查询课表页面
	 * @功能说明：学生查询课表页面
	 * @创建时间：2015.10.05
	 */
	@RequestMapping(value="/course.html")
	public String getstudentCourse(){
		return "course";
	}
	
	@RequestMapping(value="/teachergetCourse.html")
	public String getteacherCourse(){
		return "teacherCourse";
	}
	
	/*
	 * @方法名：getCourse(@RequestBody String requestJsonBody)
	 * @功能：学生查看课表信息
	 * @功能说明：学生查看课表信息
	 * @创建时间：2015.10.07
	 */
	@RequestMapping(value="/getCourse.html")
	@ResponseBody//注明返回给客户端的数据为json数据
	public String getCourse()throws Exception
	{
		//public String getCourse(@RequestBody String requestJsonBody)throws Exception
		//这样写是因为客户端会传来json数据，不传的话就不用写了，否则出现问题
		
		//System.out.println("客户端或服务端页面传来的json数据:"+requestJsonBody);
		String studentNickname = sessionGet.getUserInfo().getUsername();
		
		//由学生昵称得到该学生的班级id
		GradeId g = tryCatchStudentAskService.getClassIdByNickname(studentNickname);
		int classid = g.getGid();
		
		//由班级id获取该班级的课表信息
		Course course = tryCatchCourseService.getCourseByClassId(classid);
		
		//将课表信息转为json数据
		String courseJson = jsonAnalyze.object2Json(course);
		
		//判断获得的数据是否为空
		if(dataProcess.dataIsNull(courseJson))
		{
			return returnStatus.Fail;
		}

		//将json格式的课表数据返回给客户端
		return courseJson;
	}	
	
	@RequestMapping(value="/getteacherCourse.html")
	@ResponseBody//注明返回给客户端的数据为json数据
	public String getTeacherCourse(@RequestBody String requestJsonBody)throws Exception
	{
		//System.out.println("requestJsonBody-------------->"+requestJsonBody);
		Map<String,Object> map = new HashMap<String,Object>();
		Integer gradeId;
		try
		{
			 map = jsonAnalyze.json2Map(requestJsonBody);	
			 gradeId = (int)map.get("gradeID");
		}catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("辅导员查询课表信息;出错");
			return null;
		}
		
		//由班级id获取该班级的课表信息
		Course course = tryCatchCourseService.getCourseByClassId(gradeId);
		
		//将课表信息转为json数据
		String courseJson = jsonAnalyze.object2Json(course);
		
		//判断获得的数据是否为空
		if(dataProcess.dataIsNull(courseJson))
		{
			return returnStatus.Fail;
		}

		//将json格式的课表数据返回给客户端
		return courseJson;
	}
}
