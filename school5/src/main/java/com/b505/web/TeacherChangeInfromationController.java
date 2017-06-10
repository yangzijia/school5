/*
*@包名：com.b505.web        
*@文档名：TeacherChangeInfromationController.java 
*@功能：普通教师修改个人信息
*@作者：李振强     
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Teacher;
import com.b505.json.JsonAnalyze;
import com.b505.service.ITeacherService;
import com.b505.service.ILoginUserService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.Regex;
import com.b505.tools.ReturnStatus;
//import com.b505.tools.SSHA;
import com.b505.tools.DigestUtils;
import com.b505.tools.SessionGet;
import com.b505.tools.TryCatchLeaderService;
import com.b505.tools.TryCatchTeacherService;
import com.b505.tools.TryCatchUserService;

@Controller
public class TeacherChangeInfromationController 
{
	@Autowired
	private ILoginUserService userService;
	@Autowired
	private ITeacherService teacherService;
	@Autowired
	private Regex regex;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	//private SSHA sSHA;
	private DigestUtils du;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private TryCatchTeacherService tryCatchTeacherService;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchLeaderService tryCatchLeaderService;
	@Autowired
	private SessionGet sessionGet;
	
	/*
	 * @方法名：teacherChange(@RequestBody String requestJsonBody)
	 * @功能：普通教师修改个人信息
	 * @功能说明：普通教师修改个人信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value = "/teacherChange.html")
	@ResponseBody
	public String teacherChange(@RequestBody String requestJsonBody)throws Exception
	{
		String[] userNicknameKey = {"userNickname", "role"};
		Object[] userNicknameValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, userNicknameKey);
		if(dataProcess.dataIsNull(userNicknameValue)||dataProcess.arrayHasNull(userNicknameValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!"Role_Teacher".equals(userNicknameValue[1]))
		{
			return returnStatus.NotHaveUser;
		}
		String[] passwordKey = {"oldPassword", "newPassword"};
		Object[] passwordValue  = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, passwordKey);
		
		String[] phoneKey = {"phone"};
		Object[] phoneValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, phoneKey);
		System.out.println("phoneValue :" + phoneValue[0]);

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
				System.out.println("!tryCatchTeacherService.updatePhoneByNickName((String)phoneValue[0], (String)userNicknameValue[0]) 1 ");
				return returnStatus.Fail;
			}
		}
		else
		{	
			int b = tryCatchUserService.hasMatchUser((String)userNicknameValue[0], du.digest((String)passwordValue[0]),(String)userNicknameValue[1]);
			//System.out.println(b);
			if(b != 1){
				return returnStatus.OldPasswordError;
			}
			if(!tryCatchUserService.updatePasswordByNickName(du.digest((String)passwordValue[1]), (String)userNicknameValue[0]))
			{
				//System.out.println(" !tryCatchUserService.updatePasswordByNickName(sSHA.digest((String)passwordValue[1]), (String)userNicknameValue[0]) 2 ");
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
					//System.out.println(" !tryCatchTeacherService.updatePhoneByNickName((String)phoneValue[0], (String)userNicknameValue[0]) 3 ");
					return returnStatus.Fail;
				}
			}
		}
		return returnStatus.Success;
	}
	
	
	/*
	 * @方法名：teacherShow(@RequestBody String requestJsonBody)
	 * @功能：普通教师查看个人信息
	 * @功能说明：普通教师查看个人信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value = "/teacherShow.html")
	@ResponseBody
	public String teacherShow(@RequestBody String requestJsonBody)throws Exception
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
		String[] teacherKey = {"name", "phone", "cardId"};
		String[] teacherValue = {teacher.getTeacherName(), teacher.getTeacherPhone(), teacher.getTeacherCardId()};
		Map<String,Object> teacherMap = dataProcess.getMapByStringArray(teacherKey, teacherValue);
		if(dataProcess.dataIsNull(teacherMap))
		{
			return returnStatus.Fail;
		}
		return jsonAnalyze.map2Json(teacherMap);
	}
	
	
	//web端显示老师信息
	@RequestMapping(value = "/teacherWebShow.html")
	@ResponseBody
	public String teacherWebShow(HttpServletRequest request)throws Exception
	{
		String teacherNickname=sessionGet.getUserInfo().getUsername();
		//HttpSession hp = request.getSession();
		//String teacherNickname = (String)hp.getAttribute("userName");
		if(dataProcess.dataIsNull(teacherNickname))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Teacher teacher = tryCatchTeacherService.getTeacherByNickname(teacherNickname);
		if(dataProcess.dataIsNull(teacher))
		{
			return returnStatus.NotHaveUser;
		}
		String[] teacherKey = {"name", "phone", "cardId"};
		String[] teacherValue = {teacher.getTeacherName(), teacher.getTeacherPhone(), teacher.getTeacherCardId()};
		Map<String,Object> teacherMap = dataProcess.getMapByStringArray(teacherKey, teacherValue);
		if(dataProcess.dataIsNull(teacherMap))
		{
			return returnStatus.Fail;
		}
		return jsonAnalyze.map2Json(teacherMap);
	}
}

