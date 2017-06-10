/*
*@包名：com.b505.web        
*@文档名：NewsController.java
*@功能：新闻管理（删除、修改新闻）    
*@作者：李振强    
*@创建时间：2014.4.20
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.News;
import com.b505.bean.util.NewsUtil;
import com.b505.json.JsonAnalyze;
import com.b505.service.INewsService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchNewsService;

@Controller
public class NewsController 
{
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
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private INewsService newsService;
	/*
	 * @方法名：deletedNews(@RequestBody String requestJsonBody)
	 * @功能：删除新闻
	 * @功能说明：删除新闻
	 * @作者：李振强
	 * @创建时间：2014.3.15
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/delNews.html")
	@ResponseBody
	public String deletedNews(@RequestBody String requestJsonBody) throws Exception
	{
		@SuppressWarnings("unchecked")
		List <Integer> newsID = (List<Integer>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(newsID))
		{
			return returnStatus.CannotAnalyzeData;
		}
		for(int i=0;i<newsID.size();i++){
		News news = new News();
		news.setId(newsID.get(i));
		if(!tryCatchNewsService.deletedNewsByID(news))
		{
			return returnStatus.Fail;
		}
		}
		return returnStatus.Success;
	}

	@RequestMapping(value="/showNewsUtil.html")
	@ResponseBody
	public String getNewsUtilsBysectionName(@RequestBody String  requestJsonBody) throws IOException{
		System.out.println("requestJsonBody"+requestJsonBody);
		Map<String,Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String sectionName=(String)map.get("sectionName");
		List<NewsUtil> list = newsService.getNewsUtilsBySection(sectionName);
		String jsonString = jsonAnalyze.list2Json(list);
		return jsonString;
		
	}
}

