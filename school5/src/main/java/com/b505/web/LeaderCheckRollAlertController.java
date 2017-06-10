package com.b505.web;


import java.util.ArrayList;
import java.util.Map;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.b505.bean.util.GradeUtil;
//import com.b505.bean.util.Roll_AlertUtil;
import com.b505.bean.util.Roll_AlertUtil1;
import com.b505.bean.util.Roll_AlertUtil2;
import com.b505.bean.util.Roll_AlertUtils;
import com.b505.json.JsonAnalyze;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchGradeService;
import com.b505.tools.TryCatchLeaderService;
import com.b505.tools.TryCatchStudentService;

@Controller
public class LeaderCheckRollAlertController
{
	
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchLeaderService tryCatchLeaderService;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private TryCatchGradeService tryCatchGradeService;
	/*
	 * 领导查看预警成绩
	 */
	@RequestMapping(value="LeaderCheckRollAlert.html")
	public String leadercollegeAdmin()
	{
		return "leadercollegeAdmin";
	}
	
	/*
	 * 领导查看预警成绩的详细信息
	 */
	@RequestMapping(value="leaderCheckRoll_AlertDetail.html")
	public String leaderCheckRoll_AlertDetail(HttpServletRequest request)
	{
		String StudentNumber=request.getParameter("StudentNumber");
		String StudentName=request.getParameter("StudentName");
		request.getSession().setAttribute("StudentNumber", StudentNumber);
    	request.getSession().setAttribute("StudentName", StudentName);
		return "leaderCheckRoll_AlertDetail";
	}
	
	/**
	 * @author 少游
	 * @功能：领导根据学院、年级、专业、班级查询预警成绩
	 * @方法名：getwebLeaderGetRollAlert(@RequestBody String requestJsonBody)
	 * @时间：2016.10.20
	 */
      @RequestMapping(value="/getwebLeaderGetRollAlert.html")
      @ResponseBody
      public String getwebLeaderGetRollAlert(@RequestBody String requestJsonBody) throws Exception{
    	  if (requestJsonBody==null||dataProcess.dataIsNull(requestJsonBody))
		{
			return returnStatus.CannotAnalyzeData;
		}
    	  @SuppressWarnings("unchecked")
		Map<String, Object>maps=(Map<String, Object>) analyzeData.clientDataBeAnalyzedToServiceDataMap(requestJsonBody);
    	 String college=(String) maps.get("college");
    	 String yearClass=(String) maps.get("yearClass");
    	 String profession=(String) maps.get("profession");
    	 String classId=(String) maps.get("classId");
    	 //System.out.println("classId---->"+classId);
    	if (college!=null&&yearClass!=null&&profession!=null&&classId!=null)
		{
			List<Roll_AlertUtils>RaList=new ArrayList<>();
			RaList=tryCatchLeaderService.getRoll_Alert(college,yearClass,profession,classId);
			System.out.println("RaList----->"+RaList);
			if (RaList==null||dataProcess.listHasNull(RaList))
			{
				return returnStatus.Fail;
			}
			System.out.println(jsonAnalyze.list2Json(RaList));
			return jsonAnalyze.list2Json(RaList);
	   }else {
		return null;
	}
		
   }
      
      /**
  	 * @author 少游
  	 * @功能：领导根据学生学号和姓名查询预警成绩的详细信息
  	 * @方法名：getLeaderGetRollAlertDetail(@RequestBody String requestJsonBody)
  	 * @时间：2016.10.20
  	 */
      @RequestMapping(value="/leaderGetCheckOutRollDetail.html")
      @ResponseBody
      public String getLeaderGetRollAlertDetail(HttpServletRequest request)throws Exception{
    	  HttpSession hp = request.getSession();
  		  String studentNumber = (String)hp.getAttribute("StudentNumber");
  		  String studentName = (String)hp.getAttribute("StudentName");
          GradeUtil gradeUtil = tryCatchGradeService.getGradeUtilByStudentNumber(studentNumber);
		if(dataProcess.dataIsNull(gradeUtil))
		{
			return returnStatus.Fail;
		}
		List<Roll_AlertUtil2>list=tryCatchLeaderService.getLeaderGetRollAlertDetail(studentNumber);
		final int listsize=list.size();
		List<Roll_AlertUtil1>list1=new ArrayList<Roll_AlertUtil1>();
		for (int i = 0; i < listsize; i++)
		{
			Roll_AlertUtil2 ra=list.get(i);
			Roll_AlertUtil1 ra1=new Roll_AlertUtil1();
			ra1.setCourse(ra.getCourse());
			ra1.setScore(ra.getScore());
			ra1.setCoursetype(ra.getCoursetype());
			ra1.setOpinion(ra.getOpinion());
			list1.add(ra1);
		}
		String[] key={"grade", "rollAlert","studentName","studentNumber"};
		Object[] value={gradeUtil,list1,studentName,studentNumber};
		Map<String,Object>Map = dataProcess.getMapByStringArray(key,value);
		System.out.println("Map----->"+Map);
    	  return jsonAnalyze.map2Json(Map);
      }
      
      /**
  	 * @author 少游
     * @throws Exception 
  	 * @功能：初始化页面时加载不及格学生数据
  	 * @方法名：getwebLeaderGetAllRollAlert(@RequestBody String requestJsonBody)
  	 * @时间：2016.10.21
  	 */
      
      @RequestMapping(value="/getwebLeaderGetAllRollAlert.html")
      @ResponseBody
      public String getwebLeaderGetAllRollAlert(HttpServletRequest request) throws Exception{
    	  
    	  List<Roll_AlertUtils>rAlerts =tryCatchLeaderService.getwebLeaderGetAllRollAlert();
    	  if (rAlerts==null||dataProcess.listHasNull(rAlerts))
		{
			return returnStatus.CannotAnalyzeData;
		}else {
		     return jsonAnalyze.list2Json(rAlerts);
		}
     }
}
