package com.b505.tools;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.TimerTask;

import org.springframework.stereotype.Component;

@Component
public class MyTimer extends TimerTask 
{
	//定时刷新
	@Override
	public void run()
	{
		FileUpload fu = new FileUpload();
		try
		{
			Set<String> set = fu.getSet();
			Iterator<String> ite = set.iterator();
			
			while(ite.hasNext())
			{
				File file = new File(ite.next()+"\\");
				fu.checkAndDeletedFile(file);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
