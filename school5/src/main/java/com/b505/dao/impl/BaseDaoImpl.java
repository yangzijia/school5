package com.b505.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import com.b505.dao.IBaseDao;
import com.b505.pager.Pager;
import com.b505.pager.Pager.OrderType;

public class BaseDaoImpl <T> implements IBaseDao<T> {
	private Class<T> entityClass;
	@Resource
	private SessionFactory sessionFactory;
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {  
		        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();  
		        entityClass = (Class<T>) type.getActualTypeArguments()[0];  
		         
		    }
	public Session getCurrentSession() {
		Session session = null;
		try {
			session= this.sessionFactory.getCurrentSession();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return session;
	}

	
	
	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		getCurrentSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(Serializable id) {
		// TODO Auto-generated method stub
		return (T) getCurrentSession().get(entityClass, id);
	}

	@Override
	public void delete(T entity) {
		getCurrentSession().delete(entity);
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		 getCurrentSession().update(entity);
	}

	@Override
	public boolean findById(String propertyName, Object value) {
		T object = get(propertyName, value);
		return (object != null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(String propertyName, Object value) {
		String hql ="select model" + " from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
		return (T) getCurrentSession().createQuery(hql).setParameter(0, value).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(String propertyName1, String propertyName2, Object value1,
			Object value2) {
		String hql = " from " + entityClass.getName() + " as model where model." + propertyName1 + " = :name"
				 +  " and " + " model." + propertyName2 + " = :pd"; 
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("name", value1);
		query.setParameter("pd", value2);
		if(query.list().size()>0){
			return (T)query.list().get(0);
		}else {
			return null;
		}
		 
	
	}

	@Override
	public List<T> getAll() {
		System.out.println("执行了");
		String hql = "from " + entityClass.getName();
		Query query  = getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<T> list = query.list();
		if(list.size()>0){
			System.out.println("list:"+list);
			return list;
		}else {
			return null;
		}
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		getCurrentSession().flush();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		getCurrentSession().clear();
		
	}

	@Override
	public void saveByMerge(T entity) {
		// TODO Auto-generated method stub
		getCurrentSession().merge(entity);
	}

	@Override
	public Long totalCount() {
		// TODO Auto-generated method stub
		String hql = "select Count (model) from " + entityClass.getName() + " as model";
		Long total =(Long) getCurrentSession().createQuery(hql).uniqueResult();
		return total;
	}

	@Override
	public Pager getByPager(Pager pager) {
		if (pager == null) {
			pager = new Pager();
			
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
	
	Query  q  =  getCurrentSession().createQuery("from "+entityClass.getName());
	q.setFirstResult((pageNumber - 1) * pageSize);
	q.setMaxResults(pageSize);
	q.list();
	System.out.println(""+q.list().size());
	
		pager.setList(q.list());
		return pager;
	}

	@Override
	public List<T> getByPage(Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = "select model from " + entityClass.getName() + " as model";
		@SuppressWarnings("unchecked")
		List<T> list= getCurrentSession().createQuery(hql).setFirstResult((currentPage-1)*pageSize)
				.setMaxResults(pageSize).list();
		return list;
	}

	@Override
	public Pager findByPager(Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		return findByPager(pager, detachedCriteria);
	}

	@Override
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		String typeId=pager.getTypeId();
		String typeProperty=pager.getTypeProperty();
		OrderType orderType = pager.getOrderType();
		
		Criteria criteria = detachedCriteria.getExecutableCriteria(getCurrentSession());
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			String propertyString = "";
			if (property.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property, ".");
				String propertySuffix = StringUtils.substringAfter(property, ".");
				criteria.createAlias(propertyPrefix, "model");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			criteria.add(Restrictions.like(propertyString, "%" + keyword + "%"));
		}
		if(typeId!=null && typeId.length()>1)
		{
			criteria.add(Restrictions.like( typeProperty, typeId));
		}
		System.out.println();
		Integer totalCount = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		pager.setTotalCount(totalCount);
		System.out.println(totalCount+"totalCount");
		pager.setList(criteria.list());
		System.out.println("criteria.list"+criteria.list().size());
		return pager;
	}

	@Override
	public void saveOrUpdate(T entity) {
		getCurrentSession().saveOrUpdate(entity);
		
	}


}
