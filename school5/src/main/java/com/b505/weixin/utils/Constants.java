package com.b505.weixin.utils;

import org.springframework.stereotype.Component;

@Component
public class Constants
{
	/**
	 * 微信接入token ，用于验证微信接口
	 */
	//public String TOKEN = "weixinface";

	public static String TOKEN = "weixin";
	/**
	 * APPID(测试号的)
	 */
	public static  String APPID="wxb3b3cefc59f109ca";
	/**
	 * APPSECRET(测试号的)
	 */
	public static String  APPSECRET="aac2d7e8ce371974da591f040d2116f1";
	
	/**
	 *  获取access_token的接口地址（GET） 限200（次/天）
	 */
	public static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
}
