package com.b505.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.Section;
import com.b505.dao.ISectionDao;
@Repository(value="ISectionDao")
public class SectionDaoImpl extends BaseDaoImpl<Section> implements ISectionDao{

	@Override
	public void saveSectionByBatch(List<Section> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i=0;i<list.size();i++){
			Section section = list.get(i);
			session.save(section);
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
		
	}

	@Override
	public Boolean deleteSectionBySectionName(Integer sectionId,String sectionName) {
		// TODO Auto-generated method stub
		String hql = "delete Section section where section.sectionId=:sectionId or section.sectionName=:sectionName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("sectionName", sectionName);
		query.setParameter("sectionId", sectionId);
		Boolean b = false;
		int c = query.executeUpdate();
		if(c==1){
			b = true;
		}
		return b;
	}

	@Override
	public void updateSectionByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for(int i = 0; i<list.size();i++){
			Section section = this.get((Integer)list.get(i).get("sectionId"));
			section.setSectionName((String)list.get(i).get("sectionName"));
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public Section getSection1(String section)
	{
		// TODO Auto-generated method stub
		String hql="from Section s where s.sectionName=:sectionName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("sectionName", section);
		@SuppressWarnings({ "unchecked", "unused" })
		List<Section>list=query.list();
		if (query.list().size()>0)
		{
			return (Section) query.list().get(0);
		}else {
			return null;
		}
		
	}


}
