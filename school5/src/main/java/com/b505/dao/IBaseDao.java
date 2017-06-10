package com.b505.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import com.b505.pager.Pager;

public interface IBaseDao<T> {
	public void save(T entity);
	public void saveOrUpdate(T entity);
	public T get(Serializable id);
	public void delete(T entity);
	public void update(T entity);
	public T get(String propertyName, Object value);
	public boolean findById(String propertyName, Object value);
	public T get(String propertyName1,String propertyName2 , Object value1,Object value2);
	public List<T> getAll();
	public void flush();
	public void clear();
	public void saveByMerge(T entity);
	public Long totalCount();
	public Pager getByPager(Pager pager);
	public List<T> getByPage(Integer currentPage, Integer pageSize);
	public Pager findByPager(Pager pager);
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria);
	
}
