package com.b505.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.Resc;
import com.b505.dao.IBaseDao;
import com.b505.dao.IRescDao;
import com.b505.service.IRescService;
@Service("RescService")
public class RescServiceImpl extends BaseServiceImpl<Resc> implements IRescService {
	private IRescDao irescDao;
	@Autowired
	@Qualifier("IRescDao")
	@Override
	public void setIBaseDao(IBaseDao<Resc> irescDao) {
		// TODO Auto-generated method stub
		this.baseDao = irescDao;
		this.irescDao = (IRescDao)irescDao;
	}
	@Override
	public void saveRescByBatch(List<Resc> list) {
		// TODO Auto-generated method stub
		irescDao.saveRescByBatch(list);
	}
	@Override
	public void deleteRescByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		irescDao.deleteRescByBatch(list);
		
	}
	@Override
	public void updateRescByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		irescDao.updateRescByBatch(list);
	}
	
}
