package com.b505.dao.impl;



import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.College;
import com.b505.dao.ICollegeDao;

@Repository(value="ICollegeDao")
public class CollegeDaoImpl extends BaseDaoImpl<College>  implements ICollegeDao{
	@Override
	public String getCollege(Integer id){
		String hql = "select c.collegeName from College c where c.collegeid = :id";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		List<?> list  = query.list();
		if(list.size()>0){
			return list.get(0).toString();
		}else{
			return null;
		}
	}
	@Override
	public String[] getAllCollegeName(){
		String hql = "select c.collegeName from College c";
		Query query = getCurrentSession().createQuery(hql);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		String [] string = new String [list.size()];
		if(list.size()>0){
			for(int i = 0;i<list.size();i++){
				String a=list.get(i).toString();
				string[i] = a;
			}
			return string;
		}else{
			return null;
		}
	}
	@Override
	public void saveCollegeByBatch(List<College> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			College college = list.get(i);
			session.save(college);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}
	@Override
	public void deleteCollegeByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			College college = new College();
			college.setCollegeid(list.get(i));
			session.delete(college);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}
	@Override
	public void updateCollegeByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i = 0; i<list.size();i++){
			College college = this.get((Integer)list.get(i).get("collegeid"));
			college.setCollegeName((String)list.get(i).get("collegeName"));
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public int getCollegeIdByGradeId(int gradeId){
		
		String hql = "select g.college.collegeid from Grade g where g.gradeId = :gradeId";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("gradeId", gradeId);
		if(query.list().size()>0){
			return (int)query.list().get(0);
		}else{
			return 0;
		}
	}
}
