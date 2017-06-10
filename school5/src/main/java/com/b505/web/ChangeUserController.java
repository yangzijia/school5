/*
*@包名：com.b505.web        
*@文档名：ChangeUserController.java
*@功能：修改用户（学生、辅导员、领导、普通教师）      
*@作者：李振强    
*@创建时间：2014.4.20
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.College;
import com.b505.bean.Grade;
import com.b505.bean.Student;
import com.b505.bean.Teacher;
import com.b505.bean.LoginUser;
import com.b505.json.JsonAnalyze;
import com.b505.service.IGradeService;
import com.b505.service.ILeaderService;
import com.b505.service.IStudentService;
import com.b505.service.ITeacherService;
import com.b505.service.ILoginUserService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.Regex;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchGradeService;
import com.b505.tools.TryCatchLeaderService;
import com.b505.tools.TryCatchStudentService;
import com.b505.tools.TryCatchTeacherService;
import com.b505.tools.TryCatchUserService;

@Controller
public class ChangeUserController 
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private ILoginUserService userService;
	@Autowired
	private ILeaderService leaderService;
	@Autowired
	private IStudentService studentService;
	@Autowired
	private ITeacherService teacherService;
	@Autowired
	private IGradeService gradeService;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private Regex regex;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private ReturnObjectByAttribute returnObjectByAttribute;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private TryCatchTeacherService tryCatchTeacherService;
	@Autowired
	private TryCatchLeaderService tryCatchLeaderService;
	@Autowired
	private TryCatchGradeService tryCatchGradeService;
	
	/*
	 * @方法名：changeStudent(@RequestBody String requestJsonBody)
	 * @功能：修改学生信息
	 * @功能说明：修改学生信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.16
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/changeStudent.html")
	@ResponseBody
	public String changeStudent(@RequestBody String requestJsonBody)throws Exception
	{
		String[] requestUserMessage = {"userNickname","password","studentName","studentPhone","studentCardID","collegeName","gradeName","profession","classID"};
		Object[] responseUserMessage = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestUserMessage);
		if(dataProcess.dataIsNull(responseUserMessage)||dataProcess.arrayHasNull(responseUserMessage))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!regex.nicknameRegex((String)responseUserMessage[0])||!regex.nameRegex((String)responseUserMessage[2]))
		{
			return returnStatus.NicknameCodedError;
		}
		if(!regex.cardIDRegex(requestUserMessage[4]))
		{
			return returnStatus.CardIDError;
		}
		if(!regex.phoneRegex(requestUserMessage[3]))
		{
			return returnStatus.PhoneError;
		}
		LoginUser user = returnObjectByAttribute.returnUser(requestUserMessage[0], requestUserMessage[1]);
		College	college = returnObjectByAttribute.returnCollege(requestUserMessage[5]);
		Grade grade = returnObjectByAttribute.returnGrade(requestUserMessage[6], requestUserMessage[7], requestUserMessage[8], college);
		Student student = returnObjectByAttribute.returnStudent(requestUserMessage[2], requestUserMessage[3], requestUserMessage[4],college,grade);
		if(!tryCatchUserService.update(user))
		{
			return returnStatus.Fail;
		}
		if(!tryCatchStudentService.update(student))
		{
			tryCatchUserService.deletedUser(user);
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}


	/*
	 * @方法名：changeHeadteacher(@RequestBody String requestJsonBody)
	 * @功能：修改辅导员信息
	 * @功能说明：修改辅导员信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.16
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/changeHeadteacher.html")
	@ResponseBody
	public String changeHeadteacher(@RequestBody String requestJsonBody)throws Exception
	{
		String[] requestUserMessage = {"userNickname","password","teacherName","teacherPhone","teacherCardID","collegeName"};
		Object[] responseUserMessage = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestUserMessage);
		if(dataProcess.dataIsNull(responseUserMessage)||dataProcess.arrayHasNull(responseUserMessage))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!regex.nicknameRegex((String)responseUserMessage[0])||!regex.nameRegex((String)responseUserMessage[2]))
		{
			return returnStatus.NicknameCodedError;
		}
		if(!regex.cardIDRegex((String)responseUserMessage[4]))
		{
			return returnStatus.CardIDError;
		}
		if(!regex.phoneRegex((String)responseUserMessage[3]))
		{
			return returnStatus.PhoneError;
		}
		@SuppressWarnings("unchecked")
		List<String> gradeIDArray = (List<String>)dataProcess.getMapValueByReceiveData(jsonAnalyze.json2Map(requestJsonBody), "gradeID");
		if(dataProcess.dataIsNull(gradeIDArray)||dataProcess.listHasNull(gradeIDArray))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<Grade> gradeList = new ArrayList<Grade>();
		final int gradeIDArraySize = gradeIDArray.size();	
		for(int i = 0; i < gradeIDArraySize; i++)
		{
			Integer gradeID = Integer.parseInt(gradeIDArray.get(i));
			Grade grade = tryCatchGradeService.getGradeByGradeID(gradeID);
			if(dataProcess.dataIsNull(grade))
			{
				return returnStatus.Fail;
			}
			gradeList.add(grade);
		}
		LoginUser user = returnObjectByAttribute.returnUser((String)responseUserMessage[0], (String)responseUserMessage[1]);
		College college = returnObjectByAttribute.returnCollege((String)responseUserMessage[5]);
		Teacher headteacher = returnObjectByAttribute.returnTeacher(null,(String)responseUserMessage[2], (String)responseUserMessage[3], (String)responseUserMessage[4], college, gradeList,user);
		if(!tryCatchUserService.update(user))
		{
			return returnStatus.Fail;
		}
		if(!tryCatchTeacherService.update(headteacher))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}


	/*
	 * @方法名：changeLeader(@RequestBody String requestJsonBody)
	 * @功能：修改领导信息
	 * @功能说明：修改领导信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.16
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/changeLeader.html")
	@ResponseBody
	public String changeLeader(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> allLeaderList = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(allLeaderList)||dataProcess.listHasNull(allLeaderList))
		{
			return returnStatus.CannotAnalyzeData;
		}
//		List<Map<String,Object>> idList = new ArrayList<Map<String,Object>>();
//		final int leaderListSize = allLeaderList.size();
//		for(int i = 0; i < leaderListSize; i++)
//		{
//			Map<String,Object> leaderMap = (Map<String,Object>)allLeaderList.get(i);
//			idList.add(leaderMap);
//		}
		if(!tryCatchLeaderService.updateLeaderByBatch(allLeaderList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	
	/*
	 * @方法名：changeTeacher(@RequestBody String requestJsonBody)
	 * @功能：修改普通教师信息
	 * @功能说明：修改普通教师信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.16
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/changeTeacher.html")
	@ResponseBody
	public String changeTeacher(@RequestBody String requestJsonBody) throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> teacherList = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(!tryCatchTeacherService.updateTeacherByBatch(teacherList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
}

