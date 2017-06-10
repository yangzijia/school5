package com.b505.tools;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.json.JsonAnalyze;

@Component
public class AnalyzeData
{
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	
	
	/*
	 * @方法名：clientDataBeAnalyzedToServiceDataMap2String(String requestJsonBody , String[] key)
	 * @功能：将客户端数据按照key解析，返回的数据为数组。
	 * @功能说明：此方法适用于key值与value值是一一对应的数据格式{"key1":"value1","key2":"value2","key3":"value"}
	 * @作者：李振强
	 * @创建时间：2014.5.19
	 * @修改时间：2014.5.19
	 * @修改说明：代码重构
	 */
	public Object clientDataBeAnalyzedToServiceDataMap2String(String requestJsonBody , String[] key)throws Exception
	{
		Object[] obj;
		if(dataProcess.dataIsNull(requestJsonBody))
		{
			return obj = null;
		}
		Map<String,Object> requestJsonBodyMap = jsonAnalyze.json2Map(requestJsonBody);
		System.out.println("requestJsonBodyMap---->"+requestJsonBodyMap);
		obj = dataProcess.getMapValueByKey(requestJsonBodyMap, key);
		return obj;
	}
	
	
	/*
	 * @方法名：clientDataBeAnalyzedToServiceDataList2String(String requestJsonBody)
	 * @功能：将客户端数据转换成List数组
	 * @功能说明：将客户端数据转换成List数组。{ [object] ,[object]}
	 * @作者：李振强
	 * @创建时间：2014.5.19
	 * @修改时间：2014.5.19
	 * @修改说明：代码重构
	 */
	public Object clientDataBeAnalyzedToServiceDataList(String requestJsonBody)throws Exception
	{
		if(dataProcess.dataIsNull(requestJsonBody))
		{
			return  null;
		}
		List<Object> requestJsonBodyList = jsonAnalyze.json2List(requestJsonBody);	
		return requestJsonBodyList;
	}
	/*
	 * @方法名：clientDataBeAnalyzedToServiceDataList2String(String requestJsonBody)
	 * @功能：将客户端数据转换成Map数组
	 * @功能说明：将客户端数据转换成Map数组。{ [object] ,[object]}
	 * @作者：李振强
	 * @创建时间：2014.5.19
	 * @修改时间：2014.5.19
	 * @修改说明：代码重构
	 */
	public Object clientDataBeAnalyzedToServiceDataMap(String requestJsonBody)throws Exception
	{
		if(dataProcess.dataIsNull(requestJsonBody))
		{
			return  null;
		}
		Map<String,Object> requestJsonBodyList = jsonAnalyze.json2Map(requestJsonBody);	
		return requestJsonBodyList;
	}
	/*
	 * @方法名：clientDataBeAnalyzedToServiceDataList2String(String requestJsonBody,String key)
	 * @功能：返回key值对应的value
	 * @功能说明：返回key值对应的value。{"key":"value"}
	 * @作者：李振强
	 * @创建时间：2014.5.19
	 * @修改时间：2014.5.19
	 * @修改说明：代码重构
	 */
	public Object clientDataBeAnalyzedToServiceDataList2String(String requestJsonBody,String key)throws Exception
	{
		if(dataProcess.dataIsNull(requestJsonBody))
		{
			return  null;
		}
		Map<String,Object> requestJsonBodyMap = jsonAnalyze.json2Map(requestJsonBody);
		System.out.println("requestJsonBodyMap-----》"+requestJsonBodyMap);
		System.out.println("requestJsonBodyMap.get(key)=="+requestJsonBodyMap.get(key));
		return requestJsonBodyMap.get(key);
	}
	
	
	/*
	 * @方法名：clientDataBeAnalyzedToServiceDataHttpServletRequest2String(HttpServletRequest request, String[] str)
	 * @功能：解析HttpServletRequest数据
	 * @功能说明：适用于与Web端的数据交互
	 * @作者：李振强
	 * @创建时间：2014.5.19
	 * @修改时间：2014.5.19
	 * @修改说明：代码重构
	 */
	public String[] clientDataBeAnalyzedToServiceDataHttpServletRequest2String(HttpServletRequest request, String[] str)
	{
		if(dataProcess.dataIsNull(str)||dataProcess.arrayHasNull(str))
		{
			return null;
		}
		final int strLength = str.length;
		for(int i = 0; i < strLength; i++)
		{
			System.out.println("request "+ i +str[i]);
			
			str[i] = request.getParameter(str[i]);
			
			System.out.println("request2 "+ i +str[i]);
		}
		return str;
	}
}

