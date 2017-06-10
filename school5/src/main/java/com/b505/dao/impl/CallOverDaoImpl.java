package com.b505.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.CallOver;
import com.b505.bean.util.CallOverUtil;
import com.b505.bean.util.CallOverUtils;
import com.b505.dao.ICallOverDao;
@Repository(value="ICallOverDao")
public class CallOverDaoImpl extends BaseDaoImpl<CallOver> implements ICallOverDao{
	
	@Override
	public List<String> getAllDate(){
		String hql = "select distinct c.date from CallOver c";
		Query  query = getCurrentSession().createQuery(hql);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List <String> list = new ArrayList();
		@SuppressWarnings("rawtypes")
		List list2 = query.list();
		if(list2.size()>0){
			for(int i=0;i<list2.size();i++){
				String s1 = list2.get(i).toString();
				list.add(s1);
			}
			return  list;
		}else{
			return null;
		}
		
	}
	@Override
	public List<String> getAllClass(){
		
		String hql = "select distinct c.className from CallOver c";
		Query query = getCurrentSession().createQuery(hql);
		List <String> list = new ArrayList<>();
		@SuppressWarnings("rawtypes")
		List list2 = query.list();
		if(list2.size()>0){
			for(int i=0;i<list.size();i++){
				String s1 = list2.get(i).toString();
				list.add(s1);
			}
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<CallOver> getStudentListByDC(String date,String className){
		String hql = "select c from CallOver c where c.date = :date and c.className = :className ";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("date", date);
		query.setParameter("className", className);
		@SuppressWarnings("unchecked")
		List<CallOver> list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<CallOver> getStudentListByTN(String teacherName){
		String hql = "select c from CallOver where c.teacherName = :teacherName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("teacherName", teacherName);
		@SuppressWarnings("unchecked")
		List<CallOver> list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<String> getClassNameByClassID(Integer gradeId) {
		String hql="select distinct callover.className from CallOver callover where callover.student.studentClass.gradeId=:gradeId";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("gradeId",gradeId );
		@SuppressWarnings("unchecked")
		List<String> list = query.list();
		if(list.size()>0){
			return  list;
		}else{
			return null;
		}
		
	}
	@Override
	public List<CallOver> getStudentListByDGCC(String date1, Integer gradeID,
			String classTime, String className) {
		// TODO Auto-generated method stub
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date =  sdf.parse(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql = "select callOver from CallOver callOver where callOver.date=:date and " +
				" callOver.classTime=:classTime and callOver.className=:className and callOver.student.studentClass.gradeId = :gradeId";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("date", date);
		query.setParameter("classTime", classTime);
		query.setParameter("className", className);
		query.setParameter("gradeId", gradeID);
		@SuppressWarnings("unchecked")
		List<CallOver> list = query.list();
		if(list.size()<0){
			return null;
		}else{
			return list;
		}
		
	}
	@Override
	public void saveCallOverByBatch(List<CallOver> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			CallOver callOver = list.get(i);
			session.save(callOver);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}
	@Override
	public boolean updateCallOver( String classTime,
			String studentNumber, String status,String  date) {
		// TODO Auto-generated method stub
		String hql="update CallOver c set c.status=:status where   c.classTime=:classTime and c.student.id=:studentNumber and c.date=:date ";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("status", status);
		query.setParameter("classTime", classTime);
		query.setParameter("studentNumber", studentNumber);
		query.setParameter("date", date);
		int b = query.executeUpdate();
		if(b>0){
			return true;
		}else{
		return false;
		}
	}
	
	/**
	 * @author JSY
	 * @function 拿到所有学生考勤情况的详细信息（包括迟到和旷课的）
	 * @revise time（修改时间） 2016.4.1
	 */
	@Override
	public List<CallOverUtil> getCallOverByStudentNumber(String studentNumber) {
		// TODO Auto-generated method stub
	  //String hql="select new com.b505.bean.util.CallOverUtil(c.date,c.teacherName,c.className)from CallOver c where c.student.id=:studentNumber and c.status=:status";
	  //修改过的	
		String hql="select new com.b505.bean.util.CallOverUtil(c.date,c.teacherName,c.className)from CallOver c where c.student.id=:studentNumber";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("studentNumber", studentNumber);
	  //query.setParameter("status", "旷课");
		@SuppressWarnings("unchecked")
		List<CallOverUtil> list = query.list();
		if(list.size()>0){
			return list;
		}else{
		return null;
		}
	}
	@Override
	public CallOver getStudentCallOver(String date, String classTime,
			String studentNumber) {
		// TODO Auto-generated method stub
		String hql="select c from CallOver c where c.date=:date and c.classTime=:classTime and c.student.id=:studentNumber";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("date", date);
		query.setParameter("classTime", classTime);
		query.setParameter("studentNumber", studentNumber);
		@SuppressWarnings("unchecked")
		List<CallOver> callOvers = query.list();
		if(callOvers.size()>0){
			return callOvers.get(0);
		}else{
			return null;
		}
	}
	@Override
	public int deleteCallOver(String date, String classTime,
			String studentNumber) {
		// TODO 
		String hql = "delete  from CallOver co where co.student.id=:studentNumber and co.classTime=:classTime and co.date=:date";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("date", date);
		query.setParameter("studentNumber", studentNumber);
		query.setParameter("classTime", classTime);
		int b = query.executeUpdate();
		if(b==1){
			return b;
		}
		return 0;
	}
	
	/**
	 * @author lyf
	 * @time 2015.11.19
	 * @功能：领导按日期查询考勤
	 */
	@Override
	public List<CallOverUtils> getCallOverBycollegeNameAndTime(String collgeName,String STime,String ETime){

		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date1 = null;
		java.util.Date date2 = null;
		try {
			date1 =  sdf.parse(STime);
			date2 =  sdf.parse(ETime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String hql="select new com.b505.bean.util.CallOverUtils(c.student.studentClass.yearClass,c.student.studentClass.profession,c.student.studentClass.classId,c.student.id,c.student.studentName,c.teacherName) from CallOver c where c.student.college.collegeName=:collegeName and c.status=:status and term=:term and c.date between :date1 and :date2";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("collegeName", collgeName);
		query.setParameter("status", "旷课");
		//手动设定查询本学期
		query.setParameter("term", "第一学期");
		query.setParameter("date1", date1);
		query.setParameter("date2", date2);
		@SuppressWarnings("unchecked")
		List<CallOverUtils> list = query.list();
		if(list.size()>0){
			return list;
		}else{
		return null;
		}
		
	}
	
	/**
	 * @author JSY
	 * @time 2016.4.5
	 * @功能：辅导员按日期查询考勤
	 * @代码修改
	 */
	@Override
	public List<CallOverUtils> getCountUtilsByGradeAndTime(String yearClass,String profession,String classId,
			String stime,String etime){

		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date1 = null;
		java.util.Date date2 = null;
		try {
			date1 =  sdf.parse(stime);
			date2 =  sdf.parse(etime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//String hql="select new com.b505.bean.util.CallOverUtils(c.student.studentClass.yearClass,c.student.studentClass.profession,c.student.studentClass.classId,c.student.id,c.student.studentName,c.teacherName) from CallOver c where c.student.studentClass.yearClass=:yearClass and c.student.studentClass.profession=:profession and c.student.studentClass.classId=:classId and c.status=:status and c.term=:term and c.date between :date1 and :date2";
		//修改的，能查看所有考勤情况
		String hql="select new com.b505.bean.util.CallOverUtils(c.student.studentClass.yearClass,c.student.studentClass.profession,c.student.studentClass.classId,c.student.id,c.student.studentName,c.teacherName,c.status) from CallOver c where c.student.studentClass.yearClass=:yearClass and c.student.studentClass.profession=:profession and c.student.studentClass.classId=:classId and c.date between :date1 and :date2";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("yearClass", yearClass);
		query.setParameter("profession", profession);
		query.setParameter("classId", classId);
		//query.setParameter("status", "旷课");
		//手动设定查询本学期
		//query.setParameter("term", "第一学期");
		query.setParameter("date1", date1);
		query.setParameter("date2", date2);
		@SuppressWarnings("unchecked")
		List<CallOverUtils> list = query.list();
		if(list.size()>0){
			return list;
		}else{
		return null;
		}
		
	}
	/**
	 * @author JSY
	 * @function 页面初始化时加载所有的考勤数据
	 * @time 2016.3.31
	 */
	@Override
	public List<CallOverUtils> getAllCountUtils()
	{
		String hql="select new com.b505.bean.util.CallOverUtils(c.student.studentClass.yearClass,c.student.studentClass.profession,c.student.studentClass.classId,c.student.id,c.student.studentName,c.teacherName,c.status) from CallOver as c";
	    Query query = getCurrentSession().createQuery(hql);
	    @SuppressWarnings("unchecked")
		List<CallOverUtils> list = query.list();
	    if (list.size()>0)
		{
			return list;
		}else {
			return null;
		}
	}
	
}
