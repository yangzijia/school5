package com.b505.dao.impl;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatImage;
import com.b505.dao.ILongLatImageDao;

@Repository(value="ILongLatImageDao")
public class LongLatImageDaoImpl extends BaseDaoImpl<LongLatImage> implements ILongLatImageDao{

	@Override
	public void save(String imgurl, LongLat longLat, String imageName,byte[] by) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		LongLatImage latImage = new LongLatImage();
		latImage.setImage(session.getLobHelper().createBlob(by));
		latImage.setImageName(imageName);
		latImage.setImgurl(imgurl);
		latImage.setLongLat(longLat);
		session.save(latImage);
		session.flush();
		session.clear();
	}

	
}
