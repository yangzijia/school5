/*
*@包名：com.b505.web        
*@文档名：NewsInputController.java 
*@功能：新闻录入   
*@作者：李振强        
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.NewsImage;
import com.b505.bean.News;
import com.b505.bean.Section;
import com.b505.json.JsonAnalyze;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchNewsImageService;
import com.b505.tools.TryCatchNewsService;
import com.b505.tools.TryCatchSectionService;


@Controller
public class NewsInputController 
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
	private TryCatchNewsImageService tryCatchNewsImageService;
	@Autowired
	private TryCatchSectionService tryCatchSectionService;
	@Autowired
	private ReturnObjectByAttribute returnObjectByAttribute;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	
	/*
	 * @方法名：newsInput1()
	 * @功能：新闻录入
	 * @功能说明：跳转到新闻录入页面
	 * @作者：贾少游
	 * @创建时间：2016.10.24
	 */
	@RequestMapping(value="/newsInput1.html")
	public String newsInput1()
	{
		return "newsInput1";
	}
	
	/*
	 * @方法名：newsInput()
	 * @功能：新闻录入
	 * @功能说明：跳转到新闻录入页面
	 * @作者：李振强
	 * @创建时间：2014.3.15
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/newsInput.html")
	public String newsInput()
	{
		return "newsInput";
	}
	
	
	
	/*
	 * @方法名：getSectionMethod()
	 * @功能：获得新闻栏目
	 * @功能说明：从数据库中获得新闻栏目
	 * @作者：陈炳旭
	 * @创建时间：2014.4.27
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/getSection.html")
	@ResponseBody
	public String getSectionMethod()throws Exception
	{
		List<Section> sectionList = tryCatchSectionService.getSectionAll();
		if(dataProcess.dataIsNull(sectionList)||dataProcess.listHasNull(sectionList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		String sectionString = jsonAnalyze.list2Json(sectionList);
		if(dataProcess.dataIsNull(sectionString))
		{
			return returnStatus.Fail;
		}
		return sectionString;
	}
	
	
	/*
	 * @方法名：newsInput()
	 * @功能：保存新闻录入
	 * @功能说明：将录入的新闻存入数据库
	 * @作者：贾少游
	 * @修改时间：2016.10.28
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/newsAdd.html")
	@ResponseBody
	public String newsAdd(@RequestBody String requestJsonBody,HttpServletRequest request) throws ServletException, Exception
	{
		//前台通过ajax向后台传送数据
		String[] requestKey = {"content","newsTitle","newsAuthor","checker","section"};
		//String[] responseValue = analyzeData.clientDataBeAnalyzedToServiceDataHttpServletRequest2String(request, requestKey);
		Object[] responseValue=(Object[]) analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestKey);
		
		if(dataProcess.dataIsNull(responseValue)||dataProcess.arrayHasNull(responseValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		// 获取的是Servlet容器对象，相当于tomcat容器了;getRealPath("/") 获取实际路径，“/”指代项目根目录.
		final String realPath = request.getSession().getServletContext().getRealPath("/");
		System.out.println("realPath---->"+realPath);
		//其作用是获取当前的系统路径,得到项目的名字
		String URL = request.getContextPath()+"/"+"news";
		System.out.println("URL-->"+URL);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date newsDate =new Date();
	    String date =format.format(newsDate);
	    
	    Section section = tryCatchSectionService.getSection("sectionName",responseValue[4]);
	 
	    News news = returnObjectByAttribute.returnNews(null,(String)responseValue[1], (String)responseValue[2], URL, (String)responseValue[0], (String)responseValue[3], section);
	    if(dataProcess.dataIsNull(news))
		{
			return returnStatus.Fail;
		}
	    news.setReDate(newsDate);
		if(!tryCatchNewsService.saveNews(news))
		{
			return returnStatus.Fail;
		}
		String imageURL = URL+"/images/"+date;
		NewsImage image = returnObjectByAttribute.returnNewsImage(imageURL, news);
		if(!tryCatchNewsImageService.saveNewsImage(image))
		{
			return returnStatus.Fail;
		}
		News newsUpdate = tryCatchNewsService.getNews(news.getId());
		String newsUpdateURL ="http://sc1.hebeinu.edu.cn/"+URL+"/"+ news.getId();
		newsUpdate.setUrl(newsUpdateURL);
		
		if(!tryCatchNewsService.updateNews(newsUpdate))
		{
			return returnStatus.Fail;
		}
	    return returnStatus.Success;
	}
	
	/**
	 * @author 少游
	 * @功能：更新新闻
	 * @方法：updateNews()
	 * @time:2016.10.28
	 */
	
	@RequestMapping(value="/updateNews.html")
	@ResponseBody
	public String updateNews(@RequestBody String requestJsonBody,HttpServletRequest request)throws Exception{
		System.out.println("requestJsonBody---->"+requestJsonBody);
		//从session中获取新闻id
		HttpSession hpSession=request.getSession();
		Integer newId=(Integer) hpSession.getAttribute("newsId");
		
		//将前台数据转换成map数据
		Map<String, Object>map=new HashMap<String, Object>();
		map=jsonAnalyze.json2Map(requestJsonBody);
		//判断数据是否为空
		if (dataProcess.dataIsNull(map)||map==null)
		{
			return returnStatus.CannotAnalyzeData;
		}
		String content=(String) map.get("content");
		String newsTitle=(String) map.get("newsTitle");
		String newsAuthor=(String) map.get("newsAuthor");
		String checker=(String)map.get("checker");
		String section=(String)map.get("section");
		//获取当前时间
		Date date=new Date();
		
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=format.format(date);
		
		//其作用是获取当前的系统路径,得到项目的名字
		String URL = request.getContextPath()+"/"+"news";
		String newsUpdateURL ="http://sc1.hebeinu.edu.cn"+URL+"/"+newId;
		
		 Section section1 = tryCatchSectionService.getSection1(section);
		
		 News news=returnObjectByAttribute.returnNews(newId,newsTitle,newsAuthor,newsUpdateURL,content,checker,section1);
		 news.setReDate(date);
		 if (!tryCatchNewsService.updateNews(news))
		{
			return returnStatus.Fail;
		}
		 String imageURL = URL+"/images/"+time;
			//NewsImage image = returnObjectByAttribute.returnNewsImage(imageURL, news);
		 NewsImage image=tryCatchNewsImageService.getNewsImage(newId);
		 System.out.println("image---->"+image);
		 image.setImgUrl(imageURL);
			System.out.println("执行了吗");
			if(!tryCatchNewsImageService.updateNewsImage(image))
			{
				return returnStatus.Fail;
			}
		return returnStatus.Success;
	}
}	

