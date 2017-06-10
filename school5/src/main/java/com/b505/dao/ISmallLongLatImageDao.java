package com.b505.dao;

import com.b505.bean.LongLat;
import com.b505.bean.SmallLongLatImage;

public interface ISmallLongLatImageDao extends IBaseDao<SmallLongLatImage>{
	public void save(String imgurl, LongLat longLat, String imageName,byte[] by);
}
