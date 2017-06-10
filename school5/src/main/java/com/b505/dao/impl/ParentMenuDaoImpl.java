package com.b505.dao.impl;



import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.ParentMenu;
import com.b505.bean.util.ParentMenuUtil;
import com.b505.dao.IParentMenuDao;
@Repository(value="IParentMenuDao")
public class ParentMenuDaoImpl extends BaseDaoImpl<ParentMenu> implements IParentMenuDao {

	@Override
	public void deleteParentMenuByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			ParentMenu parentMenu = new ParentMenu();
			parentMenu.setId(list.get(i));
			session.delete(parentMenu);
		}
		
	}

	@Override
	public void saveParentMenuByBatch(List<ParentMenu> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			ParentMenu parentMenu = list.get(i);
			session.save(parentMenu);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public void updateParentMenuByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size() ;i++){
			ParentMenu parentMenu = new ParentMenu();
			parentMenu.setId((Integer) list.get(i).get("id"));
			parentMenu.setMenuName((String)list.get(i).get("menuName"));
			session.update(parentMenu);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public List<ParentMenuUtil> getParentMenuUtils() {
		// TODO Auto-generated method stub
		String sql="select new com.b505.bean.util.ParentMenuUtil(p.id,p.menuName)from ParentMenu p";
		@SuppressWarnings("unchecked")
		List<ParentMenuUtil> parentMenuUtils = getCurrentSession().createQuery(sql).list();
		if(parentMenuUtils.size()>0){
			return parentMenuUtils;
		}
		return null;
	}


}
