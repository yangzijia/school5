package com.b505.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.RoleName;
import com.b505.bean.util.RoleNameUtil;
import com.b505.dao.IRoleNameDao;
@Repository(value="IRoleDao")
public class RoleNameDaoImpl extends BaseDaoImpl<RoleName> implements IRoleNameDao{

	@Override
	public void saveRoleNameByBatch(List<RoleName> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			RoleName roleName = list.get(i);
			session.save(roleName);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public void updateRoleNameByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			RoleName roleName= this.get((Integer)list.get(i).get("id"));
			roleName.setRoleName((String)list.get(i).get("roleName"));
			roleName.setRemark((String)list.get(i).get("remark"));
			session.update(roleName);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public void deleteRoleNameByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			RoleName roleName = new RoleName();
			roleName.setId(list.get(i));
			session.delete(roleName);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleNameUtil> getRoleNameUtils() {
		// TODO Auto-generated method stub
		String hql="select new com.b505.bean.util.RoleNameUtil(r.id,r.roleName,r.remark)from RoleName r";
		Query query = getCurrentSession().createQuery(hql);
		List<RoleNameUtil> list =  new ArrayList<RoleNameUtil>();
		list = query.list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}

}
