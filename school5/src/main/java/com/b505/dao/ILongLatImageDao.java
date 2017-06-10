package com.b505.dao;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatImage;

public interface ILongLatImageDao extends IBaseDao<LongLatImage>{

	public void save(String imgurl, LongLat longLat, String imageName,byte[] by);

}
