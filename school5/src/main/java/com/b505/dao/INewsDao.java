package com.b505.dao;

import java.util.List;



import com.b505.bean.News;
import com.b505.bean.util.NewsUtil;



public interface INewsDao extends IBaseDao<News> {
	public List<News> findByPagerAndSection(Integer currentPage,Integer pageSize,String sectionName);
	public List<News> getBySource(String source);
	public List<NewsUtil> getNewsUtilsBySection(String sectionName);
}
