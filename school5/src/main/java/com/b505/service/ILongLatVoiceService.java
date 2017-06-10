package com.b505.service;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatVoice;

public interface ILongLatVoiceService extends IBaseService<LongLatVoice> {
	public void save(String voiceUrl, LongLat longLat, String voiceName,
			byte[] by);
}
