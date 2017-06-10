package com.b505.service;

import java.util.List;

import com.b505.bean.AppPushInfo;
import com.b505.bean.LoginUser;

public interface ILoginUserService extends IBaseService<LoginUser> {
	 public int hasMatchUser(String nickName,String passWord,String role);
	 public int hasMatchUser(String nickName,String passWord);
	 public int deletedeleteByUserNickName(LoginUser user);
	 public void deleteUserByBatch(List<LoginUser> list);
	 public void saveUserByBatch(List<LoginUser> list);
	 public void passwordRecBynickName(List<String> list);
	 public boolean updatePasswordByNickName(String newPassword, String nickName);
	 public String getnickNameByIt(String nickName);
	 public LoginUser getLoginUserBynickName(String nickName);
	 public String getRoleBynickNamepassWord(String nickName,String password);
	 public String getClientidByusername(String userName);
	public void updateClientidToUser(LoginUser lu);
	public List<LoginUser> getUserByroles(String role1, String role2);
	public List<LoginUser> getUserByroles(String role1, String role2,
			String role3);
	public void savepushinfo(AppPushInfo api);
	public String getNameBysa_geter_no(String nickName);
	public List<AppPushInfo> findPushInfosByPush_geter(String userNickname);
	public void deletePushinfosByid(int push_id);
}
