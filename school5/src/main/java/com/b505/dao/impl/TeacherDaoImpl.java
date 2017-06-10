package com.b505.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.Teacher;
import com.b505.bean.util.TeacherRegisterUtil;
import com.b505.bean.util.TeacherUtil;
import com.b505.dao.ITeacherDao;

@Repository(value="ITeacherDao")
public class TeacherDaoImpl extends BaseDaoImpl<Teacher>  implements ITeacherDao{
	

	@Override
	public Teacher getTeacherByNickname(String nickName){
		String hql = "from Teacher teacher where teacher.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		if(query.list().size()>0){
			return (Teacher)query.list().get(0);
		}else {
			return null;
		}
	}
	@Override
	public List<Teacher> getTeacherListByCollege(String collegeName){
		String hql = "select teacher from Teacher teacher where teacher.college.collegeName=:collegeName and teacher.user.role=:role";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("collegeName", collegeName);
		query.setParameter("role", "Role_Teacher");
		@SuppressWarnings("unchecked")
		List<Teacher> list =query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public int deleteTeacherByPhone(String phone) {
		// TODO Auto-generated method stub
		String hql = "delete from Teacher teacher where teacher.teacherPhone = :phone";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("phone", phone);
		int b = query.executeUpdate();
		return b;
	}
	@Override
	public List<Teacher> getHeadTeacherListByCollege(String collegeName) {
		String hql = "select teacher from Teacher teacher where teacher.college.collegeName=:collegeName and teacher.user.role=:role";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("collegeName", collegeName);
		query.setParameter("role", "Role_HeadTeacher");
		@SuppressWarnings("unchecked")
		List<Teacher> list =query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<Teacher> getHeadteacherList() {
		String hql = "select teacher from Teacher teacher where  teacher.user.role=:role";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("role", "Role_HeadTeacher");
		@SuppressWarnings("unchecked")
		List<Teacher> list =query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherUtil> getTeacherUtilByRole() {
		// TODO Auto-generated method stub
		String hql = "select new com.b505.bean.util.TeacherUtil(t.id,t.teacherName,t.teacherPhone,t.teacherCardId,t.user.nickName,t.user.password) from Teacher t where t.user.role=:role";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("role","Role_Teacher" );
		List<TeacherUtil> list = new ArrayList<TeacherUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public void deleteTeacherByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		Session  session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Teacher teacher = new Teacher();
			teacher.setId(list.get(i));
			session.delete(teacher);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
		
	}
	@Override
	public void updateTeacherByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session  = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Teacher teacher = this.get((Integer)list.get(i).get("id"));
			teacher.setTeacherCardId((String)list.get(i).get("teacherCardId"));
			teacher.setTeacherName((String)list.get(i).get("teacherName"));
			teacher.setTeacherPhone((String)list.get(i).get("teacherPhone"));
			session.update(teacher);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherUtil> getHeadTeacherUtilByRole(String collegeName) {
		String hql = "select new com.b505.bean.util.TeacherUtil(t.id,t.teacherName,t.teacherPhone,t.teacherCardId,t.user.nickName,t.user.password) from Teacher t where t.college.collegeName=:collegeName and t.user.role=:role";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("role","Role_HeadTeacher" );
		query.setParameter("collegeName", collegeName);
		List<TeacherUtil> list = new ArrayList<TeacherUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	
	}
	@Override
	public TeacherRegisterUtil getTeacherRegisterUtilByNickName(String nickName) {
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.TeacherRegisterUtil(t.teacherName,t.teacherPhone,t.teacherCardId)from Teacher t where t.user.nickName=:nickName and t.user.role=:role";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		query.setParameter("role", "Role_Teacher");
		@SuppressWarnings("unchecked")
		List<TeacherRegisterUtil> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Teacher getHeadTeacherRegisterUtilByNickName(
			String nickName) {
		// TODO Auto-generated method stub
		String hql="select  t from Teacher t where t.user.nickName=:nickName and t.user.role=:role";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		query.setParameter("role", "Role_HeadTeacher");
		List<Teacher> list = new ArrayList<Teacher>();
		list = query.list();
		if(list.size()>0){
			
			return list.get(0);
		}else{
			return null;
		}
		
	}
	@Override
	public boolean updatePhoneByNickName(String phone, String nickName) {
		// TODO Auto-generated method stub
		String hql="update Teacher t set t.teacherPhone=:teacherPhone where t.user.nickName=:nickName ";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("teacherPhone", phone);
		query.setParameter("nickName", nickName);
		int b = query.executeUpdate();
		if(b>0){
			return true;
		}else{
			return false;
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public String getTeacherNameByNickName(String nickname) {
		// TODO Auto-generated method stub
		String hql="select t.teacherName from Teacher t where t.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickname);
		List<String> list = new ArrayList<String>();
		list = query.list();
		if(list.size()>0){
			return (String) list.get(0);
		}
		
		return null;
	}
	
	@Override
	//得到辅导员的姓名和手机号
	public  Map<String, Object> getTeaNameByTid(int tid){
		
		String hql = "select new com.b505.bean.util.TeacherRegisterUtil(t.teacherName,t.teacherPhone) from Teacher t where t.id=:tid";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("tid", tid);
		if(query.list().size()>0){
			TeacherRegisterUtil tUtil=	(TeacherRegisterUtil)query.list().get(0);
			Map<String, Object>map=new HashMap<String, Object>();
			map.put("teacherName", tUtil.getTeacherName());
			map.put("teacherPhone", tUtil.getTeacherPhone());		
			
			return map;
		}else {
			return null;
		}	
	}
	
	@Override
	public Teacher getTeacherByTnaem(String teachername){
		
		String hql = "from Teacher t where t.teacherName=:teachername";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("teachername", teachername);
		if(query.list().size()>0){
			return (Teacher)query.list().get(0);
		}else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	//得到 辅导员的姓名
	public String getTeaNameByTid1(int tid){
		
		String hql="select new com.b505.bean.util.TeacherRegisterUtil(t.teacherName) from Teacher t where t.id=:tid";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("tid", tid);
		List<TeacherRegisterUtil> list = new ArrayList<TeacherRegisterUtil>();
		list = query.list();
		//System.out.println("测试:"+list);
		if(list.size()>0){
			TeacherRegisterUtil teacherName=list.get(0);
			return teacherName.getTeacherName();
		}
		return null;
		
	}
}
