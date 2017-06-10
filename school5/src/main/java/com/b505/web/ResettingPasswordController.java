package com.b505.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchResettingPassword;

@Controller
public class ResettingPasswordController
{
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private TryCatchResettingPassword tryCatchResettingPassword;
	@Autowired
	private AnalyzeData analyzeData;
	
	/*
	 * @方法名：resettingPassword
	 * @功能：重置密码
	 * @功能说明：重置密码
	 * @作者：李振强
	 * @创建时间：2014.5.22
	 * @修改时间：2014.5.22
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/resettingPassword.html")
	@ResponseBody
	public String resettingPassword(@RequestBody String requestJsonBody)throws Exception
	{	
		
		System.out.println("requestJsonBody"+requestJsonBody);
		@SuppressWarnings("unchecked")
		List<String> nicknameList = (List<String>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		System.out.println("nicknameList====>"+nicknameList);
		if(dataProcess.dataIsNull(nicknameList)||dataProcess.listHasNull(nicknameList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchResettingPassword.passwordRecBynickName(nicknameList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
}

