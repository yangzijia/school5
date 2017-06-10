package com.b505.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.Dormcallover;
import com.b505.dao.IDormcalloverDao;
@Repository(value="IDormcalloverDao")
public class DormcalloverDaoImpl extends BaseDaoImpl<Dormcallover> implements IDormcalloverDao{

	@Override
	public void saveDormcalloverByBatch(List<Dormcallover> list){
		
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Dormcallover dormcallover = list.get(i);
			session.save(dormcallover);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 服务端辅导员查询宿舍考勤信息,按班级和日期
	 */
	@Override
	public List<Dormcallover> getDormcalloversByGradeAndDate(int gradeId,String date1){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date =  sdf.parse(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql = "select da from Dormcallover da where da.student.studentClass.gradeId=:gradeId and da.date=:date order by da.date desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("gradeId", gradeId);
		query.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<Dormcallover> list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 服务端辅导员查询宿舍考勤信息，按班级
	 */
	@Override
	public List<Dormcallover> getDormcalloversByGrade(int gradeId){
		String hql = "select da from Dormcallover da where da.student.studentClass.gradeId=:gradeId order by da.date desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("gradeId", gradeId);
		@SuppressWarnings("unchecked")
		List<Dormcallover> list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 * @author Administrator
	 * @time 2015-11-24
	 * @fucntion 辅导员批量删除宿舍考勤信息
	 */
	public void delDormcalloverByBatch(List<Integer> list){
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Dormcallover dormcallover = new Dormcallover();
			dormcallover.setId(list.get(i));
			session.delete(dormcallover);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}
}
