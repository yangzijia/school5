package com.b505.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.b505.json.JsonAnalyze;

@Component
public class StatusMap 
{
	private JsonAnalyze jsonAnalyze = new JsonAnalyze();
	
	public String status(String status) throws IOException
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("Status", status);
		String str = jsonAnalyze.map2Json(map);	
		return str;
	}
}
