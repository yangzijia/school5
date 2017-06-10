package com.b505.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.b505.bean.CallOverCount;
import com.b505.bean.util.CallOverCountUtil;
import com.b505.bean.util.CallOverCountUtils;
import com.b505.dao.ICallOverCountDao;

@Repository(value="ICallOverCountDao")
public class CallOverCountDaoImpl extends BaseDaoImpl<CallOverCount> implements ICallOverCountDao{

	@Override
	public CallOverCount getCallOverCountBysnumber(String studentNumber) {
		String hql = "select cc from CallOverCount cc where cc.student.id=:studentNumber";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("studentNumber", studentNumber);
		@SuppressWarnings("unchecked")
		List<CallOverCount> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	//领导查询考勤
	@Override
	public List<CallOverCountUtil> getCountUtilsByCollege(String collgeName) {
		// TODO Auto-generated method stub
		String hql=" select new com.b505.bean.util.CallOverCountUtil(c.student.id,c.student.studentName,c.truant,c.student.user.imgUrl)from CallOverCount c where c.student.college.collegeName=:collegeName  and c.truant>0  order by c.truant desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("collegeName", collgeName);
		@SuppressWarnings("unchecked")
		List<CallOverCountUtil> list = query.list();
		if(list.size()>0){
			return list;
		}else{
		return null;
		}
	}

	//需要修改,辅导员按日期查询考勤
	@Override
	public List<CallOverCountUtil> getCountUtilsByGrade(String yearClass,
			String profession, String classId) {
		// TODO Auto-generated method stub
		String hql=" select new com.b505.bean.util.CallOverCountUtil(c.student.id,c.student.studentName,c.truant,c.student.user.imgUrl)from CallOverCount c where c.student.studentClass.yearClass=:yearClass and c.student.studentClass.profession=:profession and c.student.studentClass.classId=:classId and c.truant>0  order by c.truant desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("yearClass", yearClass);
		query.setParameter("profession", profession);
		query.setParameter("classId", classId);
		@SuppressWarnings("unchecked")
		List<CallOverCountUtil> list = query.list();
		if(list.size()>0){
			return list;
		}else{
		return null;
		}
	}

	@Override
	public Long getTotalCountByCollegeAndStatus(String collegeName) {
		// TODO Auto-generated method stub
		String hql ="select count(c) from CallOverCount c where c.student.college.collegeName=:collegeName and c.truant > 0 ";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("collegeName", collegeName);
		Long total =(Long)query.uniqueResult();
		return total;
	}

	@Override
	public List<CallOverCountUtil> getCountUtilsByPagerAndCollege(
			String collgeName, Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql=" select new com.b505.bean.util.CallOverCountUtil(c.student.id,c.student.studentName,c.truant,c.student.user.imgUrl)from CallOverCount c where c.student.college.collegeName=:collegeName and c.truant>0 order by c.truant desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("collegeName", collgeName);
		@SuppressWarnings("unchecked")
		List<CallOverCountUtil> list =query.setFirstResult((currentPage-1)*pageSize)
				.setMaxResults(pageSize).list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
		
	}

	@Override
	public boolean updateCallOver(String date, String className,
			String classTime, String studentNumber,String status) {
		// TODO Auto-generated method stub
		String sql="update CallOver c set c.status=:status where c.date=:date and c.className=:className and c.classTime=：classTime and" +
				" c.student.studentNumber=:studentNumber ";
		Query query = getCurrentSession().createQuery(sql);
		query.setParameter("date", date);
		query.setParameter("className", className);
		query.setParameter("classTime", classTime);
		query.setParameter("status", status);
		query.setParameter("studentNumber", studentNumber);
		int b = query.executeUpdate();
		if(b>0){
			return true;
		}
		return false;
	}

	/**
	 * @author lyf
	 * @time 2015-11-20
	 * @function 辅导员查询考勤预警信息    
	 */
	@Override
	public List<CallOverCountUtils> getWarnCountUtilsByGrade(String yearClass,String profession,String classId ){
		// TODO Auto-generated method stub
		String hql=" select new com.b505.bean.util.CallOverCountUtils(c.student.id,c.student.studentName,c.truant,c.lateNumber)from CallOverCount c where c.student.studentClass.yearClass=:yearClass and c.student.studentClass.profession=:profession and c.student.studentClass.classId=:classId and c.lateNumber>10 or c.truant>10  order by c.truant desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("yearClass", yearClass);
		query.setParameter("profession", profession);
		query.setParameter("classId", classId);
		@SuppressWarnings("unchecked")
		List<CallOverCountUtils> list = query.list();
		if(list.size()>0){
			return list;
		}else{
		return null;
		}
	}
}
