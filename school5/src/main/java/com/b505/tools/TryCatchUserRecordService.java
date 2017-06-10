package com.b505.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.UserRecord;
import com.b505.service.IUserRecordService;


@Component
public class TryCatchUserRecordService {

	@Autowired
	private IUserRecordService userRecordService;
	
	/*
	 * @方法名：saveUser(UserRecord user)
	 * @功能：保存user
	 * @功能说明：保存user
	 * @作者：lyf
	 * @创建时间：2015.06.23
	 */
	public boolean saveUser(UserRecord user)throws Exception
	{
		try
		{
			userRecordService.save(user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*
	 * @方法名：updateUser(User user)
	 * @功能：updateUser user
	 * @功能说明：updateUser user
	 * @作者：lyf
	 * @创建时间：2015.06.24
	 */
	public boolean updateUser(UserRecord user)
	{
		try
		{
			userRecordService.update(user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
