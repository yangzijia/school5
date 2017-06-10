/*
*@包名：com.b505.web        
*@文档名：紧急情况模块 
*@功能：返回学生的同班同学、辅导员、学校领导的联系方式        
*@作者：李振强        
*@创建时间：2014.3.31   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Grade;
import com.b505.bean.HeadteacherClass;
import com.b505.bean.Leader;
import com.b505.bean.LongLatType;
import com.b505.bean.Student;
import com.b505.bean.Teacher;
import com.b505.json.JsonAnalyze;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchCollegeService;
import com.b505.tools.TryCatchGradeService;
import com.b505.tools.TryCatchHeadteacherClassService;
import com.b505.tools.TryCatchInteger;
import com.b505.tools.TryCatchLeaderService;
import com.b505.tools.TryCatchLongLatService;
import com.b505.tools.TryCatchStudentService;
import com.b505.tools.TryCatchTeacherClassService;
import com.b505.tools.TryCatchTeacherService;


@Controller
public class EmergencyController 
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private TryCatchTeacherService tryCatchTeacherService;
	@Autowired
	private TryCatchTeacherClassService tryCatchTeacherClassService;
	@Autowired
	private TryCatchLeaderService tryCatchLeaderService;
	@Autowired
	private TryCatchGradeService tryCatchGradeService;
	@Autowired
	private TryCatchInteger tryCatchInteger;
	@Autowired
	private ReturnObjectByAttribute returnObjectByAttribute;
	@Autowired
	private TryCatchCollegeService tryCatchCollegeService;
	@Autowired
	private TryCatchLongLatService tryCatchLongLatService;
	@Autowired
	private TryCatchHeadteacherClassService tryCatchHeadteacherClassService;
	
	/*
	 * @方法名：studentSos(@RequestBody String requestJsonBody)
	 * @功能：学生求救
	 * @功能说明：学生求救
	 * @作者：李振强
	 * @创建时间：2014.4.5
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/emerStudent.html")
	@ResponseBody
	public String studentSos(@RequestBody String requestJsonBody)throws Exception
	{
		String[] requestStudentKey = {"userNickname"};
		Object[] responseStudentValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestStudentKey);
		if(dataProcess.dataIsNull(responseStudentValue)||dataProcess.arrayHasNull(responseStudentValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Student student = tryCatchStudentService.getStudentByNickname((String)responseStudentValue[0]);
		if(dataProcess.dataIsNull(student))
		{
			return returnStatus.Fail;
		}
		Grade grade = student.getStudentClass();
		if(dataProcess.dataIsNull(grade))
		{
			return returnStatus.Fail;
		}
		Integer  gradeID = grade.getGradeId();
		List<Student> studentList = tryCatchStudentService.getStudentListByGradeID(gradeID);
		if(dataProcess.dataIsNull(studentList)||dataProcess.listHasNull(studentList))
		{
			return returnStatus.Fail;
		}
		//List<Leader> leaderList = tryCatchLeaderService.getAllLeader();
		//2015.11.17修改第二个方法中的rank条件
		int collegeid = tryCatchCollegeService.getCollegeIdByGradeId(gradeID);
		List<Leader> leaderList = tryCatchLeaderService.getLeadersByCollegeAndRank(collegeid);
		if(dataProcess.dataIsNull(leaderList)||dataProcess.listHasNull(leaderList))
		{
			return returnStatus.Fail;
		}
		//TeacherClass teacherClass = tryCatchTeacherClassService.getTeacherClassByStudentCalss(grade);
		HeadteacherClass hteacherClass = tryCatchHeadteacherClassService.getHeadteacherClassByStudentCalss(grade);
		if(dataProcess.dataIsNull(hteacherClass))
		{
			return returnStatus.Fail;
		}
		Teacher headTeacher = hteacherClass.getTeacher();
		Map<String,Object> allStudentMap = new HashMap<String,Object>();
		final int studentListSize = studentList.size();	
		for(int i = 0; i < studentListSize; i++)
		{
			String[] studentKey = {"studentName","phone"};
			String[] studentValue = {studentList.get(i).getStudentName(),studentList.get(i).getStudentPhone()};
			Map<String,Object> studentMap = dataProcess.getMapByStringArray(studentKey, studentValue);
			allStudentMap.put(""+i, studentMap);
		}
		Map<String,Object> allLeaderMap = new HashMap<String,Object>();
		final int leaderListSize = leaderList.size();
		for(int i = 0; i < leaderListSize; i++)
		{
			String[] leaderKey = {"leaderName","phone"};
			String[] leaderValue = {leaderList.get(i).getName(),leaderList.get(i).getPhone()};
			Map<String,Object> leaderMap = dataProcess.getMapByStringArray(leaderKey, leaderValue);
			allLeaderMap.put(""+i, leaderMap);
		}
		String[] headteacherKey = {"headTeacherName","phone"};
		String[] headteacherValue = {headTeacher.getTeacherName(),headTeacher.getTeacherPhone()};
		Map<String,Object> headteacherMap = dataProcess.getMapByStringArray(headteacherKey, headteacherValue);
		String[] allMapKey = {"classmates","leader","headTeacher"};
		Object[] allMapValue = {allStudentMap,allLeaderMap,headteacherMap};
		Map<String,Object> allMap = dataProcess.getMapByStringArray(allMapKey, allMapValue);
		if(dataProcess.dataIsNull(allMap))
		{
			return returnStatus.Fail;
		}
		String allMapJson = jsonAnalyze.map2Json(allMap);
		if(dataProcess.dataIsNull(allMapJson))
		{
			return returnStatus.Fail;
		}
		return allMapJson;
	}
	
	
	/*
	 * @方法名：headTeacherSosGetCollege(@RequestBody String requestJsonBody)
	 * @功能：根据辅导员的昵称得到辅导员所在学院的辅导员和整个学校的领导信息
	 * @功能说明：根据辅导员的昵称得到辅导员所在学院的辅导员和整个学校的领导信息
	 * @作者：李振强
	 * @创建时间：2014.4.5
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/getHeadteacherLeader.html")
	@ResponseBody
	public String getHeadTeacherLeader(@RequestBody String requestJsonBody)throws Exception
	{
		String[] headTeacherKey = {"headTeacherNickname"};
		Object[] headTeacherValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, headTeacherKey);
		if(dataProcess.dataIsNull(headTeacherValue)||dataProcess.arrayHasNull(headTeacherValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Teacher headTeacher = tryCatchTeacherService.getTeacherByNickname((String)headTeacherValue[0]);
		if(dataProcess.dataIsNull(headTeacher))
		{
			return returnStatus.Fail;
		}
		String collegeName = headTeacher.getCollege().getCollegeName();
		List<Teacher> headTeacherList = tryCatchTeacherService.getHeadTeacherListByCollege(collegeName);
		if(dataProcess.dataIsNull(headTeacherList)||dataProcess.listHasNull(headTeacherList))
		{
			return returnStatus.Fail;
		}
		List<Leader> leaderList = tryCatchLeaderService.getAllLeader();
		if(dataProcess.dataIsNull(leaderList)||dataProcess.listHasNull(leaderList))
		{
			return returnStatus.Fail;
		}
		Map<String,Object> allHeadteacherMap = new HashMap<String,Object>();
		final int headTeacherListSize = headTeacherList.size();
		for(int i = 0; i < headTeacherListSize; i++)
		{
			String[] headTeacherMapKey = {"headTeacherName","phone"};
			String[] headTeacherMapValue = {headTeacherList.get(i).getTeacherName(),headTeacherList.get(i).getTeacherPhone()};
			Map<String,Object> headTeacherMap =dataProcess.getMapByStringArray(headTeacherMapKey, headTeacherMapValue);
			allHeadteacherMap.put(""+i, headTeacherMap);
		}
		Map<String,Object> allLeaderMap = new HashMap<String,Object>();
		final int leaderListSize = leaderList.size();
		for(int i = 0; i < leaderListSize; i++)
		{
			String[] leaderMapKey = {"leaderName","phone"};
			String[] leaderMapValue = { leaderList.get(i).getName(),  leaderList.get(i).getPhone()};
			Map<String,Object> leaderMap = dataProcess.getMapByStringArray(leaderMapKey, leaderMapValue);
			allLeaderMap.put(""+i, leaderMap);
		}
		String[] allMapKey = {"headTeacher","leader"};
		Object[] allMapValue = {allHeadteacherMap,allLeaderMap};
		Map<String,Object> allMap = dataProcess.getMapByStringArray(allMapKey, allMapValue);
		String allMapJson = jsonAnalyze.map2Json(allMap);
		if(dataProcess.dataIsNull(allMapJson))
		{
			return returnStatus.Fail;
		}
		return allMapJson;
	}
	
	
	/*
	 * @方法名：leaderSosGetCollege(@RequestBody String requestJsonBody)
	 * @功能：得到学生和辅导员信息
	 * @功能说明：根据gradeID或grade、profession、classID得到相应的学生和辅导员信息
	 * @作者：李振强
	 * @创建时间：2014.4.5
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/emerStudentAndTeacher.html")
	@ResponseBody
	public String leaderSosGetCollege(@RequestBody String requestJsonBody)throws Exception
	{
		String[] requestKey = {"gradeID","grade","profession","classID"};
		Object[] responseValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestKey);
		if(dataProcess.dataIsNull(responseValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Integer gradeID =tryCatchInteger.String2Number((String)responseValue[0]);
		if(dataProcess.dataIsNull(gradeID))
		{
			gradeID = tryCatchGradeService.getGradeIDByGradePropertyClassID((String)responseValue[1], (String)responseValue[2], (String)responseValue[3]);
		}
		if(dataProcess.dataIsNull(gradeID))
		{
			return returnStatus.Fail;
		}
		Grade grade = tryCatchGradeService.getGradeByGradeID(gradeID);
		if(dataProcess.dataIsNull(grade))
		{
			return returnStatus.Fail;
		}
		List<Student> studentList = tryCatchStudentService.getStudentListByGradeID(gradeID);
		if(dataProcess.dataIsNull(studentList)||dataProcess.listHasNull(studentList))
		{
			return returnStatus.Fail;
		}
		//TeacherClass teacherClass = tryCatchTeacherClassService.getTeacherClassByStudentCalss(grade);
		HeadteacherClass hteacherClass = tryCatchHeadteacherClassService.getHeadteacherClassByStudentCalss(grade);
		if(dataProcess.dataIsNull(hteacherClass))
		{
			return returnStatus.Fail;
		}
		Teacher headTeacher = hteacherClass.getTeacher();
		Map<String,Object> allStudentMap = new HashMap<String,Object>();
		final int studentListSize = studentList.size();
		for(int i = 0; i < studentListSize; i++)
		{
			String[] studentKey = {"studentName","phone","headSculpture"};
			String[] studentValue = {studentList.get(i).getStudentName(),studentList.get(i).getStudentPhone(),studentList.get(i).getUser().getImgUrl()};
			Map<String,Object> studentMap = dataProcess.getMapByStringArray(studentKey, studentValue);
			allStudentMap.put(""+i, studentMap);
		}
		String[] headTeacherKey = {"headTeacherName", "phone"};
		String[] headTeacherValue = {headTeacher.getTeacherName(),headTeacher.getTeacherPhone()};
		Map<String,Object> headTeacherMap = dataProcess.getMapByStringArray(headTeacherKey, headTeacherValue);
		
		String[] allMapKey = {"student","headTeacher"};
		Object[] allMapValue = {allStudentMap,headTeacherMap};
		Map<String,Object> allMap = dataProcess.getMapByStringArray(allMapKey, allMapValue);
		String allMapJson = jsonAnalyze.map2Json(allMap);
		if(dataProcess.dataIsNull(allMapJson))
		{
			return returnStatus.Fail;
		}
		return allMapJson;
	}
	
	
	/*
	 * @方法名：getHeadTeacherByCollegeName(@RequestBody String requestJsonBody)
	 * @功能：根据学院名字得到辅导员信息
	 * @功能说明：根据学院名字得到辅导员信息
	 * @作者：李振强
	 * @创建时间：2014.4.5
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/emerGetHeadTeacherByCollegeName.html")
	@ResponseBody
	public String getHeadTeacherByCollegeName(@RequestBody String requestJsonBody)throws Exception
	{
		String collegeName = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "collegeName");
		if(dataProcess.dataIsNull(collegeName))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<Teacher> teacherList = tryCatchTeacherService.getHeadTeacherListByCollege(collegeName);
		if(dataProcess.dataIsNull(teacherList)||dataProcess.listHasNull(teacherList))
		{
			return returnStatus.Fail;
		}
		Map<String,Object> headTeacherMap = new HashMap<String,Object>();
		final int teacherListSize = teacherList.size();
		for(int i = 0; i < teacherListSize; i++)
		{
			Teacher teacher = teacherList.get(i);
			String[] teacherMapKey = {"headTeacherName","phone"};
			String[] teacherMapValue = {teacher.getTeacherName(),teacher.getTeacherPhone()};
			Map<String,Object> teacherMap = dataProcess.getMapByStringArray(teacherMapKey, teacherMapValue);
			headTeacherMap.put(""+i, teacherMap);
		}
		Map<String,Object> headTeacherMapKey= new HashMap<String,Object>();
		headTeacherMapKey.put("headTeacher", headTeacherMap);
		String headTeacherJson = jsonAnalyze.map2Json(headTeacherMapKey);
		if(dataProcess.dataIsNull(headTeacherJson))
		{
			return returnStatus.Fail;
		}
		return headTeacherJson;
	}
	
	
	/*
	 * @方法名：emerGetLeader()
	 * @功能：显示所有的领导联系方式
	 * @功能说明：显示所有的领导联系方式
	 * @作者：李振强
	 * @创建时间：2014.4.5
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/emerGetLeader.html")
	@ResponseBody
	public String emerGetLeader()throws Exception
	{
		List<Leader> leaderList = tryCatchLeaderService.getAllLeader();
		if(dataProcess.dataIsNull(leaderList))
		{
			return returnStatus.Fail;
		}
		Map<String,Object> allLeaderMap = new HashMap<String,Object>();
		final int leaderListSize = leaderList.size();
		for(int i = 0; i < leaderListSize; i++)
		{
			String[] leaderKey = {"leaderName","phone"};
			String[] leaderValue = {leaderList.get(i).getName(),leaderList.get(i).getPhone()};		
			Map<String,Object> leaderMap = dataProcess.getMapByStringArray(leaderKey, leaderValue);
			allLeaderMap.put(""+i, leaderMap);
		}
		String allMapJson = jsonAnalyze.map2Json(allLeaderMap);
		if(dataProcess.dataIsNull(allMapJson))
		{
			return returnStatus.Fail;
		}
		return allMapJson;
	}	
	
	/*
	 * @方法名：getLongLatType
	 * @功能：客户端获取预警类型
	 * @创建时间：2015.11.18
	 */
	@RequestMapping(value="/getLongLatType.html")
	@ResponseBody
	public String getLongLatType()throws Exception 
	{
		List<LongLatType> typeList = tryCatchLongLatService.getAllLatTypes();
		if(dataProcess.dataIsNull(typeList)||dataProcess.listHasNull(typeList))
		{
			return returnStatus.Fail;
		}
		Map<String,Object> allTypeMap = new HashMap<String,Object>();
		final int typeListSize = typeList.size();
		for(int i = 0; i < typeListSize; i++)
		{
			String[] leaderKey = {"typeList"};
			String[] leaderValue = {typeList.get(i).getLonglattype()};		
			Map<String,Object> typeMap = dataProcess.getMapByStringArray(leaderKey, leaderValue);
			allTypeMap.put(""+i, typeMap);
		}
		String allMapJson = jsonAnalyze.map2Json(allTypeMap);
		if(dataProcess.dataIsNull(allMapJson))
		{
			return returnStatus.Fail;
		}
		return allMapJson;
	}
	
	/*
	 * @方法名：webgetLongLatType
	 * @功能：服务端获取预警类型
	 * @创建时间：2015.11.18
	 */
	@RequestMapping(value="/webgetLongLatType.html")
	@ResponseBody
	public String webgetLongLatType()throws Exception 
	{
		List<LongLatType> typeList = tryCatchLongLatService.getAllLatTypes();
		if(dataProcess.dataIsNull(typeList)||dataProcess.listHasNull(typeList))
		{
			return returnStatus.Fail;
		}
		return jsonAnalyze.list2Json(typeList);
	}
}

