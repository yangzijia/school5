package com.b505.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.ChildrenMenu;
import com.b505.bean.util.ChildrenMenuUtil;
import com.b505.dao.IChildrenMenuDao;

@Repository(value="IChildrenMenuDao")
public class ChildrenMenuDaoImpl extends BaseDaoImpl<ChildrenMenu> implements IChildrenMenuDao {

	@Override
	public List<ChildrenMenu> getChildrenMenuByparent(String parentMenuName) {
		// TODO Auto-generated method stub
		String hql = "select childrenMenu from ChildrenMenu childrenMenu where childrenMenu.parentMenu.menuName=:parentMenuName ";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("parentMenuName", parentMenuName);
		@SuppressWarnings("unchecked")
		List <ChildrenMenu>list = query.list();
		if(list.size()>0){
			return list;
			
		}else{
			return null;
		}
		
	}

	@Override
	public void deleteChildrenMenuByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			ChildrenMenu childrenMenu = new ChildrenMenu();
			childrenMenu.setId(list.get(i));
			session.delete(childrenMenu);
		}
		
	}

	@Override
	public void saveChildrenMenuByBatch(List<ChildrenMenu> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			
			ChildrenMenu childrenMenu = list.get(i);
			session.save(childrenMenu);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public void updateChildrenMenuByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size() ;i++){
			ChildrenMenu childrenMenu = this.get((Integer) list.get(i).get("id"));
			childrenMenu.setChildrenmenuName((String)list.get(i).get("childrenmenuName"));
			childrenMenu.setUrl((String)list.get(i).get("url"));
			session.update(childrenMenu);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<ChildrenMenu> getByUrlOrByChildrenMenuname(String url){
		List<ChildrenMenu> list = new ArrayList<ChildrenMenu>();
		try {
			String hql = "select childrenMenu from ChildrenMenu childrenMenu where  childrenMenu.url = :url";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("url", url);
			list= query.list();
			System.out.println(list);
		} catch (Exception e) {
			System.out.println("1"+e);
		}
		if(list.size()>0){
			return list;
		}else {
			return null;
		}
	}

	@Override
	public List<ChildrenMenuUtil> getAllChilderUtils() {
		// TODO Auto-generated method stub
		String sql="select new com.b505.bean.util.ChildrenMenuUtil(c.id,c.childrenmenuName,c.url,c.remark) from ChildrenMenu c";
		@SuppressWarnings("unchecked")
		List <ChildrenMenuUtil> list = getCurrentSession().createQuery(sql).list();
		if(list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public List<ChildrenMenu> getChildrenMenusByRoleAndParent(String roleName,String parentMenu) {
		// TODO Auto-generated method stub
		String sql="select c from ChildrenMenu c join c.roleNames r where r.roleName=:roleName and c.parentMenu.menuName=:parentMenu ";
		Query query = getCurrentSession().createQuery(sql);
		query.setParameter("roleName", roleName);
		query.setParameter("parentMenu", parentMenu);
		@SuppressWarnings("unchecked")
		List<ChildrenMenu> list = query.list();
		
		if(list.size()>0){
			return list;
		}
		return null;
	}
}
