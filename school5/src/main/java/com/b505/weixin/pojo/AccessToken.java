package com.b505.weixin.pojo;

import java.io.Serializable;

/**
 * accessToke获取类
 * @author 少游
 *
 */
public class AccessToken implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 接口访问凭证
	private String access_token;
	// 凭证有效期，单位：秒
	private int expires_in;
	public String getAccess_token()
	{
		return access_token;
	}
	public void setAccess_token(String access_token)
	{
		this.access_token = access_token;
	}
	public int getExpires_in()
	{
		return expires_in;
	}
	public void setExpires_in(int expires_in)
	{
		this.expires_in = expires_in;
	}
	@Override
	public String toString()
	{
		return "AccessToken [access_token=" + access_token + ", expires_in=" + expires_in + "]";
	}

}
