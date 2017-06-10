package com.b505.dao.impl;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatVoice;
import com.b505.dao.ILongLatVoiceDao;
@Repository(value="ILongLatVoiceDao")
public class LongLatVoiceDaoImpl extends BaseDaoImpl<LongLatVoice> implements ILongLatVoiceDao {

	@Override
	public void save(String voiceUrl, LongLat longLat, String voiceName, byte[] by) {
		
		System.out.println("==========================================================");
		
		System.out.println("voiceUrl : "+voiceUrl);
		System.out.println("longLat : "+longLat);
		System.out.println("voiceName : "+voiceName);
		System.out.println("by : "+by);
		
		System.out.println("==========================================================");
		
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		LongLatVoice latVoice = new LongLatVoice();
		latVoice.setVoice(session.getLobHelper().createBlob(by));
		latVoice.setVoiceName(voiceName);
		latVoice.setVoiceUrl(voiceUrl);
		latVoice.setLongLat(longLat);
		session.save(latVoice);
		session.flush();
		session.clear();
		
	}

	
}
