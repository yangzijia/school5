package com.b505.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.service.ILoginUserService;

@Component
public class TryCatchResettingPassword
{
	@Autowired
	private ILoginUserService userService;
	
	public boolean passwordRecBynickName(List<String> list) throws Exception
	{
		try
		{
			userService.passwordRecBynickName(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

