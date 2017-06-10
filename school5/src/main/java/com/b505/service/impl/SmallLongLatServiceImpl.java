package com.b505.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.LongLat;
import com.b505.bean.SmallLongLatImage;
import com.b505.dao.IBaseDao;
import com.b505.dao.ISmallLongLatImageDao;
import com.b505.service.ISmallLongLatImageService;
@Service("SmallLongLatImageService")
public class SmallLongLatServiceImpl extends BaseServiceImpl<SmallLongLatImage> implements ISmallLongLatImageService {
	private ISmallLongLatImageDao ismallLongLatImageDao;
	@Autowired
	@Qualifier("ISmallLongLatImageDao")
	@Override
	public void setIBaseDao(IBaseDao<SmallLongLatImage> ismallLongLatImageDao) {
		// TODO Auto-generated method stub
		this.baseDao = ismallLongLatImageDao;
		this.ismallLongLatImageDao=(ISmallLongLatImageDao)ismallLongLatImageDao;
	}
	@Override
	public void save(String imgurl, LongLat longLat, String imageName, byte[] by) {
		// TODO Auto-generated method stub
		ismallLongLatImageDao.save(imgurl, longLat, imageName, by);
	}
	
}
