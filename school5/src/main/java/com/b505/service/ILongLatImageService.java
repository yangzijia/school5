package com.b505.service;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatImage;

public interface ILongLatImageService extends IBaseService<LongLatImage>{
public void save(String imgurl, LongLat longLat, String imageName,
			byte[] by);

}
