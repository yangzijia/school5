package com.b505.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatType;
import com.b505.bean.util.HteacherLongLatUtil;
import com.b505.bean.util.LongLatUtil;
import com.b505.dao.ILongLatDao;

@Repository(value="ILongLatDao")
public class LongLatDaoImpl extends BaseDaoImpl<LongLat> implements ILongLatDao {
	@Override
	public List <LongLat> getLongLatByStudentId(String id){
		String hql = "from LongLat longlat where longlat.student.id = :id ";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<LongLat> longLats = query.list();
		if(longLats.size()>0){
			return longLats;
		}else{
			return null;
		}
	}

	@Override
	public Long getLongLatLengthByGrade(String yearClass,String profession, String classId) {
		String hql = "select Count (longLat) from LongLat longLat where longLat.student.studentClass.yearClass=:yearClass" +
				" and longLat.student.studentClass.profession=:profession and  longLat.student.studentClass.classId=:classId";
		Query query  = getCurrentSession().createQuery(hql);
		query.setParameter("yearClass", yearClass);
		query.setParameter("profession", profession);
		query.setParameter("classId", classId);
		Long long1= (Long)query.uniqueResult();
		return long1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LongLatUtil> getLongLatUtilByGrade(int gradeId) {
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.LongLatUtil(l.mapId,l.longlattype,longLatDate,l.student.id,l.student.studentName,l.word,l.geography) from LongLat l where l.student.studentClass.gradeId =:gradeId order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("gradeId", gradeId);
		List <LongLatUtil> list = new ArrayList<LongLatUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LongLat> getLongLatClientUtilByGrade(int gradeId) {
		// TODO Auto-generated method stub
		//String hql="select new com.b505.bean.util.LongLatClientUtil(l.student.user.nickName,l.student.id,l.student.studentName,l.geography,l.latitude,l.longitude,l.)from LongLat l where l.student.studentClass.gradeId =:gradeId";
		String hql = "select l from LongLat l where l.student.studentClass.gradeId =:gradeId order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("gradeId", gradeId);
		List <LongLat> list = new ArrayList<LongLat>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLatUtil> getAllLongLatUtil() {
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.LongLatUtil(l.mapId,l.longlattype,l.longLatDate,l.student.id,l.student.studentName,l.word,l.geography) from LongLat l where l.student.user.nickName!=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", "88888888");
		List <LongLatUtil> list = new ArrayList<LongLatUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HteacherLongLatUtil> getHteacherAllLongLatUtil(){
		String hql="select new com.b505.bean.util.HteacherLongLatUtil(l.mapId,l.longlattype,l.longLatDate,l.hteacherNickname,l.word,l.geography) from LongLat l where l.student.user.nickName=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", "88888888");
		List <HteacherLongLatUtil> list = new ArrayList<HteacherLongLatUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @function 服务端领导获取所有预警类型
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLatType> getAllLatTypes(){
		
		// TODO Auto-generated method stub
		String hql="select lt from LongLatType lt";
		Query query = getCurrentSession().createQuery(hql);
		List<LongLatType> list = new ArrayList<LongLatType>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function 服务端学生科获取预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLatUtil> getLongLatUtilByStudentAdmin(){
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.LongLatUtil(l.mapId,l.longlattype,l.longLatDate,l.student.id,l.student.studentName,l.word,l.geography) from LongLat l where l.longlattype=:longlattype and l.student.user.nickName!=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("longlattype", "心理预警");
		query.setParameter("nickName", "88888888");
		List <LongLatUtil> list = new ArrayList<LongLatUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function 服务端学院教学科获取预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLatUtil> getLongLatUtilByCollegeAdmin(){
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.LongLatUtil(l.mapId,l.longlattype,l.longLatDate,l.student.id,l.student.studentName,l.word,l.geography) from LongLat l where l.longlattype=:longlattype and l.student.user.nickName!=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("longlattype", "教学异常");
		query.setParameter("nickName", "88888888");
		List <LongLatUtil> list = new ArrayList<LongLatUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function 服务端后勤集团获取预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLatUtil> getLongLatUtilByLogisticsAdmin(){
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.LongLatUtil(l.mapId,l.longlattype,l.longLatDate,l.student.id,l.student.studentName,l.word,l.geography) from LongLat l where l.longlattype=:longlattype and l.student.user.nickName!=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("longlattype", "设施维修");
		query.setParameter("nickName", "88888888");
		List <LongLatUtil> list = new ArrayList<LongLatUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function 服务端宿管科获取预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLatUtil> getLongLatUtilByDormAdmin(){
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.LongLatUtil(l.mapId,l.longlattype,l.longLatDate,l.student.id,l.student.studentName,l.word,l.geography) from LongLat l where l.longlattype=:longlattype and l.student.user.nickName!=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("longlattype", "学生生活");
		query.setParameter("nickName", "88888888");
		List <LongLatUtil> list = new ArrayList<LongLatUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 客户端学生科获取预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLat> getLongLatClientUtilByStudentAdmin(){
		// TODO Auto-generated method stub
		String hql = "select l from LongLat l where l.longlattype=:longlattype and l.student.user.nickName!=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("longlattype", "心理预警");
		query.setParameter("nickName", "88888888");
		List <LongLat> list = new ArrayList<LongLat>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 客户端学院教学科获取预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLat> getLongLatClientUtilByCollegeAdmin(){
		// TODO Auto-generated method stub
		String hql = "select l from LongLat l where l.longlattype=:longlattype and l.student.user.nickName!=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("longlattype", "教学异常");
		query.setParameter("nickName", "88888888");
		List <LongLat> list = new ArrayList<LongLat>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 客户端后勤集团获取预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLat> getLongLatClientUtilByLogisticsAdmin(){
		// TODO Auto-generated method stub
		String hql = "select l from LongLat l where l.longlattype=:longlattype and l.student.user.nickName!=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("longlattype", "设施维修");
		query.setParameter("nickName", "88888888");
		List <LongLat> list = new ArrayList<LongLat>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 客户端宿管科获取预警消息
	 */
	@SuppressWarnings("unchecked")
	public List<LongLat> getLongLatClientUtilByDormAdmin(){
		// TODO Auto-generated method stub
		String hql = "select l from LongLat l where l.longlattype=:longlattype and l.student.user.nickName!=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("longlattype", "学生生活");
		query.setParameter("nickName", "88888888");
		List <LongLat> list = new ArrayList<LongLat>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-26
	 * @function 服务端领导按预警类型查询预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLatUtil> getLongLatUtilByLongLattype(String longlattype){
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.LongLatUtil(l.mapId,l.longlattype,l.longLatDate,l.student.id,l.student.studentName,l.word,l.geography) from LongLat l where l.longlattype=:longlattype and l.student.user.nickName!=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("longlattype", longlattype);
		query.setParameter("nickName", "88888888");
		List <LongLatUtil> list = new ArrayList<LongLatUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-27
	 * @function 服务端领导按预警类型查询教师预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLatUtil> getTeacherLongLatUtilByLongLattype(String longlattype){
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.LongLatUtil(l.mapId,l.longlattype,l.longLatDate,l.student.id,l.student.studentName,l.word,l.geography) from LongLat l where l.longlattype=:longlattype and l.student.user.nickName=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("longlattype", longlattype);
		query.setParameter("nickName", "88888888");
		List <LongLatUtil> list = new ArrayList<LongLatUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-26
	 * @function 服务端领导按时间段查询预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLatUtil> getLongLatUtilByDate(String time1,String time2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 =  sdf.parse(time1);
			date2 =  sdf.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql="select new com.b505.bean.util.LongLatUtil(l.mapId,l.longlattype,l.longLatDate,l.student.id,l.student.studentName,l.word,l.geography) from LongLat l where l.longLatDate between :date1 and :date2 and l.student.user.nickName!=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("date1", date1);
		query.setParameter("date2", date2);
		query.setParameter("nickName", "88888888");
		List <LongLatUtil> list = new ArrayList<LongLatUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-27
	 * @function 服务端领导按时间段查询教师预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLatUtil> getTeacherLongLatUtilByDate(String time1,String time2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 =  sdf.parse(time1);
			date2 =  sdf.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql="select new com.b505.bean.util.LongLatUtil(l.mapId,l.longlattype,l.longLatDate,l.student.id,l.student.studentName,l.word,l.geography) from LongLat l where l.longLatDate between :date1 and :date2 and l.student.user.nickName=:nickName order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("date1", date1);
		query.setParameter("date2", date2);
		query.setParameter("nickName", "88888888");
		List <LongLatUtil> list = new ArrayList<LongLatUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * @author JSY
	 * @time 2016.3.15
	 * @function  客户端领导获得最新的预警消息
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LongLat> getAllLongLat() {
		
		// TODO Auto-generated method stub
		String hql = "select l from LongLat l order by l.longLatDate desc";
		Query query = getCurrentSession().createQuery(hql).setMaxResults(10);
		
		List<LongLat> list = new ArrayList<LongLat>();
		list = query.list();
		System.out.println("list"+" "+list);
		if(list.size()>0){
			return list;
		}
		return null;
	}
}
