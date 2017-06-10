package com.b505.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.AppPushInfo;
import com.b505.bean.LoginUser;
import com.b505.dao.IBaseDao;
import com.b505.dao.ILoginUserDao;
import com.b505.service.ILoginUserService;

@Service("loginUserService")

public class LoginUserServiceImpl extends BaseServiceImpl<LoginUser> implements ILoginUserService {
	private ILoginUserDao iloginUserDao;
	@Autowired
	@Qualifier("ILoginUserDao")
	@Override
	public void setIBaseDao(IBaseDao<LoginUser> iloginUserDao) {
		// TODO Auto-generated method stub
		this.baseDao = iloginUserDao;
		this.iloginUserDao = (ILoginUserDao)iloginUserDao;
	}

	 @Override
	public int hasMatchUser(String nickName,String passWord,String role){
		
			@SuppressWarnings("unused")
			int b = 0;
			LoginUser user=iloginUserDao.get(nickName);
			if(user==null){
				return b = 2;
			}else if(iloginUserDao.get("nickName", "password", nickName, passWord)==null){
				return b = 3;
			}
			//验证用户输入的角色是否正确。
			else if(!user.getRole().equals(role)){
				return b =4;
			}
			
			else {
				return b = 1;
			}
		}

	@Override
	public int deletedeleteByUserNickName(LoginUser user) {
		int b = 0;
		this.delete(user);
		if(this.get(user.getNickName())==null){
			b = 1;
		}
		return b;
	}

	@Override
	public void deleteUserByBatch(List<LoginUser> list) {
		// TODO Auto-generated method stub
		iloginUserDao.deleteUserByBatch(list);
	}

	@Override
	public void saveUserByBatch(List<LoginUser> list) {
		// TODO Auto-generated method stub
		iloginUserDao.saveUserByBatch(list);
	}

	@Override
	public void passwordRecBynickName(List<String> list) {
		// TODO Auto-generated method stub
		iloginUserDao.passwordRecBynickName(list);
	}

	@Override
	public boolean updatePasswordByNickName(String newPassword, String nickName) {
		// TODO Auto-generated method stub
		return iloginUserDao.updatePasswordByNickName(newPassword, nickName);
	}

	@SuppressWarnings("unused")
	@Override
	public int hasMatchUser(String nickName, String passWord) {
		int b = 0;
		LoginUser user=iloginUserDao.get(nickName);
		if(user==null){
			return b = 2;
		}else if(!user.getNickName().equals(nickName)||!user.getPassword().equals(passWord)){
			return b = 3;
		}
		else {
			return b = 1;
		}
	}
	
	@Override
	public String getnickNameByIt(String nickName){
		
		return iloginUserDao.getnickNameByIt(nickName);
	}
	
	@Override
	public LoginUser getLoginUserBynickName(String nickName){
		
		return iloginUserDao.getLoginUserBynickName(nickName);
	}
	
	@Override
	public String getRoleBynickNamepassWord(String nickName,String password){
		return iloginUserDao.getRoleBynickNamepassWord(nickName, password);
	}
	
	@Override
	public String getClientidByusername(String userName) {
		return iloginUserDao.getClientidByusername(userName);
	}

	@Override
	public void updateClientidToUser(LoginUser lu) {
		iloginUserDao.updateClientidToUser(lu);
	}

	@Override
	public List<LoginUser> getUserByroles(String role1, String role2) {
		return iloginUserDao.getUserByroles(role1,role2);
	}

	@Override
	public List<LoginUser> getUserByroles(String role1, String role2,
			String role3) {
		return iloginUserDao.getUserByroles(role1,role2,role3);
	}

	@Override
	public void savepushinfo(AppPushInfo api) {
		iloginUserDao.savepushinfo(api);
	}

	@Override
	public String getNameBysa_geter_no(String nickName) {
		return iloginUserDao.getNameBysa_geter_no(nickName);
	}

	@Override
	public List<AppPushInfo> findPushInfosByPush_geter(String userNickname) {
		return iloginUserDao.findPushInfosByPush_geter(userNickname);
	}

	@Override
	public void deletePushinfosByid(int push_id) {
		AppPushInfo api = new AppPushInfo();
		api.setId(push_id);
		iloginUserDao.deletePushinfo(api);
	}
}
