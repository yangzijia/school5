package com.b505.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.b505.bean.DormcalloverCount;
import com.b505.dao.IDormcalloverCountDao;

@Repository(value="IDormcalloverCountDao")
public class DormcalloverCountDaoImpl extends BaseDaoImpl<DormcalloverCount> implements IDormcalloverCountDao{

	@Override
	public DormcalloverCount getDormcalloverCountBysnumber(String studentNumber){
		String hql = "select d from DormcalloverCount d where d.student.id=:studentNumber";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("studentNumber", studentNumber);
		@SuppressWarnings("unchecked")
		List<DormcalloverCount> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
