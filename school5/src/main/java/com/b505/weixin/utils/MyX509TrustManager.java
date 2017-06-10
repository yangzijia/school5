package com.b505.weixin.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 证书信任管理器（用于https请求）
 * 
 * @author 少游
 */
public class MyX509TrustManager implements X509TrustManager
{
	

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public X509Certificate[] getAcceptedIssuers()
		{
			// TODO Auto-generated method stub
			return null;
		}
}
