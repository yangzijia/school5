package com.b505.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.Roll_Alert;
import com.b505.bean.Student;
import com.b505.bean.StudentAsk;
import com.b505.bean.util.GradeUtil;
import com.b505.bean.util.StudentGid;
import com.b505.bean.util.StudentRegisterUtil;
import com.b505.bean.util.StudentUtil;
import com.b505.dao.IStudentDao;
@Repository(value="IStudentDao")
public class StudentDaoImpl extends BaseDaoImpl<Student>implements IStudentDao {
	

	@Override
	public Student getStudentByNickname(String userNickname){
		String hql = "from Student student where  student.user.nickName=:userNickname";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("userNickname", userNickname);
		if(query.list().size()>0){
			return (Student)query.list().get(0);
		}else {
			return null;
		}
	}
	
	@Override
	public List<Student> getStudentByGrade(Integer gradeId){
		String hql = "from Student student where student.studentClass.gradeId=:gradeId";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("gradeId", gradeId);
		@SuppressWarnings("unchecked")
		List <Student> list =query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<Student> getStudentByGradeAll(String yearClass,String profession,String classId){
		String hql = "from Student student where student.studentClass.yearClass = :yearClass and " +
				" student.studentClass.profession = :profession and student.studentClass.classId = :classId";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("yearClass", yearClass);
		query.setParameter("profession", profession);
		query.setParameter("classId", classId);
		@SuppressWarnings("unchecked")
		List<Student> list = query.list();
		if(list.size()>0){
			return list;
			}else{
				return null;
		}
	}

	@Override
	public void deleteStudentByBatch(List<String> list) {
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i = 0;i<list.size();i++){
			Student student = new Student();
			student.setId(list.get(i));
			session.delete(student);
			if(i%20==0){
				session.flush();
				session.close();
			}
		}
	}
	
	@Override
	public void   updateStudentByBatch(List<Student> list){
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			session.update(list.get(i));
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentUtil> getStudentUtilsByGrade(String yearClass,String profession,String classId) {
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.StudentUtil(s.user.nickName,s.id,s.studentName,s.studentPhone) from Student s where s.studentClass.yearClass = :yearClass and " +
				" s.studentClass.profession = :profession and s.studentClass.classId = :classId";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("yearClass", yearClass);
		query.setParameter("profession", profession);
		query.setParameter("classId", classId);
		List<StudentUtil> list = new ArrayList<StudentUtil>();
		list = query.list();
		if(list!=null){
			return list;
		}else{
		return null;
		}
	}

	@Override
	public List<StudentUtil> getStudentUtilUnregistered() {
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.StudentUtil(s.id,s.studentName) from Student s where s.user = null  ";
		Query query = getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List <StudentUtil> list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}	
		
	@Override
	public StudentRegisterUtil getStudentRegisterInforByNickName(String nickName){
		String hql = "select new com.b505.bean.util.StudentRegisterUtil(s.id,s.studentName,s.studentPhone,s.studentCardId,s.college.collegeName,s.studentClass.yearClass,s.studentClass.profession,s.studentClass.classId,s.user.imgUrl)from Student s where s.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		@SuppressWarnings("unchecked")
		List<StudentRegisterUtil> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public boolean updatePhoneByNickName(String phone, String nickName) {
		// TODO Auto-generated method stub
		String hql ="update Student s set s.studentPhone=:phone where s.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("phone", phone);
		query.setParameter("nickName", nickName);
		int b = query.executeUpdate();
		if(b>0){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public GradeUtil getGradeUtilByStudentNumber(String studentNumber){
		// TODO Auto-generated method stub
		String hql = "select new com.b505.bean.util.GradeUtil(s.studentClass.yearClass,s.studentClass.profession,s.studentClass.classId) from Student s where s.id=:studentNumber";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("studentNumber", studentNumber);
		@SuppressWarnings("unchecked")
		List<GradeUtil> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	@Override
	public StudentGid getGidByNumber(String number){
		
		String hql = "select new com.b505.bean.util.StudentGid(s.studentClass.gradeId) from Student s where s.id=:number";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("number", number);
		if(query.list().size()>0){
			return (StudentGid)query.list().get(0);
		}else {
			return null;
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAsk> getStudentAskBynickNameAndtype(String nickName,String type){
		
		//注意：此处type为数据库中ask表中的status字段，不是type字段！！！！！即：请假状态
		String hql = "select sAsk from StudentAsk sAsk where sAsk.student.user.nickName = :nickName and sAsk.status = :type";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		query.setParameter("type", type);
		List<StudentAsk> list = new ArrayList<StudentAsk>();
		list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	/*@Override
	public StudentAsk getstudentAskDetailBynickName(String nickName){
		
		String hql = "from StudentAsk sask where  sask.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		if(query.list().size()>0){
			return (StudentAsk)query.list().get(0);
		}else {
			return null;
		}
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public String getstudentNameBynickName(String nickName){
		
		String hql="select s.studentName from Student s where s.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		List<String> list = new ArrayList<String>();
		list = query.list();
		if(list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
	@Override
	public StudentAsk getstudentAskDetailByaskId(int askid){
		
		String hql = "from StudentAsk sask where sask.askid=:askid";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("askid", askid);
		if(query.list().size()>0){
			return (StudentAsk)query.list().get(0);
		}else {
			return null;
		}
	}
	/*
	 * @功能：在数据库中查询预警成绩
	 * @功能说明：在数据库中查询预警成绩
	 * @作者：JSY
	 * @创建时间：2016.5.20
	 */
	@Override
	public List<Roll_Alert> getAlertByNickname(String nickName)
	{
		// TODO Auto-generated method stub
		String hql = "from Roll_Alert alert where alert.student.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		@SuppressWarnings("unchecked")
		List<Roll_Alert> list=query.list();
		if(query.list().size()>0){
			return  list;
		}else {
			return null;
		}
	}

	@Override
	public GradeUtil getGradeBystudentNumber(String studentNumber)
	{
		// TODO Auto-generated method stub
		String hql = "select new com.b505.bean.util.GradeUtil(s.studentClass.yearClass,s.studentClass.profession,s.studentClass.classId) from Student s where s.id=:studentNumber";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("studentNumber", studentNumber);
		@SuppressWarnings("unchecked")
		List<GradeUtil> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Roll_Alert getRoll_AlertBystudentNumber(String studentNumber,
			String studentName)
	{
		// TODO Auto-generated method stub
		String hql="from Roll_Alert r where r.student.id=:studentNumber and r.name=:studentName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("studentNumber", studentNumber);
		query.setParameter("studentName", studentName);
		if(query.list().size()>0){
			return (Roll_Alert)query.list().get(0);
		}else {
			return null;
		}
	}
	
}
