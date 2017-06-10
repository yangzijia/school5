package com.b505.dao.impl;

import java.util.List;
import java.util.Map;



import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.Resc;
import com.b505.dao.IRescDao;
@Repository(value="IRescDao")
public class RescDaoImpl extends BaseDaoImpl<Resc> implements IRescDao {

	@Override
	public void saveRescByBatch(List<Resc> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Resc resc = list.get(i);
			session.save(resc);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public void deleteRescByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Resc resc = new Resc();
			resc.setId(list.get(i));
			session.delete(resc);
			if(i%20==0){
				session.flush();
				session.clear();
			}
			
		}
		
	}

	@Override
	public void updateRescByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Resc resc = this.get((Integer)list.get(i).get("id"));
			resc.setName((String)list.get(i).get("name"));
			resc.setRemark((String)list.get(i).get("remark"));
			session.update(resc);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

}
