/*
*@包名：com.b505.web        
*@文档名：NewsOutputController.java 
*@功能：新闻输出   
*@作者：李振强        
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.News;
import com.b505.bean.util.NewsUtil;
import com.b505.json.JsonAnalyze;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchNewsService;

@Controller
public class NewsOutputController
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchNewsService tryCatchNewsService;
	@Autowired
	private ReturnObjectByAttribute returnObjectByAttribute;
	
	
	/*
	 * @方法名：getClientNewsList(@PathVariable("source") String source ,@RequestBody String requestJsonBody)
	 * @功能：发送新闻列表
	 * @功能说明：发送新闻列表
	 * @作者：李振强
	 * @创建时间：2014.3.15
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/newsList/{source}")
	@ResponseBody
	public String getClientNewsList(@PathVariable("source") String source ,@RequestBody String requestJsonBody,HttpServletRequest request) throws Exception
	{
		String[] requestKey = {"index","sectionName"};
		Object[] responseValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestKey);
		if(dataProcess.dataIsNull(responseValue)||dataProcess.arrayHasNull(responseValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		final int NumberOfThePage = 6;
		int index = Integer.parseInt((String)responseValue[0]);
		String newsJson = "";
		if(index < 0)
		{
			index = 1;
		}
		List<News> newsList = tryCatchNewsService.findByPagerAndSection(index, NumberOfThePage, (String)responseValue[1]);
		final int newsListSize = newsList.size();
		Map<String,Object> newsSectionMap = new HashMap<String,Object>();
		if("client".equals(source))
		{
			for(int i = 0; i <newsListSize; i++)
			{
				News news = newsList.get(i);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String[] sectionMapKey = {"Author","Theme","Section","URL","ReDate"};
				Object[] sectionMapValue = {news.getAuthor(), news.getTheme(), news.getSection(),news.getUrl()+"/client"+".html", format.format(news.getReDate())};
				Map<String,Object> map = dataProcess.getMapByStringArray(sectionMapKey, sectionMapValue);
				newsSectionMap.put(i+"",map);
			}
		}
		else if("web".equals(source))
		{
			for(int i = 0; i <newsListSize; i++)
			{
				News news = newsList.get(i);
				String[] sectionMapKey = {"Author","Theme","Section","URL","ReDate"};
				Object[] sectionMapValue = {news.getAuthor(), news.getTheme(), news.getSection(),news.getSection(),news.getUrl()+"/web"+".html", news.getReDate()};
				Map<String,Object> map = dataProcess.getMapByStringArray(sectionMapKey, sectionMapValue);
				newsSectionMap.put(i+"",map);
			}
		}
		newsJson = jsonAnalyze.map2Json(newsSectionMap);
		if(dataProcess.dataIsNull(newsJson))
		{
			return returnStatus.LoadingNewsError;
		}
		System.out.println("newsJson"+newsJson);
		return newsJson;
	}
	
	
	/*
	 * @方法名：getClientNewsBody(@PathVariable("url") String url,@PathVariable("source") String source )
	 * @功能：发送新闻内容
	 * @功能说明：根据客户端发送的URL返回对应的新闻内容
	 * @作者：李振强
	 * @创建时间：2014.3.15
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/news/{url}/{source}")
	@ResponseBody
	public String getClientNewsBody(HttpServletRequest request,@PathVariable("url") String url,@PathVariable("source") String source )throws Exception
	{
		int index = Integer.parseInt(url);
		News news = tryCatchNewsService.getNews(index);
		if(dataProcess.dataIsNull(news))
		{
			return returnStatus.Fail;
		}
		//新闻体
		String newsBody = news.getContent();
		if(dataProcess.dataIsNull(newsBody))
		{
			return returnStatus.LoadingNewsError;
		}
		if("client".equals(source))
		{
			String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"DTD/xhtml1-strict.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\">"
					+ "<wml>"
					+ "<body style=\"width:100%;font-size:10px;margin:0px auto;position:relative\">"
					+ "<h1>"
					+ newsBody
					+ "</h1>"
					+ "</body>"
					+ "</wml>";
			return content;
		}
		else if("web".equals(source))
		{
			String content = new String();
			content = "<!DOCTYPE HTML>" 
						+"<html>"
							+"<body>"
								+newsBody
							+"</body>"
						+"</html>";
			return content;
		}
		else
		{
			return returnStatus.LoadingNewsError;
		}
	}
	@RequestMapping(value="/getNewsByNewId.html")
	@ResponseBody
	public String getNewsByNewsIdMethod(HttpServletRequest request) throws IOException{
		System.out.println("执行了吗");
		HttpSession hp = request.getSession();
		Integer newsId=(Integer)hp.getAttribute("newsId");
		//int newsId=90;
		News news = tryCatchNewsService.getNews(newsId);
		if(news==null){
			return returnStatus.Fail;
		}else{
			System.out.println(jsonAnalyze.object2Json(news));
			return jsonAnalyze.object2Json(news);
		}
		
	}
	@RequestMapping(value="/showWebNewsList.html")
	@ResponseBody
	public String getWebNewsList(HttpServletRequest request) throws IOException{
		List<NewsUtil> newsUtils = tryCatchNewsService.getWebNewsUtilList("学校风采");
		if(dataProcess.dataIsNull(newsUtils)||("").equals(newsUtils)){
			return returnStatus.Fail;
			
		}else{
			System.out.println(jsonAnalyze.list2Json(newsUtils));
			return jsonAnalyze.list2Json(newsUtils);
		}
		
	}
	
	@RequestMapping(value="/showRule.html")
	public String getShowRule(){
		return "newsStaticBody";
	}
	@RequestMapping(value="/showRule2.html")
	public String getShowRule2(){
		return "newsStaticBody2";
	}
	@RequestMapping(value="/showRule3.html")
	public String getShowRule3(){
		return "newsStaticBody3";
	}
	@RequestMapping(value="/showRule4.html")
	public String getShowRule4(){
		return "newsStaticBody4";
	}
	@RequestMapping(value="/showNewsList.html")
	public String getNewsLsit(){
		return "newsList";
	}
}

