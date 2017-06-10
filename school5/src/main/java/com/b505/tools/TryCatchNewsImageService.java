package com.b505.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.NewsImage;
import com.b505.service.INewsImageService;

@Component
public class TryCatchNewsImageService 
{
	@Autowired
	private INewsImageService  newsImageService;
	
	
	public boolean saveNewsImage(NewsImage newsImage)
	{
		try
		{
			 newsImageService.save(newsImage);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public boolean updateNewsImage(NewsImage image)
	{
		// TODO Auto-generated method stub
		try
		{
			newsImageService.update(image);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public NewsImage getNewsImage(Integer newId)
	{
		NewsImage image;
		try
		{
			image=newsImageService.get(newId);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			return image=null;
		}
		return image;
	}
}

