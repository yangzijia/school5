/*
*@包名：com.b505.web        
*@文档名：UploadController.java 
*@功能：上传文件
*@作者：李振强     
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.infinispan.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;




import com.b505.bean.BeSavingFile;
import com.b505.bean.LoginUser;
import com.b505.json.JsonAnalyze;
import com.b505.service.ILoginUserService;
import com.b505.tools.FileUpload;
import com.b505.tools.SessionGet;
import com.b505.tools.StatusMap;

@Controller
public class UploadController 
{
	@Autowired
	private ILoginUserService userService;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private SessionGet sessionGet;
	
	//显示头像的ip,放到服务器上时，需要修改
	static final String IP = "http://sc1.hebeinu.edu.cn/";  //服务器域名
	//static final String IP = "http://172.18.73.5:8080";  //自己的ip
	/*
	 * @方法名：uploadHeadSculpture(@RequestBody String requestJsonBody)
	 * @功能：上传头像
	 * @功能说明：上传头像
	 * @作者：李振强
	 * @创建时间：2014.4.20
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value = "/uploadHeadSculpture.html")
	@ResponseBody
	public String uploadHeadSculpture(HttpServletRequest request,@RequestBody String requestJsonBody)throws Exception
	{
		String Fail = statusMap.status("Fail");
		String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
		System.out.println("requestJsonBody"+requestJsonBody);
		if(requestJsonBody==null||"".equals(requestJsonBody)||requestJsonBody.length()<0)
		{
			return cannotAnalyzeData;
		}
		Map<String,Object> map = jsonAnalyze.json2Map(requestJsonBody);
		//用户昵称
		String userNickname = (String)map.get("nickname");
		System.out.println("nickname"+userNickname);
		//用户头像
		String picture = (String)map.get("headSculpture");
		System.out.println("headSculpture"+picture);
		//文件的扩展名
		String fileExtension = (String)map.get("fileExtension");
		System.out.println("fileExtension"+fileExtension);
		if(userNickname==null||"".equals(userNickname)||fileExtension==null||"".equals(fileExtension)||picture==null||"".equals(picture))
		{
			return cannotAnalyzeData;
		}
		LoginUser user;
		try
		{
			//通过昵称得到用户
			user = userService.get("nickName", userNickname);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("UploadController_uploadHeadSculpture_userService.get(\"nickName\", userNickname);出错");
			return Fail;
		}
		//文件保存的URL
		String URL = "";
		String fileKind = "headSculpture";
		//头像不进行定期删除
		boolean fileIsTimerDeleted = false;
		FileUpload fu = new FileUpload();
		// Base64解码
		byte[] pictureByte = Base64.decode(picture);
		//项目根目录
		final String realPath = request.getSession().getServletContext().getRealPath("/");
		final String fileURL = realPath + fileKind;
		BeSavingFile beSavingFile = new BeSavingFile();
		beSavingFile.setFileURL(fileURL);
		beSavingFile.setFileExtension(fileExtension);
		beSavingFile.setTimerDeleted(fileIsTimerDeleted);
		beSavingFile.setFilesByte(pictureByte);
		
		//将头像保存到本地
		String[] str = fu.saveFile(beSavingFile);
		Map<String,Object> map2 = new HashMap<String, Object>();
		if("1".equals(str[0]))
		{
			URL = str[1];
			String url = URL.replaceAll("\\\\", "/")+"."+fileExtension;
			user.setImgUrl(IP+url.substring(url.indexOf("/school5")));
			System.out.println(IP+url.substring(url.indexOf("/school5")));
			map2.put("Status", "Success");
			map2.put("URL",IP+url.substring(url.indexOf("/school5")));
			try
			{
				//此处为更新，而不是插入，因为数据不一定是同步的。
				userService.update(user);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.err.println("UploadController_uploadHeadSculpture_userService.update(user);出错");
				return Fail;
			}
			String string = jsonAnalyze.map2Json(map2);
			return string;
		}
		else
		{
			return Fail;
		}
	}
	
	
	@RequestMapping(value = "/webUploadHeadSculpture.html")
	public ModelAndView webUploadHeadSculptureMethod(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request) throws Exception{
		//HttpSession session = request.getSession();
		//String userNickname = (String)session.getAttribute("userName");	
		String userNickname = sessionGet.getUserInfo().getUsername();
		System.out.println("userNickname-------"+userNickname);
		LoginUser user;
		try
		{
			//通过昵称得到用户
			user = userService.get("nickName", userNickname);
			System.out.println("user----->"+user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("UploadController_uploadHeadSculpture_userService.get(\"nickName\", userNickname);出错");
			return new ModelAndView("studentuploadheadpicture","error","");
		}
	        //文件保存的URL
			String URL = "";
			//文件类型
			String fileKind = "headSculpture"; 
			//文件扩展
			String fileExtension = "";   
			//图片字节
			byte[] pictureByte = null;    
			//头像不进行定期删除
			boolean fileIsTimerDeleted = false;
			FileUpload fu = new FileUpload();
		    System.out.println("size"+file.getSize());
		if(file.getSize()<=0){
			return new ModelAndView("studentinformation", "error", "请选择头像!");
		}
		//获得文件类型
		String fileType = file.getContentType();
		//输出文件类型
		System.out.println(fileType);
		//获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名   
		fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1,file.getOriginalFilename().length());
		System.out.println("fileExtension---"+fileExtension);
		pictureByte = file.getBytes();
		
		//request.getSession().getServletContext() 获取的是Servlet容器对象，相当于tomcat容器了;getRealPath("/") 获取实际路径，“/”指代项目根目录.
		final String realPath = request.getSession().getServletContext().getRealPath("/");
		System.out.println("realPath------>"+realPath);
		final String fileURL = realPath + fileKind;
		System.out.println("fileURL---->"+fileURL);
		BeSavingFile beSavingFile = new BeSavingFile();
		beSavingFile.setFileURL(fileURL);
		beSavingFile.setFileExtension(fileExtension);
		beSavingFile.setTimerDeleted(fileIsTimerDeleted);
		beSavingFile.setFilesByte(pictureByte);
		System.out.println("beSavingFile----->"+beSavingFile);
		
		//将头像保存到本地
		String[] str = fu.saveFile(beSavingFile);;
		
		//Map<String,Object> map2 = new HashMap<String, Object>();
		if("1".equals(str[0]))
		{
			URL = str[1];
			String url = URL.replaceAll("\\\\", "/")+"."+fileExtension;
			System.out.println("url======="+url);
			
			//indexOf() 方法可返回某个指定的字符串值在字符串中首次出现的位置
			//substring() 方法用于提取字符串中介于两个指定下标之间的字符，用法：stringObject.substring(start,stop),start必须有，stop不是必须的
			user.setImgUrl(IP+url.substring(url.indexOf("/school5")));
			System.out.println(IP+url.substring(url.indexOf("/school5")));
			//map2.put("Status", "Success");
			//map2.put("URL",IP+url.substring(url.indexOf("/school5")));
			try
			{
				//此处为更新，而不是插入，因为数据不一定是同步的。
				userService.update(user);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.err.println("UploadController_uploadHeadSculpture_userService.update(user);出错");
				return new ModelAndView("studentinformation", "error", "插入失败请重新上传!");
			}
		}
		return new ModelAndView("studentinformation", "error", "插入成功!");
	}
	
	 @RequestMapping(value="/openStduentUpload.html")
	 public String getStudentUplod(){
		return "studentuploadheadpicture";
	}
}

