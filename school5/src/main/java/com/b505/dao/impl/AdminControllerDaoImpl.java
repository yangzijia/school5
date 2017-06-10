package com.b505.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.b505.bean.util.AdminControllerUtil;
import com.b505.dao.IAdminControllerDao;
@Repository(value="IAdminControllerDao")
public class AdminControllerDaoImpl extends BaseDaoImpl<AdminControllerUtil> implements IAdminControllerDao
{

	@Override
	public List<AdminControllerUtil> getAdminControllerUtilByGrade(
			String yearClass, String profession, String classId)
	{
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.AdminControllerUtil(r.student.studentClass.yearClass,r.student.studentClass.profession,r.student.studentClass.classId,r.student.id,r.name,r.rid) from Roll_Alert r where r.student.studentClass.yearClass=:yearClass and r.student.studentClass.profession=:profession and r.student.studentClass.classId=:classId";
		//String hql="select new com.b505.bean.util.AdminControllerUtil(c.yearClass,c.profession,c.classId,c.name,c.snumber,c.opinion) from Roll_Alert c where c.yearClass=:yearClass and c.profession=:profession and c.classId=:classI";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("yearClass", yearClass);
		query.setParameter("profession", profession);
		query.setParameter("classId", classId);
		@SuppressWarnings("unchecked")
		List<AdminControllerUtil> list = query.list();
		if(list.size()>0){
			return list;
		}else{
		return null;
		}
	}

	

}
