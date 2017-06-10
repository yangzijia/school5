/*
*@包名：com.b505.web        
*@文档名：CheckCallOverInformationController.java 
*@功能：查看考勤      
*@作者：李振强        
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.CallOver;
import com.b505.bean.Grade;
import com.b505.bean.Student;
import com.b505.bean.StudentAsk;
import com.b505.bean.util.CallOverCountUtil;
import com.b505.bean.util.CallOverCountUtils;
import com.b505.bean.util.CallOverUtil;
import com.b505.bean.util.CallOverUtil1;
import com.b505.bean.util.CallOverUtils;
import com.b505.bean.util.GradeUtil;
import com.b505.json.JsonAnalyze;
import com.b505.service.ICallOverService;
import com.b505.service.IGradeService;
import com.b505.service.IStudentService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchCallOverCountService;
import com.b505.tools.TryCatchCallOverService;
import com.b505.tools.TryCatchFileDownload;
import com.b505.tools.TryCatchGradeService;
import com.b505.tools.TryCatchStudentAskService;
import com.b505.tools.TryCatchStudentService;

@Controller
public class CheckCallOverInformationController
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private IStudentService studentService;
	@Autowired
	private IGradeService gradeService;
	@Autowired
	private ICallOverService callOverService;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private TryCatchGradeService tryCatchGradeService;
	@Autowired
	private TryCatchCallOverService tryCatchCallOverService;
	@Autowired
	private TryCatchFileDownload tryCatchFileDownload;
	@Autowired
	private TryCatchCallOverCountService tryCatchCallOverCountService;
	@Autowired
	private TryCatchStudentAskService tryCatchStudentAskService;
	
	/*
	 * @方法名：getStudent(@RequestBody String requestJsonBody)
	 * @功能：点名时得到学生信息
	 * @功能说明：根据客户端返回的班级信息，发送这个班级的学生信息
	 * @作者：李振强
	 * @创建时间：2014.4.20
	 * @修改时间：2014.5.16
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/checkStuCallOver.html")
	@ResponseBody
	public String getStudent(@RequestBody String requestJsonBody)throws Exception
	{
		String[] requestGrae = {"grade","profession","classID"};
		Object[] responseGrade = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestGrae);
		if(dataProcess.dataIsNull(responseGrade)||dataProcess.arrayHasNull(responseGrade))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Grade grade  = tryCatchGradeService.getGradeByGradeNameAndPropertyAndClassID((String)responseGrade[0], (String)responseGrade[1], (String)responseGrade[2]);
		if(dataProcess.dataIsNull(grade))
		{
			return returnStatus.Fail;
		}
		Integer gradeID = grade.getGradeId();
		List<Student> studentList = tryCatchStudentService.getStudentListByGradeID(gradeID);
		if(dataProcess.dataIsNull(studentList)||dataProcess.listHasNull(studentList))
		{
			return returnStatus.Fail;
		}
		String type = "审批通过";
		List<StudentAsk> listAsks = tryCatchStudentAskService.getSAskByGradeIdandStatus(gradeID, type);
		if(dataProcess.dataIsNull(listAsks)){
			Map<String,Object>studentMessageMap = new HashMap<String,Object>();
			final int studentListSize = studentList.size();
			for(int i = 0; i < studentListSize; i++)
			{
				Student student = studentList.get(i);
				String fileURL = student.getUser().getImgUrl();
				String[] studentInfor = {"studentName","studentID","headSculpture","isAsk"};
				Object[] studentInforValue = {student.getStudentName(),student.getId(),fileURL,0};
				Map<String,Object> studentInformation = dataProcess.getMapByStringArray(studentInfor, studentInforValue);
				if(dataProcess.dataIsNull(studentInformation))
				{
					return returnStatus.Fail;
				}
				studentMessageMap.put(""+i, studentInformation);
			}
			String studentListJson = jsonAnalyze.map2Json(studentMessageMap);
			
			if(dataProcess.dataIsNull(studentListJson))
			{
				return returnStatus.Fail;
			}
			
			return jsonAnalyze.map2Json(studentMessageMap);
		}else {
			
			Map<String,Object> studentMessageMap = new HashMap<String,Object>();
			final int studentListSize = studentList.size();
			for(int i = 0; i < studentListSize; i++)
			{
				Student student = studentList.get(i);
				StudentAsk sAsk =  tryCatchStudentAskService.getSAskBynickNameAndType(student.getId(), type);
				int isAsk = 0;				
				if(dataProcess.dataIsNull(sAsk)){
					isAsk = 0; //该学生没有审批通过的假条
				}else{
					isAsk = 1;  //有审批通过的假条
				}
				String fileURL = student.getUser().getImgUrl();
				String[] studentInfor = {"studentName","studentID","headSculpture","isAsk"};
				Object[] studentInforValue = {student.getStudentName(),student.getId(),fileURL,isAsk};
				Map<String,Object> studentInformation = dataProcess.getMapByStringArray(studentInfor, studentInforValue);
				if(dataProcess.dataIsNull(studentInformation))
				{
					return returnStatus.Fail;
				}
				studentMessageMap.put(""+i, studentInformation);
			}
			String studentListJson = jsonAnalyze.map2Json(studentMessageMap);
			
			if(dataProcess.dataIsNull(studentListJson))
			{
				return returnStatus.Fail;
			}
			
			return jsonAnalyze.map2Json(studentMessageMap);
		}		
	}
	
	
	/*
	 * @方法名：getClassName(@RequestBody String requestJsonBody)
	 * @功能：通过年级、专业、班级返回课程信息
	 * @功能说明：给客户端返回课程信息
	 * @作者：李振强
	 * @创建时间：2014.4.20
	 * @修改时间：2014.5.16
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/getClassName.html")
	@ResponseBody
	public String getClassName(@RequestBody String requestJsonBody)throws Exception
	{
		String[] requestGrade = {"grade","profession","classID"};
		Object[] responseGrade = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestGrade);
		if(dataProcess.dataIsNull(responseGrade)||dataProcess.arrayHasNull(responseGrade))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Grade grade = tryCatchGradeService.getGradeByGradeNameAndPropertyAndClassID((String)responseGrade[0],(String)responseGrade[1],(String)responseGrade[2]);
		if(dataProcess.dataIsNull(grade))
		{
			return returnStatus.Fail;
		}
		Integer gradeID = grade.getGradeId();
		List<String> courseNameList = tryCatchCallOverService.getCourseNameListByGradeID(gradeID);
		if(dataProcess.dataIsNull(courseNameList)||dataProcess.listHasNull(courseNameList))
		{
			return returnStatus.Fail;
		}
		Map<String,Object> calssInformationMap = new HashMap<String,Object>();
		calssInformationMap.put("className", courseNameList);
		String json = jsonAnalyze.map2Json(calssInformationMap);
		return json;		
	}
	

	/*
	 * @方法名：getCheckOutcome(@RequestBody String requestJsonBody)
	 * @功能：查看考勤
	 * @功能说明：领导或辅导员查看考勤
	 * @作者：李振强
	 * @创建时间：2014.4.20
	 * @修改时间：2014.5.16
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/checkOutcome.html")
	@ResponseBody
	public String getCheckOutcome(@RequestBody String requestJsonBody)throws Exception
	{
		String[] requestOutcome = {"date","course","classTime","grade","profession","classID"};
		Object[] responseOutcome = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestOutcome);
		if(dataProcess.dataIsNull(responseOutcome)||dataProcess.arrayHasNull(responseOutcome))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Grade grade = tryCatchGradeService.getGradeByGradeNameAndPropertyAndClassID((String)responseOutcome[3], (String)responseOutcome[4], (String)responseOutcome[5]);
		if(dataProcess.dataIsNull(grade))
		{
			return returnStatus.Fail;
		}
		Integer gradeID = grade.getGradeId();
		List<CallOver> calloverList = tryCatchCallOverService.getCallOverListByDateGradeIDClassTimeClassName((String)responseOutcome[0], gradeID, (String)responseOutcome[2], (String)responseOutcome[1]);
		if(dataProcess.dataIsNull(calloverList)||dataProcess.listHasNull(calloverList))
		{
			return returnStatus.Fail;
		}
		final int calloverListSize = calloverList.size();
		Map<String,Object> callOverMap = new HashMap<String,Object>();
		for(int i = 0; i < calloverListSize; i++)
		{
			Student student = calloverList.get(i).getStudent();
			String[] studentMessageKey = {"studentName","studentNumber","studyStatus","headSculpture"};
			Object[] studentMessageValue = {student.getStudentName(),student.getId(),calloverList.get(i).getStatus(),student.getUser().getImgUrl()};
			Map<String,Object> studentMap = dataProcess.getMapByStringArray(studentMessageKey, studentMessageValue);
			callOverMap.put(""+i, studentMap);
		}
		String json = jsonAnalyze.map2Json(callOverMap);
		return json;
	}
	
	
	/*
	 * @方法名：getCheckOutcomeByCollegeName(@RequestBody String requestJsonBody)
	 * @功能：领导快速查看考勤
	 * @功能说明：领导快速查看考勤
	 * @作者：李振强
	 * @创建时间：2014.5.30
	 * @修改时间：2014.5.30
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/checkOutcomeByCollegeName.html")
	@ResponseBody
	public String getCheckOutcomeByCollegeName(@RequestBody String requestJsonBody)throws Exception
	{
		String[] requestKey = {"collegeName", "currentPage", "pageSize"};
		Object[] requestValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestKey);
		if(dataProcess.dataIsNull(requestValue)||dataProcess.arrayHasNull(requestValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<CallOverCountUtil> callOverCountUtilList = tryCatchCallOverCountService.getCountUtilsByPagerAndCollege((String)requestValue[0], Integer.parseInt((String)requestValue[1]), Integer.parseInt((String)requestValue[2]));
		if(dataProcess.dataIsNull(callOverCountUtilList)||dataProcess.listHasNull(callOverCountUtilList))
		{
			return returnStatus.Fail;
		}
		Long totalCountSize = tryCatchCallOverCountService.getTotalCountByCollegeAndStatus((String)requestValue[0]);
		String[] responseKey = {"callOverCountUtilList", "totalCountSize"};
		Object[] responseValue = {callOverCountUtilList, totalCountSize};
		Map<String,Object> responseMap = dataProcess.getMapByStringArray(responseKey, responseValue);		
		return jsonAnalyze.map2Json(responseMap);	
	}
	
	
	/*
	 * @方法名：getCheckOutcomeByGradeID(@RequestBody String requestJsonBody)
	 * @功能：客户端辅导员快速查看考勤
	 * @功能说明：客户端辅导员快速查看考勤
	 * @作者：李振强
	 * @创建时间：2014.5.30
	 * @修改时间：2014.5.30
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/checkOutcomeByGradeID.html")
	@ResponseBody
	public String getCheckOutcomeByGradeID(@RequestBody String requestJsonBody)throws Exception
	{
		String gradeID = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "gradeID");
		if(dataProcess.dataIsNull(gradeID))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Grade grade = tryCatchGradeService.getGradeByGradeID(Integer.parseInt(gradeID));
		
		if(dataProcess.dataIsNull(grade))
		{
			return returnStatus.Fail;
		}
		
		List<CallOverCountUtil> callOverCountUtilList = tryCatchCallOverCountService.getCountUtilsByGrade(grade.getYearClass(), grade.getProfession(), grade.getClassId());
		if(dataProcess.dataIsNull(callOverCountUtilList)||dataProcess.listHasNull(callOverCountUtilList))
		{
			return returnStatus.Fail;
		}
		String[] responseKey = {"callOverCountUtilList"};
		Object[] responseValue = {callOverCountUtilList};
		Map<String,Object> responseMap = dataProcess.getMapByStringArray(responseKey, responseValue);		
		return jsonAnalyze.map2Json(responseMap);	
	}
	
	/*
	 * @方法名：WebcheckOutcomeByGradeID(@RequestBody String requestJsonBody)
	 * @功能：服务端辅导员按日期快速查看考勤
	 * @功能说明：服务端辅导员按日期快速查看考勤
	 * @作者：lyf
	 * @创建时间：2015.11.21
	 */
	@RequestMapping(value = "/WebcheckOutcomeByGradeID.html")
	@ResponseBody
	public String WebcheckOutcomeByGradeID(@RequestBody String requestJsonBody)throws Exception
	{
		String gradeID = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "gradeID");
		String STime = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "STime");
		String ETime = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "ETime");
		System.out.println("gradeID------>"+gradeID);
		if(dataProcess.dataIsNull(gradeID))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Grade grade = tryCatchGradeService.getGradeByGradeID(Integer.parseInt(gradeID));
		
		if(dataProcess.dataIsNull(grade))
		{
			return returnStatus.Fail;
		}
		
		//List<CallOverCountUtil> callOverCountUtilList = tryCatchCallOverCountService.getCountUtilsByGrade(grade.getYearClass(), grade.getProfession(), grade.getClassId());
		List<CallOverUtils> callOverUtilsList = tryCatchCallOverService.getCountUtilsByGradeAndTime(grade.getYearClass(), grade.getProfession(), grade.getClassId(), STime, ETime);
				
		if(dataProcess.dataIsNull(callOverUtilsList)||dataProcess.listHasNull(callOverUtilsList))
		{
			return returnStatus.Fail;
		}
		String[] responseKey = {"callOverUtilsList"};
		Object[] responseValue = {callOverUtilsList};
		Map<String,Object> responseMap = dataProcess.getMapByStringArray(responseKey, responseValue);		
		return jsonAnalyze.map2Json(responseMap);	
	}
	
	
	/*
	 * @方法名：getStudentInformation(@RequestBody String requestJsonBody)
	 * @功能：通过学号返回该学生的详细班级信息
	 * @功能说明：通过学号返回该学生的详细班级信息
	 * @作者：李振强
	 * @创建时间：2014.5.30
	 * @修改时间：2014.5.30
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/getStudentInformation.html")
	@ResponseBody
	public String getStudentInformation(@RequestBody String requestJsonBody)throws Exception
	{
		String studentNumber = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "studentNumber");
		GradeUtil gradeUtil = tryCatchGradeService.getGradeUtilByStudentNumber(studentNumber);
		if(dataProcess.dataIsNull(gradeUtil))
		{
			return returnStatus.Fail;
		}
		List<CallOverUtil> callOverUtilList = tryCatchCallOverService.getCallOverByStudentNumber(studentNumber);
		if(dataProcess.dataIsNull(callOverUtilList)||dataProcess.listHasNull(callOverUtilList))
		{
			return returnStatus.Fail;
		}
		String[] studentInforKey = {"grade", "callOver"};
		Object[] studentInforValue = {gradeUtil, callOverUtilList};
		Map<String,Object> studentMap = dataProcess.getMapByStringArray(studentInforKey, studentInforValue);
		return jsonAnalyze.map2Json(studentMap);
	}
	
	/*
	 * @方法名：getLeaderGetCheckOut(@RequestBody String requestJsonBody)
	 * @功能：客户端领导快速查看考勤
	 * @功能说明：客户端快速查看考勤
	 * @作者：李振强
	 * @创建时间：2014.5.30
	 * @修改时间：2014.5.30
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/leaderGetCheckOut.html")
	@ResponseBody
	public String getLeaderGetCheckOut(@RequestBody String requestJsonBody) throws Exception{
		String collegeName = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "collegeName");
		String STime = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "STime");
		String ETime = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "ETime");
		if(dataProcess.dataIsNull(collegeName)||dataProcess.dataIsNull(STime)||dataProcess.dataIsNull(ETime))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<CallOverCountUtil> callOverCountUtilList = tryCatchCallOverCountService.getCountUtilsByCollege(collegeName);
		if(dataProcess.dataIsNull(callOverCountUtilList)||dataProcess.listHasNull(callOverCountUtilList))
		{
			return returnStatus.Fail;
		}
		return jsonAnalyze.list2Json(callOverCountUtilList);
	}
	
	
	
	@RequestMapping(value="/get.html")
	@ResponseBody
	public String getwebLeaderGetCheckOut() throws Exception{
		List<CallOverUtils> callOverUtilsList = tryCatchCallOverService.getAllCountUtils();
		return jsonAnalyze.list2Json(callOverUtilsList);
	}
	
	
	
	/*
	 * @方法名：getwebLeaderGetCheckOut(@RequestBody String requestJsonBody)
	 * @功能：在服务端领导按日期快速查看考勤之前，在初始化界面时加载所有考勤数据
	 * @功能说明：在服务端领导按日期快速查看考勤之前，在初始化界面时加载所有考勤数据
	 * @作者：JSY
	 * @修改时间：2016.4.1
	 */
	@RequestMapping(value="/getwebLeaderGetCheckOut.html")
	@ResponseBody
	public String getwebLeaderGetCheckOut(@RequestBody String requestJsonBody) throws Exception{
	  
	  //String collegeName = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "collegeName");
		String yearClass = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "yearClass");
		String profession = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "profession");
		String classId = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "classId");
		String STime = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "STime");
		String ETime = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "ETime");
//		if(dataProcess.dataIsNull(yearClass)||dataProcess.dataIsNull(profession)||dataProcess.dataIsNull(classId)||dataProcess.dataIsNull(STime)||dataProcess.dataIsNull(ETime))
//		{
//			return returnStatus.CannotAnalyzeData;
//		}
		
		if ("".equals(yearClass)||"".equals(profession)||"".equals(classId)||"".equals(STime)||"".equals(ETime))
		{
			List<CallOverUtils> callOverUtilsList = tryCatchCallOverService.getAllCountUtils();
			if ("".equals(callOverUtilsList)||dataProcess.dataIsNull(callOverUtilsList)||callOverUtilsList==null)
			{
				  return returnStatus.Fail;
			}
			return jsonAnalyze.list2Json(callOverUtilsList);
		}else
		    {
				//List<CallOverUtils> callOverUtilsList = tryCatchCallOverService.getCallOverBycollegeNameAndTime(collegeName, STime, ETime);
				List<CallOverUtils> callOverUtilsList = tryCatchCallOverService.getCountUtilsByGradeAndTime(yearClass,profession,classId, STime, ETime);
				if(dataProcess.dataIsNull(callOverUtilsList)||dataProcess.listHasNull(callOverUtilsList))
				{
					return returnStatus.Fail;
			    }
			return jsonAnalyze.list2Json(callOverUtilsList);
		    }
		
	}
	
	@RequestMapping(value="/leaderGetCheckOutDetail.html")
	@ResponseBody
	public String  getleaderGetCheckOutDetail(HttpServletRequest request) throws Exception{
		
		HttpSession hp = request.getSession();
		String studentNumber = (String)hp.getAttribute("StudentNumber");
		String studentName = (String)hp.getAttribute("StudentName");
		GradeUtil gradeUtil = tryCatchGradeService.getGradeUtilByStudentNumber(studentNumber);
		
		if(dataProcess.dataIsNull(gradeUtil))
		{
			return returnStatus.Fail;
		}
		List<CallOverUtil> callOverUtilList = tryCatchCallOverService.getCallOverByStudentNumber(studentNumber);
		List<CallOverUtil1> callOverUtilLists = new ArrayList<CallOverUtil1>();
		for(int i=0;i<callOverUtilList.size();i++){
			
			CallOverUtil1 cUtil1 = new CallOverUtil1();
			Date date = callOverUtilList.get(i).getDate();
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); //格式化当前日期
			String dateTime = dateFm.format(date);
			String className = callOverUtilList.get(i).getClassName();
			String teacherName = callOverUtilList.get(i).getTeacherName();
			cUtil1.setDate(dateTime);
			cUtil1.setClassName(className);
			cUtil1.setTeacherName(teacherName);
			callOverUtilLists.add(cUtil1);
		}
		if(dataProcess.dataIsNull(callOverUtilList)||dataProcess.listHasNull(callOverUtilList))
		{
			return returnStatus.Fail;
		}
		String[] studentInforKey = {"grade", "callOver","studentName","studentNumber"};
		Object[] studentInforValue = {gradeUtil, callOverUtilLists,studentName,studentNumber};
		Map<String,Object> studentMap = dataProcess.getMapByStringArray(studentInforKey, studentInforValue);
		return jsonAnalyze.map2Json(studentMap);
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-20
	 * @功能 进入考勤预警界面
	 */
	@RequestMapping(value="/callOverWarn.html")
	public String getcallOverWarn(){
		return "callOverWarn";
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-20
	 * @function 辅导员查看其管理班级的学的考勤预警信息
	 * @return 考勤预警信息
	 * @throws Exception
	 */
	@RequestMapping(value="/getcallOverWarnByGrade.html")
	@ResponseBody
	public String getcallOverWarnInfo(@RequestBody String requestJsonBody) throws Exception{
		String gradeID = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "gradeID");
		if(dataProcess.dataIsNull(gradeID))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Grade grade = tryCatchGradeService.getGradeByGradeID(Integer.parseInt(gradeID));
		if(dataProcess.dataIsNull(grade))
		{
			return returnStatus.Fail;
		}	
		List<CallOverCountUtils> callOverCountUtilLists = tryCatchCallOverCountService.getWarnCountUtilsByGrade(grade.getYearClass(), grade.getProfession(), grade.getClassId());
		if(dataProcess.dataIsNull(callOverCountUtilLists)||dataProcess.listHasNull(callOverCountUtilLists))
		{
			return returnStatus.Fail;
		}
		String[] responseKey = {"callOverCountUtilLists"};
		Object[] responseValue = {callOverCountUtilLists};
		Map<String,Object> responseMap = dataProcess.getMapByStringArray(responseKey, responseValue);		
		return jsonAnalyze.map2Json(responseMap);
	}
}

