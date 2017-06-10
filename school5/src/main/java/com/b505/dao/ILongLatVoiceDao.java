package com.b505.dao;


import com.b505.bean.LongLat;
import com.b505.bean.LongLatVoice;

public interface ILongLatVoiceDao extends IBaseDao<LongLatVoice> {
	public void save(String voiceUrl, LongLat longLat, String voiceName,byte[] by);
}
