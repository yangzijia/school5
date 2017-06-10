/*
*@包名：com.b505.web        
*@文档名：HeadteacherChangeInformationController.java 
*@功能：辅导员修改个人信息      
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
import com.b505.bean.util.GradeUtil;
import com.b505.json.JsonAnalyze;
import com.b505.service.IGradeService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.Regex;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
//import com.b505.tools.SSHA;
import com.b505.tools.DigestUtils;
import com.b505.tools.SessionGet;
import com.b505.tools.StatusMap;
import com.b505.tools.TryCatchGradeService;
import com.b505.tools.TryCatchTeacherService;
import com.b505.tools.TryCatchUserService;

@Controller
public class HeadteacherChangeInformationController 
{
	@Autowired
	private Regex regex;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private TryCatchTeacherService tryCatchTeacherService;
	@Autowired
	private TryCatchGradeService tryCatchGradeService;
	@Autowired
	private ReturnObjectByAttribute returnObjectByAttribute;
	@Autowired
	//private SSHA sSHA;
	private DigestUtils du;
	@Autowired
	private IGradeService gradeService;
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private SessionGet sessionGet;
	
	/*
	 * @方法名：headteacherChange(@RequestBody String requestJsonBody)
	 * @功能：辅导员修改个人信息
	 * @功能说明：辅导员修改个人信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/headTeacherChange.html")
	@ResponseBody
	public String headteacherChange(@RequestBody String requestJsonBody)throws Exception
	{
		String[] userNicknameKey = {"userNickname","role"};
		Object[] userNicknameValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, userNicknameKey);
		if(dataProcess.dataIsNull(userNicknameKey)||dataProcess.arrayHasNull(userNicknameKey))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!"Role_HeadTeacher".equals(userNicknameValue[1]))
		{
			return returnStatus.NotHaveUser;
		}
		String[] passwordKey = {"oldPassword", "newPassword"};
		Object[] passwordValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, passwordKey);
		
		String[] phoneKey = {"phone"};
		Object[] phoneValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, phoneKey);
		
		if(dataProcess.dataIsNull(passwordValue)||dataProcess.arrayHasNull(passwordValue))
		{
			if(dataProcess.dataIsNull(phoneValue)||dataProcess.arrayHasNull(phoneValue))
			{
				return returnStatus.CannotAnalyzeData;
			}
			if(!regex.phoneRegex((String)phoneValue[0]))
			{
				return returnStatus.PhoneError;
			}
			if(!tryCatchTeacherService.updatePhoneByNickName((String)phoneValue[0], (String)userNicknameValue[0]))
			{
				return returnStatus.Fail;
			}
		}
		else
		{
			if(!du.digest((String)passwordValue[0]).equals(tryCatchUserService.getUserByNickname((String)userNicknameValue[0]).getPassword()))
			{
				return returnStatus.OldPasswordError;
			}
			if(!tryCatchUserService.updatePasswordByNickName(du.digest((String)passwordValue[1]), (String)userNicknameValue[0]))
			{
				return returnStatus.Fail;
			}
			if(!dataProcess.dataIsNull(phoneValue)&&!dataProcess.arrayHasNull(phoneValue))
			{
				if(!regex.phoneRegex((String)phoneValue[0]))
				{
					return returnStatus.PhoneError;
				}
				if(!tryCatchTeacherService.updatePhoneByNickName((String)phoneValue[0], (String)userNicknameValue[0]))
				{
					return returnStatus.Fail;
				}
			}
		}
		@SuppressWarnings("unchecked")
		List<Object> gradeID = (List<Object>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "gradeID");
		if(!dataProcess.dataIsNull(gradeID)&&!dataProcess.listHasNull(gradeID))
		{
			Teacher headTeacher = tryCatchTeacherService.getTeacherByNickname((String)userNicknameValue[0]);
			if(dataProcess.dataIsNull(headTeacher))
			{
				return returnStatus.Fail;
			}
			List<Grade> newsGrade = new ArrayList<Grade>();
			final int gradeIDArrayLength = gradeID.size();
			for(int i = 0; i < gradeIDArrayLength; i++)
			{
				Integer index = Integer.parseInt((String)gradeID.get(i));
				Grade grade = tryCatchGradeService.getGradeByGradeID(index);
				newsGrade.add(grade);
				headTeacher.setCollege(grade.getCollege());
			}
			headTeacher.setTeacherClass(newsGrade);
			if(!tryCatchTeacherService.update(headTeacher))
			{
				return returnStatus.Fail;
			}
		}
		return returnStatus.Success;
	}
	
	
	/*
	 * @方法名：headteacherShow(@RequestBody String requestJsonBody)
	 * @功能：显示辅导员的个人信息
	 * @功能说明：显示辅导员的个人信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/headteacherShow.html")
	@ResponseBody
	public String headteacherShow(@RequestBody String requestJsonBody)throws Exception
	{
		String teacherNickname = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "userNickname");
		if(dataProcess.dataIsNull(teacherNickname))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Teacher teacher = tryCatchTeacherService.getTeacherByNickname(teacherNickname);
		if(dataProcess.dataIsNull(teacher))
		{
			return returnStatus.NotHaveUser;
		}
		String[] teacherKey = {"name", "phone", "cardId", "college", "gradeList"};
		Object[] teacherValue = {teacher.getTeacherName(), teacher.getTeacherPhone(), teacher.getTeacherCardId(), teacher.getCollege().getCollegeName(), teacher.getTeacherClass()};
		Map<String,Object> teacherMap = dataProcess.getMapByStringArray(teacherKey, teacherValue);
		if(dataProcess.dataIsNull(teacherMap))
		{
			return returnStatus.Fail;
		}
		String json = jsonAnalyze.map2Json(teacherMap);
		return json;
	}
	
	//web端显示页面
	@RequestMapping(value = "/headteacherWebShow.html")
	@ResponseBody
	public String headteacherShow(HttpServletRequest request)throws Exception
	{
		String teacherNickname=sessionGet.getUserInfo().getUsername();
		/*LoginUser user = (LoginUser) request.getSession().getAttribute("user");
		String teacherNickname = user.getNickName();*/
		
		if(dataProcess.dataIsNull(teacherNickname))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Teacher teacher = tryCatchTeacherService.getTeacherByNickname(teacherNickname);
		if(dataProcess.dataIsNull(teacher))
		{
			return returnStatus.NotHaveUser;
		}
		String[] teacherKey = {"name", "phone", "cardId", "college", "gradeList"};
		Object[] teacherValue = {teacher.getTeacherName(), teacher.getTeacherPhone(), teacher.getTeacherCardId(), teacher.getCollege().getCollegeName(),teacher.getTeacherClass()};

		
		Map<String,Object> teacherMap = dataProcess.getMapByStringArray(teacherKey, teacherValue);
		if(dataProcess.dataIsNull(teacherMap))
		{
			return returnStatus.Fail;
		}
		return jsonAnalyze.map2Json(teacherMap);
	}
	
	
	
	@RequestMapping(value="/headteacherChangeclass.html")
	public String headteacherChangeclass()
	{	
		//跳转到辅导员修改班级页面
		return "headteacherchangeclass";
	}
	
	//通过辅导员昵称得到其管理的班级信息
	@RequestMapping(value="/getGradeutilByNickName.html")
	@ResponseBody
	public String getGradeUtilsByHeadTeacherNickName(@RequestBody String requestJsonBody) throws IOException{
		String Fail = statusMap.status("Fail");
		String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
		String string = null;
		Map<String,Object> userMap = new HashMap<String,Object>();
		userMap = jsonAnalyze.json2Map(requestJsonBody);
		String nickName = (String)userMap.get("nickName");
					
		if(nickName==null||"".equals(nickName))
		{
			return cannotAnalyzeData;
		}
		List<GradeUtil> grade = new ArrayList<GradeUtil>();
		try
		{
			grade = gradeService.getGradeUtilsByHeadTeacherNickName(nickName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("RequestCollegeController_getTeacherGrade_gradeService.getGradeUtilsByHeadTeacherNickName(nickName);出错");
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
	
	
	
	
	@RequestMapping(value = "/headteacherChangeClass.html")
	@ResponseBody
	public String updateGradeUtilsByHeadTeacherNickName(@RequestBody String requestJsonBody)throws Exception
	{	
		@SuppressWarnings("unchecked")
		List<GradeUtil> grade = (List<GradeUtil>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "grade");
		
		if(dataProcess.dataIsNull(grade)||dataProcess.listHasNull(grade))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchGradeService.updateGradeUtilsByHeadTeacherNickName(grade))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;	
	}
}

