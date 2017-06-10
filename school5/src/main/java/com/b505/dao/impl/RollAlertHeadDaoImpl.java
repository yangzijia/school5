package com.b505.dao.impl;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.b505.bean.Roll_Alert_Head;

import com.b505.dao.IRollAlertHeadDao;

@Repository(value="IRollAlertHeadDao")
public class RollAlertHeadDaoImpl extends BaseDaoImpl<Roll_Alert_Head> implements IRollAlertHeadDao
{

	

	@Override
	public int getMaxid()
	{
		// TODO Auto-generated method stub
		String hql="select max(head_id) from Roll_Alert_Head";
		Query query = getCurrentSession().createQuery(hql);
		if(query.list().size()>0){
			return (int) query.list().get(0);
		}else {
			return 0;
		}
	}

}
