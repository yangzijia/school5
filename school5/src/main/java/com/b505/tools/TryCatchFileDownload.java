package com.b505.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TryCatchFileDownload
{
	@Autowired
	private FileDownload fileDownload;
	
	public Object getFile(String URL)throws Exception
	{
		Object fileByte;
		try
		{
			fileByte = fileDownload.getFile(URL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fileByte = null;
		}
		return fileByte;
	}
}
