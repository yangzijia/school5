package com.b505.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.b505.bean.Roll_Alert;
import com.b505.dao.ICollegeAdminAlertDao;
@Repository(value="ICollegeAdminAlertDao")
public class CollegeAdminAlertDaoImpl extends BaseDaoImpl<Roll_Alert> implements ICollegeAdminAlertDao
{

	@Override
	public List<Roll_Alert> getCollegeAdminAlert()
	{
		// TODO Auto-generated method stub
		String hql = "select Rl from Roll_Alert Rl";
		Query query = getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Roll_Alert> list=query.list();
		if(query.list().size()>0){
			return  list;
		}else {
			return null;
		}
	}

}
