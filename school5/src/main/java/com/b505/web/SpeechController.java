/*
*@包名：com.b505.web        
*@文档名：SpeechController.java 
*@功能：语音搜索新闻 
*@作者：李振强        
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.News;
import com.b505.json.JsonAnalyze;
import com.b505.service.INewsService;
import com.b505.tools.StatusMap;

@Controller
public class SpeechController 
{
	@Autowired
	private INewsService newsService;
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	
	
	/*
	 * @方法名：newsSearch (@RequestBody String searchNews,@PathVariable("source") String source )
	 * @功能：新闻搜索
	 * @功能说明：新闻搜索
	 * @作者：李振强
	 * @创建时间：2014.3.20
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/newsSearch/{source}.html")
	@ResponseBody
	public String newsSearch(@RequestBody String searchNews,@PathVariable("source") String source )throws Exception
	{
		//状态返回
		String Fail = statusMap.status("Fail");
		String NoHaveNews = statusMap.status("NoHaveNews");
		String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1  = jsonAnalyze.json2Map(searchNews);
		String newsSearch =(String) map1.get("newsSearch");
		if(newsSearch==null||"".equals(newsSearch))
		{
			return cannotAnalyzeData;
		}
		Map<String,Object> newsMap = new HashMap<String,Object>();		
		int strLength = newsSearch.length();
		if(strLength < 1)
		{
			System.err.println("骚年，找你对象去！！！");
			return cannotAnalyzeData;
		}
		//将新闻分成不同的字长
		String[] str2W = new String[strLength];
		String[] str3W = new String[strLength];
		String[] str4W = new String[strLength];
		
		List<News> list1 = new ArrayList<News>();
		try
		{
			list1 = newsService.getAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("SpeechController_newsSearch_newsService.getAll();出错");
			return Fail;
		}
		if(list1==null||"".equals(list1)||list1.size()<1)
		{
			System.err.println("没有新闻让你搜哎");
			return Fail;
		}
		//循环队列，存放找到的新闻ID,最多返回的新闻条数为20条
		int[] newsID = new int[20];
		//队首下标 
		int front = 0; 
		//队尾下标
	    int rear = 0;
		//查询内容与新闻体或新闻内容的相似度
		int MaxLike = 0;
		//最匹配的新闻的id
		Integer id = 0;
		int like = 0;
		/////////将搜索内容分为2个字的长度///////////
		if(strLength >= 2)
		{
			int w2 = 0;
			for(int i = 0 ; i < strLength-1; i++)
			{
				str2W[w2] = newsSearch.substring(i, i+2);
				w2++;
			}
		}
		/////////将搜索内容分为3个字的长度/////////
		if(strLength >= 3)
		{
			int w3 = 0;
			for(int i = 0 ; i < strLength-2; i++)
			{
				str3W[w3] = newsSearch.substring(i, i+3);
				w3++;
			}
		}
		/////////将搜索内容分为4个字的长度/////////
		if(strLength >= 4)
		{
			int w4 = 0;
			for(int i = 0 ; i < strLength-3; i++)
			{
				str4W[w4] = newsSearch.substring(i, i+4);
				w4++;
			}
		}
		//新闻标题
		String newsTitle = new String();
		//新闻内容
		String newsBody = new String();
		News news = new News();
		for(int i = 0; i < list1.size(); i++ )
		{
			//like = 0;
			news = list1.get(i);
			newsTitle = news.getTheme();
			newsBody = news.getContent();
			//两个字长
			for(int j = 0; j < str2W.length - 1; j++)
			{
				boolean isLike1 = newsTitle.contains(str2W[j]);
				boolean isLike2 = newsBody.contains(str2W[j]);
				boolean isLike = isLike1||isLike2;
				if(isLike)
				{
					like++;
				}
			}
			//三个字长
			for(int j = 0; j < str3W.length - 2; j++)
			{
				boolean isLike = newsTitle.contains(str3W[j])||newsBody.contains(str3W[j]);
				if(isLike)
				{
					like++;
				}
			}
			//四个字长
			for(int j = 0; j < str4W.length - 3; j++)
			{
				boolean isLike = newsTitle.contains(str4W[j])||newsBody.contains(str4W[j]);
				if(isLike)
				{
					like++;
				}
			}
			//整体查询
			if((newsSearch!=null||(!"".equals(newsSearch)))&&strLength > 0)
			{
				boolean isLike = newsTitle.contains(newsSearch)||newsBody.contains(newsSearch);
				if(isLike)
				{
					like++;
				}
			}
			if(like>MaxLike)
			{
				MaxLike = like;
				id = news.getId();
				//队列满，队首出队一元素,接着队尾插入一元素
				if((rear+1)%newsID.length==front)
				{
					front = (front+1)%newsID.length;
			        newsID[rear] = id;
					rear = (rear+1)%newsID.length;
			    }
				//队列不满，队尾插入一元素
				else
				{
					newsID[rear] = id;
					rear = (rear+1)%newsID.length;
				}
			}
		}
		int newsLength = (rear-front+newsID.length)%newsID.length;
		if(newsLength < 1)
		{
			return NoHaveNews;
		}
		for(int i = 0 ; i < newsLength; i++)
		{
			//客户端
			if("client".equals(source))
			{
				Map<String,Object> map = new HashMap<String,Object>();
				rear = (rear-1)%newsID.length ;
				news = newsService.get(newsID[rear]);
				newsTitle = news.getTheme();
				String url = news.getUrl()+"/client"+".html";
				map.put("newsTitle", newsTitle);
				map.put("url", url);
				newsMap.put(""+i, map);
			}
			//Web端
			else if("web".equals(source))
			{
				Map<String,Object> map = new HashMap<String,Object>();
				news = newsService.get(newsID[front]);
				newsTitle = news.getTheme();
				String url = news.getUrl()+"/web"+".html";
				map.put("newsTitle", newsTitle);
				map.put("url", url);
				newsMap.put(""+i, map);
				rear = (rear-1)%newsID.length ;
			}
			else
			{
				System.err.println("1、异常！！！");
				return Fail;
			}
		}
		JsonAnalyze jsonAnalyze = new JsonAnalyze();
		String json = jsonAnalyze.map2Json(newsMap);
		if(json == null||"".equals(json))
		{
			return Fail;
		}
		return json;
	}
}

