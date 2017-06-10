package com.b505.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Administrator;
import com.b505.bean.LoginUser;
import com.b505.json.JsonAnalyze;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.DigestUtils;
import com.b505.tools.Regex;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
import com.b505.tools.SSHA;
import com.b505.tools.SessionGet;
import com.b505.tools.TryCatchAdminService;
import com.b505.tools.TryCatchUserService;

@Controller
public class AdminiInformationController 
{
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private TryCatchAdminService tryCatchAdminService;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private ReturnObjectByAttribute returnObjectByAttribute;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private Regex regex;
	@Autowired
	private SSHA sSHA;
	@Autowired
	private SessionGet sessionGet;
	@Autowired
	private DigestUtils du;
	
	/*
	 * @方法名：addAdmin(@RequestBody String requestJsonBody)
	 * @功能：添加管理员
	 * @功能说明：添加管理员
	 * @作者：李振强
	 * @创建时间：2014.5.31
	 * @修改时间：2014.5.31
	 */
	@RequestMapping(value="/addAdmin.html")
	@ResponseBody
	public String addAdmin(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> adminInforList = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(adminInforList)||dataProcess.listHasNull(adminInforList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<LoginUser> userList = new ArrayList<LoginUser>();
		List<Administrator> adminList = new ArrayList<Administrator>();
		final int adminInforListSize = adminInforList.size();
		for(int i = 0; i < adminInforListSize; i++)
		{
			Map<String,Object> adminMap = adminInforList.get(i);		
			String[] userNicknameKey = {"nickName"};
			Object[] userNickname = dataProcess.getMapValueByKey(adminMap, userNicknameKey);
			LoginUser user = returnObjectByAttribute.returnUser((String)userNickname[0], "12345678","Role_Leader");
			userList.add(user);			
			String[] adminKey = {"name", "phone"};
			Object[] adminValue = (Object[])dataProcess.getMapValueByKey(adminMap, adminKey);
			Administrator admin = returnObjectByAttribute.returnAdmin((String)adminValue[0], (String)adminValue[1], user);
			adminList.add(admin);
		}
		if(!tryCatchUserService.saveUserByBatch(userList))
		{
			return returnStatus.Fail;
		}
		if(!tryCatchAdminService.saveAdministratorBayBatch(adminList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	/*
	 * @方法名：delAdmin(@RequestBody String requestJsonBody)
	 * @功能：删除管理员
	 * @功能说明：删除管理员
	 * @作者：李振强
	 * @创建时间：2014.5.31
	 * @修改时间：2014.5.31
	 */
	@RequestMapping(value="/delAdmin.html")
	@ResponseBody
	public String delAdmin(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> adminInforList = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(adminInforList)||dataProcess.listHasNull(adminInforList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<LoginUser> userList = new ArrayList<LoginUser>();
		List<Integer> adminIDList = new ArrayList<Integer>();
		final int adminInforListSize = adminInforList.size();
		for(int i = 0; i < adminInforListSize; i++)
		{
			Map<String,Object> adminMap = adminInforList.get(i);			
			String[] adminIDKey = {"id"};
			Object[] adminIDValue = dataProcess.getMapValueByKey(adminMap, adminIDKey);
			adminIDList.add((Integer)adminIDValue[0]);			
			String[] userNicknameKey = {"nickName"};
			Object[] userNicknameValue = dataProcess.getMapValueByKey(adminMap, userNicknameKey);
			LoginUser user = returnObjectByAttribute.returnUser((String)userNicknameValue[0]);
			userList.add(user);
		}
		if(!tryCatchAdminService.deleteAdministratorByBatch(adminIDList))
		{
			return returnStatus.Fail;
		}
		if(!tryCatchUserService.deletedUserByBatch(userList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	
	/*
	 * @方法名：changeAdmin(@RequestBody String requestJsonBody)
	 * @功能：更改管理员
	 * @功能说明：更改管理员
	 * @作者：李振强
	 * @创建时间：2014.5.31
	 * @修改时间：2014.5.31
	 */
	@RequestMapping(value="/changeAdmin.html")
	@ResponseBody
	public String changeAdmin(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> adminList = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> adminInforList = new ArrayList<Map<String,Object>>();
		final int adminListSize = adminList.size();
		for(int i = 0; i < adminListSize; i++)
		{
			Map<String,Object> adminMap = adminList.get(i);			
			userList.add(adminMap);
			adminInforList.add(adminMap);
		}
		if(!tryCatchAdminService.updataAdminByBench(adminInforList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	//Web端显示管理员个人信息
	@RequestMapping(value="/showWebAdmin.html")
	@ResponseBody
	public String getAdminInformation(HttpServletRequest request) throws Exception{
		
		String adminName=sessionGet.getUserInfo().getUsername();
		//HttpSession  hp = request.getSession();
		//String adminName =(String) hp.getAttribute("userName");
		Administrator administrator = tryCatchAdminService.getAdministrator(adminName);
		if(dataProcess.dataIsNull(adminName))
		{
			return returnStatus.CannotAnalyzeData;
		}
		String[] adminKey = {"name", "phone"};
		Object[] adminValue = {administrator.getName(), administrator.getPhone()};
		Map<String,Object> adminMap = dataProcess.getMapByStringArray(adminKey, adminValue);
		if(dataProcess.dataIsNull(adminMap))
		{
			return returnStatus.Fail;
		}
		return jsonAnalyze.map2Json(adminMap);
	}
	@RequestMapping(value="/administratorChange.html")
	@ResponseBody
	public String administratorChange(@RequestBody String requestJsonBody)throws Exception
	{
		String[] userNicknameKey = {"userNickname", "role"};
		Object[] userNicknameValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, userNicknameKey);
		if(dataProcess.dataIsNull(userNicknameValue)||dataProcess.arrayHasNull(userNicknameValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!"Role_Administrator".equals(userNicknameValue[1]))
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
			if(!tryCatchAdminService.updatePhoneByNickName((String)phoneValue[0], (String)userNicknameValue[0]))
			{
				return returnStatus.Fail;
			}
		}
		else
		{	
			System.out.println((String)passwordValue[0]);
			System.out.println(du.digest((String)passwordValue[0]));
			int b = tryCatchUserService.hasMatchUser((String)userNicknameValue[0], du.digest((String)passwordValue[0]),(String)userNicknameValue[1]);
			System.out.println(b);
			if(b != 1){
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
				if(!tryCatchAdminService.updatePhoneByNickName((String)phoneValue[0], (String)userNicknameValue[0]))
				{
					System.out.println(" !tryCatchTeacherService.updatePhoneByNickName((String)phoneValue[0], (String)userNicknameValue[0]) 3 ");
					return returnStatus.Fail;
				}
			}
		}
		return returnStatus.Success;
	}
}

