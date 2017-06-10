package com.b505.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.b505.bean.AppPushInfo;
import com.b505.bean.LoginUser;
import com.b505.dao.ILoginUserDao;
import com.b505.tools.DigestUtils;
import com.b505.tools.SSHA;

@Repository(value="ILoginUserDao")
public class LoginUserDaoImpl extends BaseDaoImpl<LoginUser>  implements ILoginUserDao{
	@Autowired
	private SSHA ssha;
	@Autowired
	private DigestUtils du;
	@Override
	public void deleteUserByBatch(List<LoginUser> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			LoginUser user = list.get(i);
			session.delete(user);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	

	@Override
	public void saveUserByBatch(List<LoginUser> list) {
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		// TODO Auto-generated method stub
		for(int i = 0;i<list.size();i++){
			LoginUser user = new LoginUser();
			user = list.get(i);
			session.save(user);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
		
		
	}

	@Override
	public void passwordRecBynickName(List<String> list)  {
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			LoginUser user = new LoginUser();
			user = this.get(list.get(i));
			System.out.println("user"+i+user);
			String password=null;
			try {
				password = du.digest("123456");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setPassword(password);
			session.update(user);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
		
	}

	@Override
	public boolean updatePasswordByNickName(String newPassword, String nickName) {
		// TODO Auto-generated method stub
		String hql="update LoginUser  user set user.password=:password where user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("password", newPassword);
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
	public String getnickNameByIt(String nickName){
		
		// TODO Auto-generated method stub
		String hql="select l.nickName from LoginUser l where l.nickName=:nickName";
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
	public LoginUser getLoginUserBynickName(String nickName){
		
		String hql = "from LoginUser l where l.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		if(query.list().size()>0){
			return (LoginUser)query.list().get(0);
		}else {
			return null;
		}
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function 客户端登录成功后返回角色信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getRoleBynickNamepassWord(String nickName,String password){
		// TODO Auto-generated method stub
		String hql="select l.role from LoginUser l where l.nickName=:nickName and l.password=:password";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		query.setParameter("password", password);
		List<String> list = new ArrayList<String>();
		list = query.list();
		if(list.size()>0){
			return list.get(0);
		}			
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getClientidByusername(String userName) {
		
		String hql="select l.clientid from LoginUser l where l.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", userName);
		List<String> list = new ArrayList<String>();
		list = query.list();
		if(list.size()>0){
			return list.get(0);
		}			
		return null;
	}

	@Override
	public void updateClientidToUser(LoginUser lu) {
		System.out.println("***********updateClientidToUser***************");
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		session.update(lu);
		session.flush();
		session.clear();
		
		/*String hql="update LoginUser set clientid=:clientid where nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("clientid", lu.getClientid());
		query.setParameter("nickName", lu.getNickName());*/
		
		/*Transaction trans=session.beginTransaction();
		String hql=”update User user set user.age=20 where user.age=18”;
		Query queryupdate=session.createQuery(hql);
		int ret=queryupdate.executeUpdate();
		trans.commit();*/
	}

	@Override
	public List<LoginUser> getUserByroles(String role1, String role2) {
		String hql = "FROM LoginUser WHERE clientid IS NOT NULL AND (role='"+role1+"' OR role='"+role2+"')";
		Query query = getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<LoginUser> list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}



	@Override
	public List<LoginUser> getUserByroles(String role1, String role2,
			String role3) {
		String hql = "FROM LoginUser WHERE clientid IS NOT NULL AND (role='"+role1+"' OR role='"+role2+"' OR role='"+role3+"')";
		Query query = getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<LoginUser> list = query.list();
		if(list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public void savepushinfo(AppPushInfo api) {
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		session.save(api);
		session.flush();
		session.clear();
	}



	@SuppressWarnings("unchecked")
	@Override
	public String getNameBysa_geter_no(String nickName) {
		System.out.println("运行了查询的方法");
		String hql = "select l.name from Leader l where l.user.nickName=:nickName";
		
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		List<String> list = new ArrayList<String>();
		list = query.list();
		System.out.println("查询结果：");
		if(list.size()>0){
			System.out.println(list.get(0));
			return list.get(0);
		}			
		return null;
	}



	@Override
	public List<AppPushInfo> findPushInfosByPush_geter(String userNickname) {
		String hql = "from AppPushInfo l where l.push_geter=:nickName or l.push_geter='allusers' order by id desc";
		
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", userNickname);
		@SuppressWarnings("unchecked")
		List<AppPushInfo> list = query.list();
		System.out.println("查询结果：");
		if(list.size()>0){
			System.out.println(list.get(0));
			return list;
		}			
		return null;
	}

	@Override
	public void deletePushinfo(AppPushInfo api) {
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		session.delete(api);
		session.flush();
		session.clear();
	}
}
