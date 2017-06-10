package com.b505.service;

import java.util.List;

import com.b505.bean.News;
import com.b505.bean.util.NewsUtil;

public interface INewsService extends IBaseService<News> {
	public List<News> findByPagerAndSection(Integer currentPage,Integer pageSize,String sectionName);
	public List<News> getBySource(String source);
	public int deletedNewsByID(News news);
	public List<NewsUtil> getNewsUtilsBySection(String sectionName);
}
