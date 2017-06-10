package com.b505.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.Administrator;
import com.b505.bean.util.AdministratorUtil;
import com.b505.dao.IAdministratorDao;
@Repository(value="IAdministratorDao")
public class AdministratorDaoImpl extends BaseDaoImpl<Administrator> implements IAdministratorDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AdministratorUtil> getAdministratorUtils() {
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.AdministratorUtil(a.id,a.name,a.phone,a.user.nickName) from Administrator a  ";
		Query query = getCurrentSession().createQuery(hql);
		List<AdministratorUtil> list = new ArrayList<AdministratorUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
		
	}

	@Override
	public void deleteAdministratorByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Administrator administrator = new Administrator();
			administrator.setId(list.get(i));
			session.delete(administrator);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public void updateAdministratorByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Administrator administrator = this.get((Integer)list.get(i).get("id"));
			administrator.setPhone((String)list.get(i).get("phone"));
			administrator.setName((String)list.get(i).get("name"));
			session.update(administrator);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public void saveAdministratorBayBatch(List<Administrator> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Administrator administrator = list.get(i);
			session.save(administrator);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
		
	}

	@Override
	public Administrator getAdministratorBynickName(String nickName) {
		// TODO Auto-generated method stub
		String sql ="select a from Administrator a where a.user.nickName=:nickName ";
		Query query = getCurrentSession().createQuery(sql);
		query.setParameter("nickName", nickName);
		@SuppressWarnings("unchecked")
		List<Administrator> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean updatePhoneByNickName(String phone, String nickName) {
		// TODO Auto-generated method stub
		String hql = "update Administrator a set a.phone = :phone where a.user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		query.setParameter("phone", phone);
		int b = query.executeUpdate();
		if(b>0){
			return true;
		}else{
			return false;
		}
		
	}
	
}
