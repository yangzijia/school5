package com.b505.tools;

import org.springframework.stereotype.Component;

@Component
public class TryCatchInteger
{
	
	public Integer String2Number(String str)
	{
		Integer integer;
		try
		{
			integer = Integer.parseInt(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return integer = null;
		}
		return integer;
	}
}
