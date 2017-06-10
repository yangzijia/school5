package com.b505.weixin.utils;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;




import com.b505.weixin.pojo.AccessToken;

public class Test
{

	public static void main(String[] args)
	{
		
		String paths[]={"spring/applicationContext.xml","spring/applicationContext-security.xml"};
		
		//加载spring的配置文件
		@SuppressWarnings("resource")
		ApplicationContext context=new ClassPathXmlApplicationContext(paths);
		BeanFactory factory = (BeanFactory) context; 
		AccessTokenUtil atu=(AccessTokenUtil) factory.getBean(AccessTokenUtil.class);
		// TODO Auto-generated method stub
		String  appId=Constants.APPID;
		String  appSecret=Constants.APPSECRET;
		System.err.println(appId);
		
		
		// 调用接口获取access_token
				AccessToken at = atu.getAccessToken(appId, appSecret);
				
				System.out.println(at.getAccess_token());
		
	

	}

}
