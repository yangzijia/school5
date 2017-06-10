package com.b505.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.b505.bean.TeacherClass;
import com.b505.bean.util.TeacherId;
import com.b505.dao.ITeacherClassDao;
@Repository(value="ITeacherClassDao")
public class TeacherClassDapImpl extends BaseDaoImpl<TeacherClass> implements ITeacherClassDao {

	@Override
	public TeacherId getTidByS_C_ID(int gradeId){  
			
			//2015.10.31修改，将TeacherClass改为HeadteacherClass
			String hql = "select new com.b505.bean.util.TeacherId(htc.teacher.id) from HeadteacherClass htc where htc.studentClass.gradeId=:gradeId";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("gradeId", gradeId);
			if(query.list().size()>0){
				
				return (TeacherId) query.list().get(0);
				
			}else {
				return null;
			}		
	}
}
