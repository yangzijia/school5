package com.b505.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.NewsImage;
import com.b505.dao.IBaseDao;
import com.b505.dao.INewsImageDao;
import com.b505.service.INewsImageService;
@Service("NewsImageService")
public class NewsImageServiceImpl extends BaseServiceImpl<NewsImage> implements INewsImageService {
	@SuppressWarnings("unused")
	private INewsImageDao inewsImageDao;
	@Autowired
	@Qualifier("INewsImageDao")
	@Override
	public void setIBaseDao(IBaseDao<NewsImage> inewsImageDao) {
		// TODO Auto-generated method stub
		this.baseDao = inewsImageDao;
		this.inewsImageDao = (INewsImageDao)inewsImageDao;
	}
	
	
}
