package com.b505.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatVoice;
import com.b505.dao.IBaseDao;
import com.b505.dao.ILongLatVoiceDao;
import com.b505.service.ILongLatVoiceService;
@Service("LongLatVoiceService")
public class LongLatVoiceServiceImpl extends BaseServiceImpl<LongLatVoice> implements ILongLatVoiceService {
	private ILongLatVoiceDao ilongLatVoiceDao;
	@Autowired
	@Qualifier("ILongLatVoiceDao")
	@Override
	public void setIBaseDao(IBaseDao<LongLatVoice> ilongLatVoiceDao) {
		// TODO Auto-generated method stub
		this.baseDao = ilongLatVoiceDao;
		this.ilongLatVoiceDao = (ILongLatVoiceDao)ilongLatVoiceDao;
	}
	@Override
	public void save(String voiceUrl, LongLat longLat, String voiceName,byte[] by)
	{
		ilongLatVoiceDao.save(voiceUrl, longLat, voiceName, by);
	}
	
	
}
