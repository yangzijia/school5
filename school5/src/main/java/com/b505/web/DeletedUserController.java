/*
*@包名：com.b505.web        
*@文档名：DeletedUserController.java
*@功能：删除用户（学生、辅导员、领导、普通教师）      
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

import com.b505.bean.Student;
import com.b505.bean.LoginUser;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchLeaderService;
import com.b505.tools.TryCatchStudentService;
import com.b505.tools.TryCatchTeacherService;
import com.b505.tools.TryCatchUserService;

@Controller
public class DeletedUserController 
{
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private TryCatchTeacherService tryCatchTeacherService;
	@Autowired
	private TryCatchLeaderService tryCatchLeaderService;
	@Autowired
	private ReturnObjectByAttribute returnObjectByAttribute;
	
	/*
	 * @方法名：deletedStudent(@RequestBody String requestJsonBody)
	 * @功能：删除学生
	 * @功能说明：删除学生
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.16
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/delStudent.html")
	@ResponseBody
	public String deletedStudent(@RequestBody String requestJsonBody)throws Exception
	{
		String[] requestStudentKey = {"userNickname","studentID"};
		Object[] responseStudentValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestStudentKey);
		if(dataProcess.dataIsNull(responseStudentValue)||dataProcess.arrayHasNull(responseStudentValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Student student = returnObjectByAttribute.returnStudent((String)responseStudentValue[1]);
		if(!tryCatchStudentService.deletedStudent(student))
		{
			return returnStatus.Fail;
		}
		LoginUser user = returnObjectByAttribute.returnUser((String)responseStudentValue[0]);
		if(!tryCatchUserService.deletedUser(user))
		{
			if(!tryCatchStudentService.saveStudent(student))
			{
				return returnStatus.Fail;
			}
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}

	
	/*
	 * @方法名：deletedTeacher(@RequestBody String requestJsonBody)
	 * @功能：删除教师
	 * @功能说明：删除教师
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.16
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/delTeacher.html")
	@ResponseBody
	public String deletedTeacher(@RequestBody String requestJsonBody) throws Exception
	{	
		System.out.println("requestJsonBody"+requestJsonBody);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> responseTeacherValue = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(responseTeacherValue)||dataProcess.listHasNull(responseTeacherValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<Integer> teacherIDList = new ArrayList<Integer>();
		List<LoginUser> userList = new ArrayList<LoginUser>();
		final int responseTeacherValueSize = responseTeacherValue.size();
		for(int i =0; i < responseTeacherValueSize; i++)
		{
			String[] teacherKey = {"userNickname","id"};
			Map<String,Object> teacherMap = responseTeacherValue.get(i);
			Object[] teacherValue = dataProcess.getMapValueByKey(teacherMap, teacherKey);
			teacherIDList.add((Integer)teacherValue[1]);
			LoginUser user = returnObjectByAttribute.returnUser((String)teacherValue[0]);
			System.out.println(user);
			userList.add(user);
		}
		if(!tryCatchTeacherService.deleteTeacherByBatch(teacherIDList))
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
	 * @方法名：deletedLeader(@RequestBody String requestJsonBody)
	 * @功能：删除领导
	 * @功能说明：删除领导
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.16
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/delLeader.html")
	@ResponseBody
	public String deletedLeader(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> responseLeaderValue = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(responseLeaderValue)||dataProcess.listHasNull(responseLeaderValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<Integer> idList = new ArrayList<Integer>();
		List<LoginUser> nicknameList = new ArrayList<LoginUser>();
		final int  leaderListSize = responseLeaderValue.size();
		for(int i = 0; i < leaderListSize; i++)
		{
			String[] leaderKey = {"nickName","id"};
			Object[] leaderValue = dataProcess.getMapValueByKey(responseLeaderValue.get(i), leaderKey);
			idList.add((Integer)leaderValue[1]);

			LoginUser user = new LoginUser();
			user.setNickName((String)leaderValue[0]);
			nicknameList.add(user);
		}
		if(!tryCatchLeaderService.deleteLeaderByBatch(idList))
		{
			return returnStatus.Fail;
		}
		if(!tryCatchUserService.deletedUserByBatch(nicknameList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
}

