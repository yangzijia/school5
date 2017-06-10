/**
 * Project:openlaw-maintain-webapp
 * File:DigestUtils.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.b505.tools;

import java.security.MessageDigest;

import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

/**
 * @author lzq
 * @date 2015年10月23日
 * @version $Id$
 */
@Component
public class DigestUtils {

	private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static char[] encode(byte[] bytes) {
		final int nBytes = bytes.length;
		char[] result = new char[2 * nBytes];
		int j = 0;
		for (int i = 0; i < nBytes; i++) {
			result[j++] = HEX[(0xF0 & bytes[i]) >>> 4];
			result[j++] = HEX[(0x0F & bytes[i])];
		}
		return result;
	}

	public static byte[] decode(CharSequence s) {
		int nChars = s.length();
		if (nChars % 2 != 0) {
			throw new IllegalArgumentException("Hex-encoded string must have an even number of characters");
		}
		byte[] result = new byte[nChars / 2];
		for (int i = 0; i < nChars; i += 2) {
			int msb = Character.digit(s.charAt(i), 16);
			int lsb = Character.digit(s.charAt(i + 1), 16);
			if (msb < 0 || lsb < 0) {
				throw new IllegalArgumentException("Non-hex character in input: " + s);
			}
			result[i / 2] = (byte) ((msb << 4) | lsb);
		}
		return result;
	}

	/*
	 * @方法名：digest(String str)
	 * @功能：密码加密
	 * @功能说明：利用MD算法进行加密
	 * @作者：JSY
	 * @修改时间：2016.4.8
	 */
	public String digest(String str) throws Exception {
		final String salt = "doWhile(1){LeavesFly();YangtzeRiverFlows();}";
		String password = str + salt;
		MessageDigest digest = MessageDigest.getInstance("MD5");
		return new String(Base64.encode(new String(encode(digest.digest(password.getBytes()))).getBytes()));

	}
	
	/**
	 * @author JSY
	 * @功能：将密码转换成加密格式
	 * @时间：2016.3.24
	 */
	public static void main(String[] args) throws Exception{
		
	         DigestUtils pd=new DigestUtils();
	         System.out.println(pd.digest("123"));
	}

}
