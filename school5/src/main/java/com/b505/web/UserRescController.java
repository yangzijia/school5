package com.b505.web;

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

import com.b505.bean.LoginUser;
import com.b505.bean.RoleName;
import com.b505.bean.util.UserRole;
import com.b505.json.JsonAnalyze;
import com.b505.service.IUserRoleService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.ReturnStatus;
import com.b505.tools.StatusMap;
import com.b505.tools.TryCatchUserRoleService;

@Controller
public class UserRescController {

	@Autowired
	private StatusMap statusMap;
	@Autowired
	private IUserRoleService userRoleService;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private ReturnStatus returnStatus;	
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchUserRoleService tryCatchUserRoleService;

	/**
	 * @功能：由用户昵称得到用户所拥有的权限
	 * @创建时间：2015.06.26
	 * @author lyf
	 * @param requestJsonBody
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getnickName.html")
	@ResponseBody
	public String getNickName(@RequestBody String requestJsonBody) throws Exception
	{
		//判断所接收的数据是否为空，若为空返回错误
		if(requestJsonBody==null||"".equals(requestJsonBody)||requestJsonBody.length()<0)
		{
			return returnStatus.CannotAnalyzeData;
		}

		List<Map<String, String>> list = new ArrayList<>();
		try
		{ 			
			//将接收的json格式的昵称转为String 
			String nickName = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "nickName");

			//调用公共dao层方法，由昵称获得角色信息
			LoginUser user=userRoleService.get("nickName", nickName);
			if(user==null){

				return returnStatus.NotHaveUser;
			}
			List<RoleName> roles=user.getRoles();
			//判断若未取到该昵称的角色信息，则返回错误
			if(roles.isEmpty()){
				
				return returnStatus.Fail;
			}
			Map<String, String>map=new HashMap<String, String>();
			StringBuffer roBuffer=new StringBuffer();

			//将用户所有权限一一取出并用逗号隔开
			for (int i = 0; i < roles.size(); i++) {

				if (i!=roles.size()-1) {
					roBuffer.append(roles.get(i).getRemark());
					roBuffer.append(",");
				}else {
					roBuffer.append(roles.get(i).getRemark());
				}
				//将昵称和用户权限放入map
				map.put("nickName", nickName);
				map.put("roleName", roBuffer.toString());
			}

			list.add(map);
			//将list转为json返回
			String json = jsonAnalyze.Listmap2Json(list);
			
			return json;
						
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnStatus.Fail;
	}

	@RequestMapping(value = "/getAllRole.html")
	@ResponseBody
	public String getAllRole(@RequestBody String requestJsonBody)throws Exception
	{

		//判断所接收的数据是否为空，若为空返回错误
		if(requestJsonBody==null||"".equals(requestJsonBody)||requestJsonBody.length()<0)
		{
			return returnStatus.CannotAnalyzeData;
		}
		String nickName = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "nickName");
		System.out.println("执行！！！");
		String userRole=tryCatchUserRoleService.getAllRole(nickName);
		System.out.println("user:"+userRole);
		return userRole;
	}

	@RequestMapping(value = "/updateUserRole.html")
	@ResponseBody
	public String updateUserRole(@RequestBody UserRole user,HttpServletRequest request)throws Exception
	{
		
		if (userRoleService.saveUserRole(user)) {
			return returnStatus.Success;
		} else {
			return returnStatus.Fail;
		}
	}
}
