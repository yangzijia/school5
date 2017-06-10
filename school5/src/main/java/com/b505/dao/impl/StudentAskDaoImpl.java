package com.b505.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.StudentAsk;
import com.b505.bean.Type;
import com.b505.bean.util.GradeId;
import com.b505.dao.IStudentAskDao;

@Repository(value="IStudentAskDao")
public class StudentAskDaoImpl extends BaseDaoImpl<StudentAsk>implements IStudentAskDao {
	
	@Override
	public StudentAsk getSAskBynickName(String nickName){
		
			//hql是不能用limit或top之类的，因为要不区分数据库的sql
			//String hql = "from StudentAsk sask where sask.user.nickName=:nickName";
			String hql = "from StudentAsk sask where sask.student.user.nickName=:nickName";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("nickName", nickName);
			if(query.list().size()>0){
				// query.list().get(0)返回 第一条!  需要返回最后一条记录
				//从0开始的，所以要size-1
				return (StudentAsk)query.list().get(query.list().size()-1);
			}else {
				return null;
			}		
	}
	
	@Override
	public List<StudentAsk> getSAskByGradeIdandStatus(int gradeId,String status){
		
			String hql = "from StudentAsk sask where sask.student.studentClass.gradeId=:gradeId and sask.status=:status";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("gradeId", gradeId);
			query.setParameter("status", status);
			@SuppressWarnings("unchecked")
			List<StudentAsk> sList = query.list();
			if(sList.size()>0){
				return sList;
			}else {
				return null;
			}
	}
	
	@Override
	public GradeId getClassIdByNickname(String nickName){
		
		String hql="select new com.b505.bean.util.GradeId(s.studentClass.gradeId) from Student s where s.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		if(query.list().size()>0){
			
			return  (GradeId)query.list().get(0);
			
		}else {
			return null;
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Type> getType(){
		
		String hql = "select t from Type t";
		Query query = getCurrentSession().createQuery(hql);
		List<Type> list = new ArrayList<>();
		list = query.list();
		if(list.size()>0){
			return list;
		}else {
			{
				return null;
			}
		}	
	}
	
	@Override
	public void deleteAskByBatch(List<Integer> list){
		
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			StudentAsk studentAsk = new StudentAsk();
			studentAsk.setAskid(list.get(i));
			session.delete(studentAsk);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}
	
	@Override
	public void updateAskByBatch(List<Map<String, Object>> list) {
		System.out.println("list.size==="+list.size());
System.out.println("(String)list.get(i).get('status')=="+(String)list.get(0).get("status"));
System.out.println("(Integer)list.get(i).get(askid)=="+(Integer)list.get(0).get("askid"));
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i = 0; i<list.size();i++){
			StudentAsk sAsk = this.get((Integer)list.get(i).get("askid"));
System.out.println(sAsk.toString());
			sAsk.setStatus((String)list.get(i).get("status"));
			if(i%20==0){//单次操作数目为20条
				//清理缓存，执行批量更新20条记录的SQL update语句
				session.flush();
				//清空缓存中的sAsk对象
				session.clear();
			}
		}
	}
	
	@Override
	public StudentAsk getSAskBynickNameAndType(String nickName,String type){
		String hql = "from StudentAsk sask where sask.student.user.nickName=:nickName and sask.status=:type";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		query.setParameter("type", type);
		if(query.list().size()>0){
			return (StudentAsk)query.list().get(0);
		}else {
			return null;
		}	
	}
}
