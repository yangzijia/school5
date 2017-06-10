package com.b505.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatImage;
import com.b505.dao.IBaseDao;
import com.b505.dao.ILongLatImageDao;
import com.b505.service.ILongLatImageService;
@Service("LongLatImageService")
public class LongLatImageServiceImpl extends BaseServiceImpl<LongLatImage> implements ILongLatImageService {
	private ILongLatImageDao ilongLatImageDao;
	@Autowired
	@Qualifier("ILongLatImageDao")
	@Override
	public void setIBaseDao(IBaseDao<LongLatImage> ilongLatImageDao) {
		// TODO Auto-generated method stub
		this.baseDao = ilongLatImageDao;
		this.ilongLatImageDao = (ILongLatImageDao)ilongLatImageDao;
	}
	@Override
	public void save(String imgurl, LongLat longLat, String imageName,
			byte[] by){
		ilongLatImageDao.save(imgurl, longLat, imageName, by);

	}
}
