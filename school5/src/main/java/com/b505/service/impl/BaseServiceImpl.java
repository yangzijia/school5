package com.b505.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import com.b505.dao.IBaseDao;
import com.b505.pager.Pager;
import com.b505.service.IBaseService;

public  abstract class BaseServiceImpl<T> implements IBaseService<T> {
	protected IBaseDao<T> baseDao;

	public abstract void setIBaseDao(IBaseDao<T> baseDao);
	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		baseDao.save(entity);
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		baseDao.delete(entity);
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		baseDao.update(entity);
	}
	@Override
	public boolean findById(String propertyName, Object value){
		return baseDao.findById(propertyName, value);
	}
	@Override
	public T get(String propertyName1,String propertyName2 , Object value1,Object value2){
		return baseDao.get(propertyName1, propertyName2, value1, value2);
	}
	@Override
	public List<T> getAll(){
		return baseDao.getAll();
	}
	@Override
	public void flush(){
		baseDao.flush();
	}
	@Override
	public void clear(){
		baseDao.clear();
	}
	@Override
	public T get(String propertyName,Object value1){
		if( baseDao.get(propertyName, value1) !=null){
			return baseDao.get(propertyName, value1);
		}else {
			return null;
		}
	}
	@Override
	public void saveByMerge(T entity){
		baseDao.saveByMerge(entity);
	}
	@Override
	public Long totalCount(){
		return baseDao.totalCount();
	}
	@Override
	public Pager getByPager(Pager pager){
		return baseDao.getByPager(pager);
	}
	@Override
	public List<T> getByPage(Integer currentPage, Integer pageSize){
		return baseDao.getByPage(currentPage, pageSize);
	}
	@Override
	public Pager findByPager(Pager pager) {
		return baseDao.findByPager(pager);
	}
	
	@Override
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		return baseDao.findByPager(pager, detachedCriteria);
	}
	@Override
	public void saveOrUpdate(T entity){
		baseDao.saveOrUpdate(entity);
	}
	@Override
	public T get(Serializable id) {
		// TODO Auto-generated method stub
		return  baseDao.get(id);
	}
}
