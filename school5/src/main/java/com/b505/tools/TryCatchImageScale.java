package com.b505.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TryCatchImageScale
{
	@Autowired
	private ImageScale imageScale;
	
	public boolean imageScale(String quondamFileURL, String changeFileURL, String fileName, String fileExtension) throws Exception
	{
		 int w = 110, h = 400;
		 BufferedImage quondamImage;
		try
		{
			quondamImage = ImageIO.read(new File(quondamFileURL));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		BufferedImage changeImage = imageScale.imageZoomOut(quondamImage, w, h, true);
		FileOutputStream fos = null;
		try
		{
			File file = new File(changeFileURL);
			if(!file.exists())
			{
				file.mkdirs();
			}
			fos = new FileOutputStream(file + fileName + "." + fileExtension);
			ImageIO.write(changeImage, fileExtension, fos);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			fos.close();
		}
		return true;
	}
}
