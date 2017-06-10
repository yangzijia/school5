package com.b505.dao.impl;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.b505.bean.Grade;
import com.b505.bean.util.GradeUtil;
import com.b505.dao.IGradeDao;


@Repository(value="IGradeDao")
public class GradeDaoImpl extends BaseDaoImpl<Grade>  implements IGradeDao{
	@Override
	public List<GradeUtil> getGradeByCollege(String collegeName){
		String hql = "select NEW com.b505.bean.util.GradeUtil( g.gradeId,g.yearClass,g.profession,g.classId)"
				+ " from Grade g"
				+ " where g.college.collegeName = "+ " :college";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("college", collegeName);
		@SuppressWarnings("unchecked")
		List<GradeUtil> list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}

	}
	
	@Override
	public String[] getyearClass(){
		//Distinct的是作用是过滤结果集中的重复值
		String hql = "select distinct g.yearClass from Grade g ";
		Query query = getCurrentSession().createQuery(hql);
		
		@SuppressWarnings("rawtypes")
		List list = query.list();
		
		String aString  [] = new String [list.size()];
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				String aString2 =list.get(i).toString();
				
				aString[i]=aString2;
				
			}
			return aString;
		}else{
			return null;
		}
	}
	
	@Override
	public String[] getProfesssionByCY(String collegeName, String yearClass){
		String hql = "select distinct g.profession from Grade g where g.college.collegeName = :collegeName1 and g.yearClass = :yearClass1";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("collegeName1", collegeName);
		query.setParameter("yearClass1", yearClass);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		String aString  [] = new String [list.size()];
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				String aString2 =list.get(i).toString();
				aString[i]=aString2;
				
			}
			return aString;
		}else{
			return null;
		}
	}
	
	@Override
	public String[] getClassIdByCYP(String collegeName, String yearClass,String profession){
		String hql = "select distinct g.classId from Grade g where g.college.collegeName = :collegeName1 and g.yearClass = :yearClass1 and g.profession = :profession1";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("collegeName1", collegeName);
		query.setParameter("yearClass1", yearClass);
		query.setParameter("profession1", profession);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		String aString  [] = new String [list.size()];
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				String aString2 =list.get(i).toString();
				aString[i]=aString2;
				
			}
			return aString;
		}else{
			return null;
		}
	}
	@Override
	public Grade getByProperty(String yearClass, String profession ,String classId){
		String hql="Select g from Grade g Where g.yearClass=:yearClass and g.profession=:profession and g.classId = :classId";
		Query query  = getCurrentSession().createQuery(hql);
		query.setParameter("yearClass", yearClass);
		query.setParameter("profession", profession);
		query.setParameter("classId",classId );
		@SuppressWarnings("rawtypes")
		List list  = query.list();
		if(list.size()>0){
			return (Grade)list.get(0);
		}else{
		return null;
		}
		
	}

	@Override
	public void saveGradeByBatch(List<Grade> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Grade grade = list.get(i);
			session.save(grade);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public void updateGradeByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size() ;i++){
			Grade grade = this.get((Integer)list.get(i).get("gradeId"));
			grade.setYearClass((String)list.get(i).get("yearClass"));
			grade.setProfession((String)list.get(i).get("profession"));
			grade.setClassId((String)list.get(i).get("classId"));
			session.update(grade);
			if(i%20==0){
				session.flush();
				session.clear();
			}
			
		}
	}

	@Override
	public void deleteGradeByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Grade grade = this.getByProperty((String)list.get(i).get("yearClass"), (String)list.get(i).get("profession"), (String)list.get(i).get("classId"));
			session.delete(grade);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public List<GradeUtil> getGradeUtilsByHeadTeacherNickName(String nickName) {
		// TODO Auto-generated method stub
		String hql ="select new com.b505.bean.util.GradeUtil( g.gradeId,g.yearClass,g.profession,g.classId) from Grade g join g.teacherList t where t.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		@SuppressWarnings("unchecked")
		List<GradeUtil> list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	@Override
	public void updateGradeUtilsByHeadTeacherNickName(List<GradeUtil> list){
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);	
		for(int i=0;i<list.size() ;i++){
			//Grade gradeUtil = this.get((Integer)list.get(i).getGradeId());
			Grade gradeUtil = this.get(Integer.valueOf(list.get(i).getGradeId()));
			gradeUtil.setYearClass((String)list.get(i).getYearClass());
			gradeUtil.setProfession((String)list.get(i).getProfession());
			gradeUtil.setClassId((String)list.get(i).getClassId());			
			session.update(gradeUtil);
			if(i%20==0){
				session.flush();
				session.clear();
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getGradeIdByGrade(String yearClass,String profession,String classId){
		
		String hql="Select gradeId from Grade g Where g.yearClass=:yearClass and g.profession=:profession and g.classId = :classId";
		Query query  = getCurrentSession().createQuery(hql);
		query.setParameter("yearClass", yearClass);
		query.setParameter("profession", profession);
		query.setParameter("classId",classId );	
		List<Integer> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}	
		return 0;
	}

	
}
