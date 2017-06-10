/*
 *@包名：com.b505.web        
 *@文档名：LoginController.java 
 *@功能：登录系统    
 *@作者：李振强        
 *@创建时间：2014.4.20   
 *@版权：河北北方学院信息技术研究所 
 */
package com.b505.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Login;
import com.b505.bean.LoginUser;
import com.b505.json.JsonAnalyze;
import com.b505.json.UserJsonAnalyze;
import com.b505.security.MyUserDetailServiceImpl;
import com.b505.service.ILoginUserService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.DigestUtils;
import com.b505.tools.MyTimer;
import com.b505.tools.ReturnStatus;
import com.b505.tools.SSHA;
import com.b505.tools.TryCatchUserRecordService;
import com.b505.tools.TryCatchUserService;
import com.b505.tools.ValidatorUtil;

@Controller
public class LoginController 
{
	@Autowired
	private UserJsonAnalyze userJsonAnalyze;
	@Autowired
	private MyTimer myTimer;
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
	private MyUserDetailServiceImpl cwSysUserService;
	@Autowired
	private AuthenticationManager myAuthenticationManager; 
	@Autowired
	private SSHA ssha;
	@Autowired
	private TryCatchUserRecordService tryCatchUserRecordService;
	@Autowired
	private ValidatorUtil validatorUtil;
	@Autowired
	private DigestUtils digestUtils;
	@Autowired
	private ILoginUserService userService;
	
	/*
	 * @方法名：loginCheck(@RequestBody String requestJsonBody)
	 * @功能：用户登录
	 * @功能说明：用户登录
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/loginCheck.html")
	@ResponseBody
	public String loginCheck(@RequestBody String requestJsonBody, HttpServletRequest request)throws Exception 
	//public String loginCheck(@RequestBody String requestJsonBody,HttpServletRequest request)throws Exception
	{
		//定期删除用户上传的图片、语音
		myTimer.run();
		//转换登录界面的数据格式
		Login user = userJsonAnalyze.newloginJsonAnalyze(requestJsonBody);
		//用户输入的验证码
		String verifyCode=user.getVerifyCode();
		//保存在Session中的验证码
		String result_verifyCode = request.getSession().getAttribute("verifyCode").toString();
		//验证码校验
		if(!checkValidateCode(verifyCode,result_verifyCode)){
			return returnStatus.verifyCodeError;
		}
		
		//验证用户的登录信息并返回登录结果
		int isValidUser = tryCatchUserService.hasMatchUser(user.getUserName(), user.getPassWord());
		if(isValidUser==1)
		{
			
			System.out.println("验证成功！");
			
			/*
			 * 验证Clientid
			 */
			if(user.getClientid()!=null){
				//从安卓或ios端传来的数据
				//根据username从数据库获取改用户的clientid
				LoginUser lu = userService.getLoginUserBynickName(user.getUserName());
System.out.println("sjdjflsjdfjsldj====="+lu.getClientid());
				if(lu.getClientid()=="null"){
					//数据库没数据，表示是第一次登陆，添加到数据库Clientid
					lu.setClientid(user.getClientid());
					tryCatchUserService.updateClientidToUser(lu);
				}else{
					if(!user.getClientid().equals(lu.getClientid())){
						//应用更换了，Clientid需要更新
						lu.setClientid(user.getClientid());
						tryCatchUserService.updateClientidToUser(lu);
					}
				}
			}
			
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassWord());
			Authentication authentication = myAuthenticationManager.authenticate(authRequest); //调用loadUserByUsername
			SecurityContextHolder.getContext().setAuthentication(authentication);
			HttpSession session = request.getSession();		
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆						
			System.out.println("zhixing**********5");		
			String role = tryCatchUserService.getRoleBynickNamepassWord(user.getUserName(), user.getPassWord());
			String status = "Success";
			//将角色信息和状态
			String[] key = {"role","status"};
			String[] value ={role,status};

			Map<String,Object> hMap = dataProcess.getMapByStringArray(key, value);
			
			if(dataProcess.dataIsNull(hMap))
			{
				return returnStatus.Fail;
			}		
			//转为json返回给页面
			System.out.println("返回给客户端角色------------>"+jsonAnalyze.map2Json(hMap));
			return jsonAnalyze.map2Json(hMap);
		}
		else if(isValidUser==2)
		{
			return returnStatus.NotHaveUser;
		}
		/*else if(isValidUser==4)
		{
			return returnStatus.RoleError;
		}*/
		else
		{
			return returnStatus.PasswordError;
		}
	}


	/*
	 * @方法名：loginCheckNickname(@RequestBody String requestJsonBody)
	 * @功能：验证昵称是否重复
	 * @功能说明：验证昵称是否重复
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/loginCheckNickname.html")
	@ResponseBody
	public String loginCheckNickname(@RequestBody String requestJsonBody)throws Exception
	{
		String responseValue = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "nickname");
		if(dataProcess.dataIsNull(responseValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		boolean status = false;
		LoginUser user = tryCatchUserService.getUserByNickname(responseValue);	
		if(!dataProcess.dataIsNull(user))
		{
			status = true;
			String[] returnKey = {"beUsed"};
			Object[] returnValue = {status};
			Map<String,Object> statusMap = dataProcess.getMapByStringArray(returnKey, returnValue);
			String statusJson = jsonAnalyze.map2Json(statusMap);
			return statusJson;
		}
		else
		{
			String[] returnKey = {"beUsed"};
			Object[] returnValue = {status};
			Map<String,Object> statusMap = dataProcess.getMapByStringArray(returnKey, returnValue);
			String statusJson = jsonAnalyze.map2Json(statusMap);
			return statusJson;
		}
	}

	/**
	 * 验证码判断
	 * @param request
	 * @return
	 */
	protected boolean checkValidateCode(String verifyCode,String result_verifyCode) {

		if (null == verifyCode || !result_verifyCode.equalsIgnoreCase(verifyCode)) {
			return false;
		}
		return true;
	}
}

