package com.b505.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.Leader;
import com.b505.bean.util.LeaderRegisterUtil;
import com.b505.bean.util.LeaderUtil;

import com.b505.bean.util.Roll_AlertUtil2;
import com.b505.bean.util.Roll_AlertUtils;
import com.b505.dao.ILeaderDao;
@Repository(value="ILeaderDao")
public class LeaderDaoImpl extends BaseDaoImpl<Leader> implements ILeaderDao{

	@Override
	public int deleteLeaderByPhone(String phone) {
		// TODO Auto-generated method stub
		String hql = "delete from Leader leader where leader.phone = :phone";
		Query  query = getCurrentSession().createQuery(hql);
		query.setParameter("phone", phone);
		int b = query.executeUpdate();
		return b;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<LeaderUtil> getLeaderUtils(){
		String hql = "select new com.b505.bean.util.LeaderUtil(l.id,l.name,l.phone,l.user.nickName,l.user.password) from Leader l";
		Query query = getCurrentSession().createQuery(hql);
		List<LeaderUtil> list = new ArrayList<>();
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
	public void deleteLeaderByBatch(List<Integer> list) {
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Leader leader = new Leader();
			leader.setId(list.get(i));
			session.delete(leader);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}
	@Override
	public void updateLeaderByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Leader leader =this.get((Integer)list.get(i).get("id"));
			leader.setPhone((String)list.get(i).get("phone"));
			leader.setName((String)list.get(i).get("name"));
			session.update(leader);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}
	@Override
	public void saveLeaderByBatch(List<Leader> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Leader leader = list.get(i);
			session.save(leader);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
		
	}
	@Override
	public LeaderRegisterUtil getLeaderRegisterUtilBynickName(String nickName) {
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.LeaderRegisterUtil(l.name,l.phone) from Leader l where l.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		@SuppressWarnings("unchecked")
		List<LeaderRegisterUtil> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	@Override
	public boolean updatePhoneBynickName(String phone, String nickName) {
		// TODO Auto-generated method stub
		String hql ="update Leader l set l.phone =:phone  where l.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("phone", phone);
		query.setParameter("nickName", nickName);
		int b = query.executeUpdate();
		if(b>0){
			return true;
		}else {
			return false;
		}
		
	}
	@Override
	public Leader getLeaderByNickname(String nickname) {
		// TODO Auto-generated method stub
		String hql="select l from Leader l where l.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName",nickname);
		@SuppressWarnings("unchecked")
		List<Leader> list = query.list(); 
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	@Override
	public List<Leader> getLeadersByCollegeAndRank(int collegeid){
		String hql="select l from Leader l where l.college.collegeid=:collegeid and l.rank=:rank or l.rank=:rank1";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("collegeid",collegeid);
		query.setParameter("rank","学生科");
		query.setParameter("rank1","学院教学科");
		@SuppressWarnings("unchecked")
		List<Leader> list = query.list(); 
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<Roll_AlertUtils> getRoll_Alert(String college,
			String yearClass, String profession, String classId)
	{
		String hql="select new com.b505.bean.util.Roll_AlertUtils(r.student.studentClass.yearClass,r.student.studentClass.profession,r.student.studentClass.classId,r.student.id,r.name,r.opinion) from Roll_Alert r where r.student.college.collegeName=:collegeName and r.student.studentClass.yearClass=:yearClass and r.student.studentClass.profession=:profession and r.student.studentClass.classId=:classId";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("collegeName",college);
		query.setParameter("yearClass",yearClass);
		query.setParameter("profession",profession);
		query.setParameter("classId",classId);
		@SuppressWarnings("unchecked")
		List<Roll_AlertUtils>list=query.list();
		if (list.size()>0)
		{
			return list;
		}else {
			return null;
		}
	}
	@Override
	public List<Roll_AlertUtil2> getLeaderGetRollAlertDetail(String studentNumber)
	{
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.Roll_AlertUtil2(r.score,r.course,r.coursetype,r.opinion) from Roll_Alert r where r.student.id=:studentNumber";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("studentNumber", studentNumber);
		@SuppressWarnings("unchecked")
		List<Roll_AlertUtil2>list=query.list();
		if (list.size()>0)
		{
			return list;
		}else {
			return null;
		}
		
	}
	@Override
	public List<Roll_AlertUtils> getwebLeaderGetAllRollAlert()
	{
		// TODO Auto-generated method stub
	    //使用关键字distinct去掉重复的数据
		String hql="select distinct new com.b505.bean.util.Roll_AlertUtils(r.student.studentClass.yearClass,r.student.studentClass.profession,r.student.studentClass.classId,r.student.id,r.name,r.opinion) from Roll_Alert as r";
		Query query=getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Roll_AlertUtils> list = query.list();
	    if (list.size()>0)
		{
			return list;
		}else {
			return null;
		}
	
	}
}
