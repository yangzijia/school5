package com.b505.weixin.utils;



import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.weixin.pojo.AccessToken;

@Component
public class AccessTokenUtil
{
	          //通过@Autowired进行类成员变量的自动注入
	             @Autowired
	             private HttpRequestUtil httpRequestUtil;
	        // 获取access_token的接口地址（GET） 限200（次/天）
	              String url=Constants.access_token_url;
						/**
						* 获取accessToken
						* @param appID
						微信公众号凭证
						* @param appScret
						微信公众号凭证秘钥
						* @return
						*/
	 
	             public AccessToken getAccessToken (String appid, String appsecret){
	            	 AccessToken accessToken=null;
	            	// 访问微信服务器
	            	 String requestUrl =url.replace("APPID", appid).replace("APPSECRET", appsecret);
	            	 System.out.println("requestUrl----->"+requestUrl);
	            	// JSONObject json =HttpRequestUtil.httpsRequest(requestUrl,"GET",null);
	            	 JSONObject json =httpRequestUtil.httpsRequest(requestUrl,"GET",null);
	            	 System.out.println("json---->"+json);
	            	 //判断json是否为空
	            	 if (json!=null)
					{
	            		 try
	 					{
	            			  accessToken=new AccessToken();
	 						   //将获取的access_token放入accessToken对象中
	 						   accessToken.setAccess_token(json.getString("access_token"));
	 						   //将获取的expires_in时间放入accessToken对象中
	 						   accessToken.setExpires_in(json.getInt("expires_in"));
	 						  
	 					}
	 					catch (Exception e)
	 					{
	 						// TODO: handle exception
	 						e.printStackTrace();
	 						System.out.println("系统出错了！");
	 					}

					}else  {
						
						 return null;
					}
					return accessToken;
	            
	          }
}
