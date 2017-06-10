/*
*@包名：com.b505.web        
*@文档名：AddUserController.java
*@功能：增加用户（学生、辅导员、领导、普通教师）      
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

import com.b505.bean.Leader;
import com.b505.bean.LoginUser;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.Regex;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchLeaderService;
import com.b505.tools.TryCatchUserService;

@Controller
public class AddUserController 
{
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
	private TryCatchLeaderService tryCatchLeaderService;
	
	/*
	 * @方法名：addStudent()
	 * @功能：添加学生
	 * @功能说明：直接跳转到学生注册页面进行添加
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/addStudent.html")
	@ResponseBody
	public String addStudent()
	{	
		//跳转到学生注册页面
		return "studentRegister";
	}
	
	/*
	 * @方法名：addHeadteacher()
	 * @功能：添加辅导员
	 * @功能说明：直接跳转到辅导员注册页面进行添加
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/addHeadteacher.html")
	@ResponseBody
	public String addHeadteacher()
	{
		return "webHeadteacherRegister";
	}

	/*
	 * @方法名：addLeader()
	 * @功能：添加领导
	 * @功能说明：直接跳转到添加领导页面进行添加，之后跳转到saveLeader(@RequestBody String requestJsonBody)进行信息保存
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/addLeader.html")
	@ResponseBody
	public String addLeader()
	{
		return "addLeader";
	}
	
	/*
	 * @方法名：saveLeader(@RequestBody String requestJsonBody)
	 * @功能：保存领导信息
	 * @功能说明：保存领导信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.16
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/saveLeader.html")
	@ResponseBody
	public String saveLeader(@RequestBody String requestJsonBody) throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> allLeaderList = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(allLeaderList)||dataProcess.listHasNull(allLeaderList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<Leader> leaderList = new ArrayList<Leader>();
		List<LoginUser> userList = new ArrayList<LoginUser>();
		final int listSize = allLeaderList.size();
		for(int i = 0; i < listSize; i++)
		{
			String[] leaderMessageKey = {"nickName","name","phone"};
			Object[] leaderMessageValue = dataProcess.getMapValueByKey(allLeaderList.get(i), leaderMessageKey);
			if(dataProcess.dataIsNull(leaderMessageValue)||dataProcess.arrayHasNull(leaderMessageValue))
			{
				return returnStatus.CannotAnalyzeData;
			}
			if(!regex.nicknameRegex((String)leaderMessageValue[0])||!regex.nicknameRegex((String)leaderMessageValue[1]))
			{
				return returnStatus.NicknameCodedError;
			}
			if(!regex.phoneRegex((String)leaderMessageValue[2]))
			{
				return returnStatus.PhoneError;
			}
			LoginUser user = returnObjectByAttribute.setLeaderOfUser((String)leaderMessageValue[0]);
			//System.out.println("user----->"+user);
			if(dataProcess.dataIsNull(user))
			{
				return returnStatus.Fail;
			}
			Leader leader = returnObjectByAttribute.returnLeader((String)leaderMessageValue[1], (String)leaderMessageValue[2], user);
			//System.out.println("leader---->"+leader);
			leaderList.add(leader);
			userList.add(user);
		}
		
		if(!tryCatchUserService.saveUserByBatch(userList))
		{
			return returnStatus.Fail;
		}
		if(!tryCatchLeaderService.saveLeaderByBatch(leaderList))
		{
			tryCatchUserService.deletedUserByBatch(userList);
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}

	/*
	 * @方法名：addTeacher()
	 * @功能：添加普通教师
	 * @功能说明：跳转到普通教师注册页面进行添加
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/addTeacher.html")
	@ResponseBody
	public String addTeacher() throws Exception
	{
		return "webteacherregister";
	}
}

