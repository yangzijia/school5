package com.b505.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.b505.bean.Course;
import com.b505.dao.ICourseDao;

@Repository(value="ICourseDao")
public class CourseDaoImpl extends BaseDaoImpl<Course> implements ICourseDao{

	@Override
	public Course getCourseByClassId(int id){
		
		// TODO Auto-generated method stub
		//由班级id获取该班级课表信息的sql语句
		String sql ="select c from Course c where c.gid=:id ";
		Query query = getCurrentSession().createQuery(sql);
		//设置参数
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Course> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}
			return null;
	}
}
