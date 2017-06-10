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

import com.b505.bean.util.GradeUtil;
import com.b505.json.JsonAnalyze;
import com.b505.service.ICollegeService;
import com.b505.service.IGradeService;
import com.b505.service.ITeacherService;
import com.b505.service.ILoginUserService;
import com.b505.tools.StatusMap;

@Controller
public class CollegeAndGradeManger {
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
	@RequestMapping(value="/getGradeutilByCollegeName.html")
	@ResponseBody
	public String getGradeUtilByCollege(@RequestBody String requestJsonBody) throws IOException{
				String Fail = statusMap.status("Fail");
				String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
				String string = null;
				Map<String,Object> collegeMap = new HashMap<String,Object>();
				collegeMap = jsonAnalyze.json2Map(requestJsonBody);
				String collegeName = (String)collegeMap.get("collegeName");
				if(collegeName==null||"".equals(collegeName))
				{
					return cannotAnalyzeData;
				}
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
				}else {
					string = jsonAnalyze.list2Json(grade);
				}
				return string;
	}
}
