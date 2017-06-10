package com.b505.tools;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatVoice;
import com.b505.bean.SmallLongLatImage;
import com.b505.json.JsonAnalyze;

@Component
public class MapOutputGetStudentFile
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private TryCatchFileDownload tryCatchFileDownload;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private SmallFileURL smallFileURL;
	
	public Object getStudentFileURL(HttpServletRequest request, LongLat longLat) throws Exception
	{
		String[] longLatMapKey = {"word","geography","longitude","latitude"};
		Object[] longLatMapValue = {longLat.getWord(), longLat.getGeography(),longLat.getLongitude(),longLat.getLatitude()};
		Map<String,Object> LongLatMap = dataProcess.getMapByStringArray(longLatMapKey, longLatMapValue);		
		//得到学生共享的图片
		List<SmallLongLatImage> longLatImageList = longLat.getSmallLongLatImages();
		//得到学生共享的语音
		List<LongLatVoice> longLatVoiceList = longLat.getLongLatVoice();
		System.out.println("request.getRemoteAddr() : " + request.getRemoteAddr());
		StringBuilder sb = new StringBuilder(request.getRemoteAddr());
		System.out.println("request.getRemoteAddr()"+request.getRemoteAddr());
		//StringBuilder sb = new StringBuilder("192.168.1.102");
		sb.append(":8080");
		StringBuilder sbs = new StringBuilder(request.getContextPath());
		sbs.delete(0, 1);
		sb.append("\\"+sbs);
		String contextPathURL = sb.toString();
		
		final int longLatImageListSize = longLatImageList.size();
		if(longLatImageListSize < 1)
		{
			LongLatMap.put("pictureURL", "");
			LongLatMap.put("thumbnailPictureURL", "");
		}
		else
		{
			SmallLongLatImage smallLongLatImage = longLatImageList.get(0);
			String URL = smallLongLatImage.getImgurl();
			
			String regex = "school5";
			String[] URLStr = URL.split(regex);
			
			String thumbnailPictureURL = contextPathURL + URLStr[1];
			LongLatMap.put("thumbnailPictureURL", thumbnailPictureURL);
			
			String pictureURL = thumbnailPictureURL.replaceAll("thumbnailPicture", "picture");
			LongLatMap.put("pictureURL", pictureURL);
			
			smallFileURL.setPictureFile(URL);
		}
		final int longLatVoiceListSize = longLatVoiceList.size();
		if(longLatVoiceListSize < 1)
		{
			LongLatMap.put("speechURL", "");
		}
		else
		{
			LongLatVoice longLatVoice = longLatVoiceList.get(0);
			String URL = longLatVoice.getVoiceUrl();
			
			String regex = "school5";
			String[] URLStr = URL.split(regex);
			
			String voiceName = longLatVoice.getVoiceName();
			int voiceIndex = voiceName.indexOf(".");
			String voice = voiceName.substring(voiceIndex);
			
			
			System.out.println("voice :" + voice);
			LongLatMap.put("speechURL", contextPathURL + URLStr[1] + voice);
		}
		System.out.println("LongLatMap :" + LongLatMap);
		return LongLatMap;
	}
}

