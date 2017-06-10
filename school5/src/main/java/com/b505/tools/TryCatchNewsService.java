package com.b505.tools;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.News;
import com.b505.bean.util.NewsUtil;
import com.b505.service.INewsService;

@Component
public class TryCatchNewsService 
{
	@Autowired
	private INewsService newsService;
	
	
	public boolean saveNews(News news)
	{
		try
		{
			newsService.save(news);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean updateNews(News news)
	{
		try
		{
			newsService.update(news);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean deletedNewsByID(News news)
	{
		try
		{
			newsService.deletedNewsByID(news);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public News getNews(Serializable id)
	{
		News news;
		try
		{
			 news = newsService.get(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return news = null;
		}
		return news;
	}
	
	
	public List<News> findByPagerAndSection(Integer currentPage, Integer pageSize, String sectionName)
	{
		List<News> newsList;
		try
		{
			newsList = newsService.findByPagerAndSection(currentPage, pageSize, sectionName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return newsList = null;
		}
		return newsList;
	}
	public List<NewsUtil> getWebNewsUtilList(String sectionName){
		List<NewsUtil> newsList;
		try {
			newsList = newsService.getNewsUtilsBySection(sectionName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return newsList= null;
		}
		return newsList;
	}
}
