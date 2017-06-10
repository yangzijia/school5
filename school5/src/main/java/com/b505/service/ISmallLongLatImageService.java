package com.b505.service;

import com.b505.bean.LongLat;
import com.b505.bean.SmallLongLatImage;

public interface ISmallLongLatImageService extends IBaseService<SmallLongLatImage> {
	public void save(String imgurl, LongLat longLat, String imageName,byte[] by);

}
