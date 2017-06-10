/*
*@包名：com.b505.tools        
*@文档名：ReceiveDataProcess.java 
*@功能：处理接收到的数据 
*@作者：李振强        
*@创建时间：2014.5.14
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.json.JsonAnalyze;

@Component
public class DataProcess
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
	
	/*
	 * @方法名：dataIsNull(String str)
	 * @功能：验证数据是否为空
	 * @功能说明：验证数据是否为空。数据为空，返回true，反之，返回false。
	 * @作者：李振强
	 * @创建时间：2014.5.14
	 * @修改时间：2014.5.14
	 */
	public boolean dataIsNull(Object obj)
	{
		if(obj==null||"".equals(obj))
		{
			return true;
		}
		return false;
	}
	/*
	 * @方法名：arrayHasNull(Object[] obj)
	 * @功能：检查数组是否为空，或数组中的值为空
	 * @功能说明：检查数组是否为空，或数组中的值为空
	 * @作者：李振强
	 * @创建时间：2014.5.14
	 * @修改时间：2014.5.14
	 */
	public boolean arrayHasNull(Object[] obj)
	{
		boolean status = false;
		if(obj == null||"".equals(obj)||obj.length < 1)
		{
			return status = true;
		}
		final int objLength = obj.length;
		for(int i = 0; i < objLength; i++)
		{
			if(obj[i] == null||"".equals(obj[i]))
			{
				return status = true;
			}
		}
		return status;
	}
	
	/*
	 * @方法名：listHasNull(List<Object> list)
	 * @功能：检查数组是否为空，或数组中的值为空
	 * @功能说明：检查数组是否为空，或数组中的值为空
	 * @作者：李振强
	 * @创建时间：2014.5.14
	 * @修改时间：2014.5.14
	 */
	public boolean listHasNull(List<?> list)
	{
		boolean status = false;
		if(list == null||"".equals(list)||list.size() < 1)
		{
			return status = true;
		}
		final int listSize = list.size();
		for(int i = 0; i < listSize; i++)
		{
			if(list.get(i) == null||"".equals(list.get(i)))
			{
				return status = true;
			}
		}
		return status;
	}
	
	/*
	 * @方法名：getStringArrayByReceiveData(Map<String,Object> map , String[] key)
	 * @功能：将得到的Map数据根据key值转换为Object[]数组
	 * @功能说明：将得到的数据转换为数组。
	 * @作者：李振强
	 * @创建时间：2014.5.14
	 * @修改时间：2014.5.14
	 */

	public  Object[] getMapValueByKey(Map<String,Object> map , String[] key) throws Exception
	{
		final int strLength = key.length;
		Object[] obj = new Object[strLength];	
		for(int i = 0; i < strLength; i++)
		{
			obj[i] =map.get(key[i]);
		}
		System.out.println("obj----->"+obj);
		return obj;
	}
	
	
	/*
	 * @方法名：getMapValueByReceiveData(Map<String,Object> map , String key)
	 * @功能：将得到的Map数据根据key值转换为List<?>
	 * @功能说明：将得到的Map数据根据key值转换为List<?>
	 * @作者：李振强
	 * @创建时间：2014.5.14
	 * @修改时间：2014.5.14
	 */
	public  Object getMapValueByReceiveData(Map<String,Object> map , String key) throws Exception
	{
		List<?> arrayStr = (List<?>)map.get(key);
		return arrayStr;
	}
	
	public  Object getMapValueByString(Map<String,Object> map , String key) throws Exception
	{
		Object obj = map.get(key);
		return obj;
	}
	/*
	 * @方法名：getListByReceiveData(Map<String,Object> map, String[] key)
	 * @功能：将Map数据根据key值转换为List数组
	 * @功能说明：将得到的数据转换为数组。
	 * @作者：李振强
	 * @创建时间：2014.5.14
	 * @修改时间：2014.5.14
	 */
	public  Object getListByReceiveData(Map<String,Object> map, String[] key) throws Exception
	{
		List<Object> list = new ArrayList<Object>();
		final int strLength = key.length;
		for(int i = 0; i < strLength; i++)
		{
			list.add(map.get(key[i]));
		}
		return list;
	}
	
	/*
	 * @方法名：getListByListReceiveData(Map<String,Object> map, List<?> list) 
	 * @功能：将得到的Map数据转换为List数组
	 * @功能说明：将得到的Map数据转换为List数组
	 * @作者：李振强
	 * @创建时间：2014.5.14
	 * @修改时间：2014.5.14
	 */
	public  Object getListByListReceiveData(Map<String,Object> map, List<?> list) throws Exception
	{
		List<Object> lists = new ArrayList<Object>();
		final int listSize = list.size();
		for(int i = 0; i < listSize; i++)
		{
			lists.add(map.get(list.get(i)));
		}
		return lists;
	}
	
	
	/*
	 * @方法名：getListByStringReceiveData(Map<String,Object> map, String key)
	 * @功能：将得到的Map数据转换为List数组
	 * @功能说明：将得到的Map数据转换为List数组
	 * @作者：李振强
	 * @创建时间：2014.5.14
	 * @修改时间：2014.5.14
	 */
	public  List<?> getListByStringReceiveData(Map<String,Object> map, String key) throws Exception
	{
		List<Object> lists = new ArrayList<Object>();
		lists.add(map.get(key));
		return lists;
	}
	
	/*
	 * @方法名：getMapByStringArray(String[] key, Object[] value)
	 * @功能：根据key数组解析得到value数组
	 * @功能说明：根据key数组解析得到value数组，返回的数据为Map格式
	 * @作者：李振强
	 * @创建时间：2014.5.14
	 * @修改时间：2014.5.14
	 */
	public Map<String,Object> getMapByStringArray(String[] key, Object[] value)throws Exception
	{
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(key.length != value.length)
		{
			return null;
		}
		final int outLength = value.length;
		for(int i = 0; i < outLength; i++)
		{
			returnMap.put(key[i], value[i]);
		}
		return returnMap;
	}
}

