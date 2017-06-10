package com.b505.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.RoleName;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchRoleNameService;

@Controller
public class RoleController 
{
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private TryCatchRoleNameService tryCatchRoleNameService;
	
	
	/*
	 * @方法名：saveRoleName(@RequestBody String requestJsonBody)
	 * @功能：保存角色
	 * @功能说明：保存角色
	 * @作者：李振强
	 * @创建时间：2014.06.10
	 * @修改时间：2014.06.10
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/saveRoleName.html")
	@ResponseBody
	public String saveRoleName(@RequestBody String requestJsonBody) throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> roleListMap = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(roleListMap)||dataProcess.listHasNull(roleListMap))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<RoleName> roleList = new ArrayList<RoleName>();
		final int roleListMapSize = roleListMap.size();
		for(int i = 0; i < roleListMapSize; i++)
		{
			Map<String,Object> roleMap = roleListMap.get(i);
			String[] roleKey = {"roleName", "remark"};
			//String[] roleValue = (String[])dataProcess.getMapValueByKey(roleMap, roleKey);
			Object[] roleValue = dataProcess.getMapValueByKey(roleMap, roleKey);
			RoleName roleName = new RoleName();
			//roleName.setRoleName(roleValue[0]);
			//roleName.setRemark(roleValue[1]);
			roleName.setRoleName(String.valueOf(roleValue[0]));
			roleName.setRemark(String.valueOf(roleValue[1]));
			roleList.add(roleName);
		}
		if(!tryCatchRoleNameService.saveRoleNameByBatch(roleList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
 	}
	
	
	/*
	 * @方法名：changeRoleName(@RequestBody String requestJsonBody)
	 * @功能：更新角色
	 * @功能说明：更新角色
	 * @作者：李振强
	 * @创建时间：2014.06.10
	 * @修改时间：2014.06.10
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/changeRoleName.html")
	@ResponseBody
	public String changeRoleName(@RequestBody String requestJsonBody) throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> roleListMap = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(roleListMap)||dataProcess.listHasNull(roleListMap))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchRoleNameService.updateRoleNameByBatch(roleListMap))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	
	/*
	 * @方法名：delRoleName(@RequestBody String requestJsonBody)
	 * @功能：删除角色
	 * @功能说明：删除角色
	 * @作者：李振强
	 * @创建时间：2014.06.10
	 * @修改时间：2014.06.10
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/delRoleName.html")
	@ResponseBody
	public String delRoleName(@RequestBody String requestJsonBody) throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Integer> roleListMap = (List<Integer>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(roleListMap)||dataProcess.listHasNull(roleListMap))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchRoleNameService.deleteRoleNameByBatch(roleListMap))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
}

