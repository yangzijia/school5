package com.b505.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;

import org.springframework.stereotype.Component;

import com.b505.bean.SmallLongLatImage;

@Component
public class SmallFileURL 
{
	
	public boolean setPictureFile(String URL) throws Exception
	{
		boolean status = false;
		File fileTest = null;
		SmallLongLatImage smallLongLatImage = new SmallLongLatImage();
		try
		{
			fileTest = new File(URL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		if(fileTest==null||fileTest.length() < 1)
		{
			FileOutputStream fileOutputStream = null;
			try
			{
				fileOutputStream = new FileOutputStream(URL);
				Blob pictureBlob = smallLongLatImage.getImage();
				if(pictureBlob==null||"".equals(pictureBlob))
				{
					throw new IOException("文件被删了或丢失了");
				}
				fileOutputStream.write(pictureBlob.getBytes(0, (int)pictureBlob.length()));
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(e);
			}
			finally
			{
				if(fileOutputStream==null)
				{
					return status;
				}
				fileOutputStream.close();
			}
			status = true;
		}
		return status;
	}
}

