package com.b505.weixin.tools;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.service.ICoreService;

@Component
public class TryCatchCoreService
{
	     @Autowired
	     private ICoreService iCoreService;

	public String getprocessRequest(HttpServletRequest request)
	{
		String  respXml;
		// TODO Auto-generated method stub
		try
		{
		  respXml=iCoreService.CoreService(request);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return respXml;
	}
                             
}
