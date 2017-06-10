package com.b505.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Leader;
import com.b505.json.JsonAnalyze;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.Regex;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
//import com.b505.tools.SSHA;
import com.b505.tools.DigestUtils;
import com.b505.tools.SessionGet;
import com.b505.tools.TryCatchLeaderService;
import com.b505.tools.TryCatchUserService;

@Controller
public class LeaderChangeInformationController 
{
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private TryCatchLeaderService tryCatchLeaderService;
	@Autowired
	private ReturnObjectByAttribute returnObjectByAttribute;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private Regex regex;
	@Autowired
	//private SSHA sSHA;
	private DigestUtils du;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private SessionGet sessionGet;
	
	/*
	 * @方法名：leaderChange(@RequestBody String requestJsonBody)
	 * @功能：领导修改个人信息
	 * @功能说明：领导修改个人信息
	 * @作者：李振强
	 * @创建时间：2014.5.31
	 * @修改时间：2014.5.31
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/leaderChange.html")
	@ResponseBody
	public String leaderChange(@RequestBody String requestJsonBody)throws Exception
	{
		//System.out.println("requestJsonBody"+requestJsonBody);
		String[] leaderInforKey = {"userNickname", "role"};
		Object[] leaderInforValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, leaderInforKey);
		if(dataProcess.dataIsNull(leaderInforValue)||dataProcess.arrayHasNull(leaderInforValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!"Role_Leader".equals(leaderInforValue[1]))
		{
			//System.out.println("Role"+leaderInforValue[1]);
			return returnStatus.NotHaveUser;
		}
		String[] passwordKey = {"oldPassword", "newPassword"};
		Object[] passwordValue  = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, passwordKey);
		String oldPassWord = (String)passwordValue[0];
		
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
			if(!tryCatchLeaderService.updatePhoneBynickName((String)phoneValue[0], (String)leaderInforValue[0]))
			{
				return returnStatus.Fail;
			}
		}
		else
		{	int b = tryCatchUserService.hasMatchUser((String)leaderInforValue[0], du.digest(oldPassWord),(String)leaderInforValue[1]);
			//System.out.println("nickName"+(String)leaderInforValue[0]);
			//System.out.println("oldpassword"+oldPassWord);
			//System.out.println("role"+(String)leaderInforValue[1]);
			//System.out.println(b);
			if(b != 1){
				return returnStatus.OldPasswordError;
			}
			if(!tryCatchLeaderService.updatePasswordByNickName(du.digest((String)passwordValue[1]), (String)leaderInforValue[0]))
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
				if(!tryCatchLeaderService.updatePhoneBynickName((String)phoneValue[0], (String)leaderInforValue[0]))
				{
					//System.out.println(" !tryCatchTeacherService.updatePhoneByNickName((String)phoneValue[0], (String)userNicknameValue[0]) 3 ");
					return returnStatus.Fail;
				}
			}
		}
		return returnStatus.Success;
	}
	
	
	/*
	 * @方法名：leaderShow(@RequestBody String requestJsonBody)
	 * @功能：领导查看个人信息
	 * @功能说明：领导查看个人信息
	 * @作者：李振强
	 * @创建时间：2014.5.31
	 * @修改时间：2014.5.31
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/leaderShow.html")
	@ResponseBody
	public String leaderShow(@RequestBody String requestJsonBody)throws Exception
	{
		String leaderNickname = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "userNickname");
		if(dataProcess.dataIsNull(leaderNickname))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Leader leader = tryCatchLeaderService.getLeaderByNickname(leaderNickname);
		if(dataProcess.dataIsNull(leader))
		{
			return returnStatus.Fail;
		}
		String[] leaderInforKey = {"name", "phone"};
		String[] leaderInforValue = {leader.getName(), leader.getPhone()};
		Map<String,Object> leaderMap = dataProcess.getMapByStringArray(leaderInforKey, leaderInforValue);
		if(dataProcess.dataIsNull(leaderMap))
		{
			return returnStatus.Fail;
		}
		return jsonAnalyze.map2Json(leaderMap);
	}
	//领导端显示信息
	@RequestMapping(value = "/leaderWebShow.html")
	@ResponseBody
	public String leaderWebShow(HttpServletRequest request)throws Exception
	{
		String leaderNickname=sessionGet.getUserInfo().getUsername();
		//HttpSession hp = request.getSession();
		//String leaderNickname = (String)hp.getAttribute("userName");
		if(dataProcess.dataIsNull(leaderNickname))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Leader leader = tryCatchLeaderService.getLeaderByNickname(leaderNickname);
		if(dataProcess.dataIsNull(leader))
		{
			return returnStatus.Fail;
		}
		String[] leaderInforKey = {"name", "phone"};
		String[] leaderInforValue = {leader.getName(), leader.getPhone()};
		Map<String,Object> leaderMap = dataProcess.getMapByStringArray(leaderInforKey, leaderInforValue);
		if(dataProcess.dataIsNull(leaderMap))
		{
			return returnStatus.Fail;
		}
		return jsonAnalyze.map2Json(leaderMap);
	}
}

