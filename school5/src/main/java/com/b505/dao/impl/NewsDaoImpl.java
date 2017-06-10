package com.b505.dao.impl;

import java.util.List;


import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.b505.bean.News;
import com.b505.bean.util.NewsUtil;
import com.b505.dao.INewsDao;

@Repository(value="INewsDao")
public class NewsDaoImpl extends BaseDaoImpl<News> implements INewsDao{
	@Override
	public List<News> findByPagerAndSection(Integer currentPage,Integer pageSize,String sectionName){
		String hql = "select model from  News  model where model.section.sectionName=:sectionName order by model.reDate desc";
		
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("sectionName", sectionName);
		@SuppressWarnings("unchecked")
		List<News> list=query.setFirstResult((currentPage-1)*pageSize)
					.setMaxResults(pageSize).list();
		return list;
		
	}
	@Override
	public List<News> getBySource(String source){
		@SuppressWarnings("unchecked")
		List<News> list = getCurrentSession().createCriteria(News.class)
				.add(Restrictions.or(
					Restrictions.like("theme", source,MatchMode.ANYWHERE),
					Restrictions.like("content", source,MatchMode.ANYWHERE)
						)).list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<NewsUtil> getNewsUtilsBySection(String sectionName) {
		// TODO Auto-generated method stub
		String hql = "select new com.b505.bean.util.NewsUtil(news.id,news.theme,news.author,news.checker,news.reDate,news.url) from News news where news.section.sectionName =:sectionName order by news.reDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("sectionName", sectionName);
		@SuppressWarnings("unchecked")
		List<NewsUtil> list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}

}
