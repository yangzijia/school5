
package com.b505.service.impl;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.Section;
import com.b505.dao.IBaseDao;

import com.b505.dao.ISectionDao;
import com.b505.service.ISectionService;

@Service("SectionService")
public class SectionServiceImpl extends BaseServiceImpl<Section> implements ISectionService{
	private ISectionDao isectionDao;
	@Autowired
	@Qualifier("ISectionDao")
	@Override
	public void setIBaseDao(IBaseDao<Section> isectionDao) {
		// TODO Auto-generated method stub
		this.baseDao = isectionDao;
		this.isectionDao = (ISectionDao)isectionDao;
	}
	@Override
	public Boolean deleteSectionBySectionName(Integer sectionId,
			String sectionName) {
		// TODO Auto-generated method stub
		return  isectionDao.deleteSectionBySectionName(sectionId, sectionName);
	}
	@Override
	public void saveSectionByBatch(List<Section> list) {
		// TODO Auto-generated method stub
		isectionDao.saveSectionByBatch(list);
	}
	@Override
	public void updateSectionByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		isectionDao.updateSectionByBatch(list);
	}
	@Override
	public Section getSection1(String section)
	{
		// TODO Auto-generated method stub
		return isectionDao.getSection1(section);
	}
	
}




