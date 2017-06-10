package com.b505.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.AppPushInfo;
import com.b505.bean.LoginUser;
import com.b505.service.ILoginUserService;

@Component
public class TryCatchUserService
{
	
	@Autowired
	private ILoginUserService userService;
	
	
	public LoginUser getUserByNickname(String nickname)
	{
		LoginUser user;
		try
		{
			user = userService.get(nickname);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			user = null;
		}
		return user;
	}
	
	/*
	 * @方法名：saveUser(User user)
	 * @功能：保存user
	 * @功能说明：保存user
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public boolean saveUser(LoginUser user)throws Exception
	{
		try
		{
			userService.save(user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/*
	 * @方法名：saveUserByBatch(List<User> userList)
	 * @功能：批量保存user
	 * @功能说明：批量保存user
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public boolean saveUserByBatch(List<LoginUser> userList)throws Exception 
	{
		try
		{
			userService.saveUserByBatch(userList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*
	 * @方法名：deletedUserByBatch(List<User> userList)
	 * @功能：批量删除user
	 * @功能说明：批量删除user
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public boolean deletedUserByBatch(List<LoginUser> userList)throws Exception 
	{
		try
		{
			userService.deleteUserByBatch(userList);
			System.out.println("llll");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*
	 * @方法名：update(User user)
	 * @功能：update user
	 * @功能说明：update user
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public boolean update(LoginUser user)
	{
		try
		{
			userService.update(user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean  updatePasswordByNickName(String newPassword, String nickName)
	{
		boolean status = false;
		try
		{
			status = userService.updatePasswordByNickName(newPassword, nickName);
		}
		catch(Exception e)
		{
			return status;
		}
		return status;
	}
	
	/*
	 * @方法名：deleted(User user)
	 * @功能：deleted user
	 * @功能说明：deleted user
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public boolean deletedUser(LoginUser user)
	{
		try
		{
			userService.delete(user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int hasMatchUser(String nickName, String passWord, String role)
	{
		int isValidUser;
		try
		{
			isValidUser = userService.hasMatchUser(nickName, passWord, role);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return isValidUser = 0;
		}
		return isValidUser;
	}	
	
	public int hasMatchUser(String nickName, String passWord)
	{
		int isValidUser;
		try
		{
			isValidUser = userService.hasMatchUser(nickName, passWord);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return isValidUser = 0;
		}
		return isValidUser;
	}	
	
	public String getnickNameByIt(String nickName){
		
		String NickName=null; 
		
		try {
			NickName=userService.getnickNameByIt(nickName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return NickName;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function 客户端登录成功后返回角色信息
	 */
	public String getRoleBynickNamepassWord(String nickName,String password){
		String role;
		try{
			role = userService.getRoleBynickNamepassWord(nickName, password);
		}catch(Exception e){
			e.printStackTrace();
			role= null ;
		}
		return role;
	}
	
	/**
	 * 根据username从数据库获取改用户的clientid
	 * @param userName
	 * @author yangzijia
	 * @date 2017-04-27
	 */
	public String getClientidByusername(String userName) {
		String loginUser;
		try {
			loginUser = userService.getClientidByusername(userName);
		} catch (Exception e) {
			e.printStackTrace();
			loginUser = null;
		}
		return loginUser;
	}

	/**
	 * 添加到数据库Clientid
	 * @param lu
	 * @author yangzijia
	 * @date 2017-04-27
	 */
	public void updateClientidToUser(LoginUser lu) {
		System.out.println("tryfangfa");
		try {
			userService.updateClientidToUser(lu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据学生和班委的id查询出user表
	 * @param role1
	 * @param role2
	 * @return List<LoginUser>
	 */
	public List<LoginUser> getUserByrole(String role1, String role2) {
		List<LoginUser> users=null;
		try {
			users = userService.getUserByroles(role1,role2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 根据学生和班委的id查询出user表
	 * @param role1
	 * @param role2
	 * @param role3
	 * @return List<LoginUser>
	 */
	public List<LoginUser> getUserByrole(String role1, String role2,
			String role3) {
		List<LoginUser> users=null;
		try {
			users = userService.getUserByroles(role1,role2,role3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public void savepushinfo(AppPushInfo api) {
		try {
			userService.savepushinfo(api);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据nickName查询出该用户的姓名
	 * @param sa_geter_no
	 * @return userName
	 */
	public String getNameBysa_geter_no(String nickName) {
		String userName=null;
		try {
			userName = userService.getNameBysa_geter_no(nickName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userName;
	}

	/**
	 * //根据userNickname查询出接受到的推送消息。
	 * @param userNickname
	 * @return
	 */
	public List<AppPushInfo> findPushInfosByPush_geter(String userNickname) {
		List<AppPushInfo> api=null;
		try {
			api = userService.findPushInfosByPush_geter(userNickname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * 根据客户端传来的id删除推送消息中的信息
	 * @param push_id
	 * @return
	 */
	public void deletePushinfosByid(int push_id) {
		try {
			userService.deletePushinfosByid(push_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

