package com.b505.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.News;
import com.b505.bean.util.NewsUtil;
import com.b505.dao.IBaseDao;
import com.b505.dao.INewsDao;
import com.b505.service.INewsService;
@Service("NewsService")
public class NewsServiceImpl extends BaseServiceImpl<News> implements INewsService {
		private INewsDao inewsDao;
		@Autowired
		@Qualifier("INewsDao")
		@Override
		public void setIBaseDao(IBaseDao<News> inewsDao) {
			// TODO Auto-generated method stub
			this.baseDao = inewsDao;
			this.inewsDao = (INewsDao)inewsDao;
		}
		@Override
		public List<News> findByPagerAndSection(Integer currentPage,Integer pageSize,String sectionName){
					
			return inewsDao.findByPagerAndSection(currentPage, pageSize, sectionName);
		}
		@Override
		public List<News> getBySource(String source){
			return inewsDao.getBySource(source);
		}
		@Override
		public int deletedNewsByID(News news) {
			// TODO Auto-generated method stub
			this.delete(news);
			int b = 0;
			if(this.get(news.getId())==null){
				b = 1;
			}
			return b;
		}
		@Override
		public List<NewsUtil> getNewsUtilsBySection(String sectionName) {
			// TODO Auto-generated method stub
			
			return inewsDao.getNewsUtilsBySection(sectionName);
		}
		
		
}
