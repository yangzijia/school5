package com.b505.tools;

import java.io.FileInputStream;

import org.springframework.stereotype.Component;

@Component
//客户端得到文件
public class FileDownload 
{
	public byte[] getFile(String URL) throws Exception
	{
		//从本地获取文件
		FileInputStream fis = null;
		//暂存数据
		byte[] fileByte;
		try
		{
			fis = new FileInputStream(URL);
			fileByte = new byte[fis.available()];
            //从本地读数据到fileByte中
            fis.read(fileByte);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("从本地获取文件时出错！");
			fileByte = new byte[0];
		}
		finally
		{
			fis.close();
		}
		return fileByte;
	}
}
