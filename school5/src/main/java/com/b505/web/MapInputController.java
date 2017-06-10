/*
*@包名：com.b505.web        
*@文档名：MapInputController.java 
*@功能：地图信息输入  
*@作者：李振强        
*@创建时间：2014.4.10  
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.infinispan.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.BeSavingFile;
import com.b505.bean.LongLat;
import com.b505.bean.Student;
import com.b505.json.JsonAnalyze;
import com.b505.service.ILongLatImageService;
import com.b505.service.ILongLatService;
import com.b505.service.ILongLatVoiceService;
import com.b505.service.ISmallLongLatImageService;
import com.b505.service.IStudentService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.FileUpload;
import com.b505.tools.HandleFile;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchImageScale;
import com.b505.tools.TryCatchLongLatService;
import com.b505.tools.TryCatchStudentService;
import com.b505.tools.TryCatchTeacherService;

@Controller
public class MapInputController 
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private IStudentService studentService;
	@Autowired
	private ILongLatService longLatService;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private ILongLatImageService longLatImageService;
	@Autowired
	private ISmallLongLatImageService smallLongLatService;
	@Autowired
	private ILongLatVoiceService longLatVoiceService;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private TryCatchLongLatService tryCatchLongLatService;
	@Autowired
	private FileUpload fu;
	@Autowired
	private TryCatchImageScale tryCatchImageScale;
	@Autowired
	private TryCatchTeacherService tryCatchTeacherService;
	
	//final static String IP = "http://172.18.74.39:8080"; //本机ip
	final static String IP = "http://sc1.hebeinu.edu.cn"; //服务器ip
	
	/*
	 * @方法名：studentMapSwim(@RequestBody String requestJsonBody)
	 * @功能：学生用户发送预警信息
	 * @功能说明：学生共享地图信息
	 * @作者：李振强
	 * @创建时间：2014.4.15
	 * @修改时间：2014.5.15
	 * @修改说明：代码重构
	 * @修改 ：2015.11.21
	 * @修改说明：添加预警类型字段
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mapInStudent.html")
	@ResponseBody
	public String studentMapSwim(HttpServletRequest request,@RequestBody String requestJsonBody)throws Exception
	{
		if(dataProcess.dataIsNull(requestJsonBody))
		{
			return returnStatus.CannotAnalyzeData;
		}
		//该数组为将要得到的数据的key值
		String[] requestUserMessage = {"userNickname","longlattype","longitude","latitude","word","geography"};
		Map<String,Object> requestJsonBodyMap = jsonAnalyze.json2Map(requestJsonBody);
		//得到的相应的key的数据值
		Object[] responseUserMessage = dataProcess.getMapValueByKey(requestJsonBodyMap, requestUserMessage);
		//检查得到的数据值是否为空
		if(dataProcess.dataIsNull(responseUserMessage)||dataProcess.arrayHasNull(responseUserMessage))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Student student = tryCatchStudentService.getStudentByNickname((String)responseUserMessage[0]);
		if(dataProcess.dataIsNull(student))
		{
			return returnStatus.Fail;
		}
		if(!"Role_Student".equals(student.getUser().getRole()))
		{
			return returnStatus.NotHaveUser;
		}
		LongLat longLat = new LongLat();
		longLat.setLonglattype((String)responseUserMessage[1]);
		longLat.setLongitude((String)responseUserMessage[2]);
		longLat.setLatitude((String)responseUserMessage[3]);
		longLat.setWord((String)responseUserMessage[4]);
		longLat.setGeography((String)responseUserMessage[5]);
		longLat.setStudent(student);
		Date newDate = new Date();
		longLat.setLongLatDate(newDate);
		boolean saveStatus = tryCatchLongLatService.longLatSaveOrUpdate(longLat);
		System.out.println("saveStatus测试------------->"+saveStatus);
		if(!saveStatus)
		{
			return returnStatus.Fail;
		}
		//得到的相应的key的数据值
		List<String> fileKind = (List<String>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "fileKind");
		if(dataProcess.dataIsNull(fileKind)||dataProcess.listHasNull(fileKind))
		{
			return returnStatus.CannotAnalyzeData;
		}
		boolean saveFileStatus = false;
		final String DateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
		//项目的根目录
		final String  RealPath = request.getSession().getServletContext().getRealPath("/");
		//项目目录名字
		final String  realPath = request.getContextPath()+"/";
		System.out.println("realPath"+realPath);
		System.out.println("RealPath"+RealPath);
		final int fileKindSize = fileKind.size();
		for(int i = 0; i < fileKindSize; i++)
		{
			String fileKindName = fileKind.get(i);
			final String fileURL = RealPath + fileKindName;
			boolean checkStatus = HandleFile.checkAndDeletedFile(fileURL);
			if(!checkStatus)
			{
				return returnStatus.Fail;
			}
			boolean fileIsTimerDeleted = true;
			List<Map<String,Object>> fileList = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, fileKindName);
			if(dataProcess.dataIsNull(fileList)||dataProcess.listHasNull(fileList))
			{
				return returnStatus.CannotAnalyzeData;
			}
			final int fileListSize = fileList.size();
			for(int j = 0; j < fileListSize; j++)
			{
				//得到文件的详细信息
				Map<String,Object> fileMap = fileList.get(j);
				if(fileMap==null||"".equals(fileMap))
				{
					return returnStatus.CannotAnalyzeData;
				}
				String fileExtension = (String)fileMap.get("fileExtension");
				byte[] filesByte = Base64.decode((String)fileMap.get(fileKindName));
				if(filesByte==null||"".equals(filesByte))
				{
					return returnStatus.CannotAnalyzeData;
				}
				BeSavingFile beSavingFile = new BeSavingFile();
				beSavingFile.setFileURL(fileURL);
				beSavingFile.setFileExtension(fileExtension);
				beSavingFile.setTimerDeleted(fileIsTimerDeleted);
				beSavingFile.setFilesByte(filesByte);		
				String[] str = fu.saveFile(beSavingFile);
				if("1".equals(str[0]))
				{
					saveFileStatus = true;
				}
				else
				{
					saveFileStatus = false;
					return returnStatus.Fail;
				}
				//保存文件。 str[1]为文件的在本地的URL，str[2]为文件的名字
				if("picture".equals(fileKindName))
				{	//绝对地址
					String imageURL = str[1];
					String imageName = str[2];
					String extensions = imageName.substring(imageName.indexOf(".")); 
					//相对路径
					String imageRURL =imageURL.substring((imageURL.indexOf("\\school5\\")));
					System.out.println("imageRURL"+imageRURL);
					longLatImageService.save(IP+imageRURL.replaceAll("\\\\", "/")+extensions, longLat,imageName, filesByte);
					//将客户端上传的大图转换为缩略图       
					String pictureURL = imageURL + "." + fileExtension;
					
					String fileNameRegex = DateStr;
					String fileExtensionRegex = "." + fileExtension;
					String[] fileName = pictureURL.split(fileNameRegex);
					String[] fileExtensionName = fileName[1].split(fileExtensionRegex);
					
					String thumbnailPictureURL = RealPath +"thumbnailPicture"+"\\" +DateStr;
					
					System.out.println("pictureURL :" + pictureURL);
					System.out.println("thumbnailPictureURL :" + thumbnailPictureURL);
					System.out.println("fileExtensionName[0] :" + fileExtensionName[0]);
					System.out.println("fileName[0] :" + fileName[0]);
					System.out.println("fileName[1] :" + fileName[1]);
					//存在数据库中的相对地址
					String reUrlString  = realPath +"thumbnailPicture"+"\\" +DateStr;
					boolean changeImage = tryCatchImageScale.imageScale(pictureURL,  thumbnailPictureURL, fileExtensionName[0], fileExtension);
					System.out.println("转换大图"+ imageURL + imageName +"为缩略图 ：" + changeImage);
					if(changeImage)
					{
						smallLongLatService.save(IP+(reUrlString + fileName[1]).replaceAll("\\\\", "/"), longLat, imageName, filesByte);
					}
					else
					{
						return returnStatus.Fail;
					}
				}
				else if("speech".equals(fileKindName))
				{
					String voiceURL = str[1];
					String voiceName = str[2];
					String extensions = voiceName.substring(voiceName.indexOf(".")); 
					String voiceRURL =voiceURL.substring((voiceURL.indexOf("\\school5\\")));
					longLatVoiceService.save(IP+voiceRURL.replaceAll("\\\\", "/")+extensions, longLat, voiceName, filesByte);
				}
			}
		}
		if(saveFileStatus)
		{
			System.out.println("保存成功!!!");
			return returnStatus.Success;
		}
		else
		{
			return returnStatus.Fail;
		}		
	}
	
	
	/*
	 * @方法名：headteacherMapSwim(HttpServletRequest request,@RequestBody String requestJsonBody)
	 * @功能：辅导员用户发送预警信息
	 * @作者：lyf
	 * @创建时间：2014.11.26
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mapInHeadteacher.html")
	@ResponseBody
	public String headteacherMapSwim(HttpServletRequest request,@RequestBody String requestJsonBody)throws Exception
	{
	
		if(dataProcess.dataIsNull(requestJsonBody))
		{
			return returnStatus.CannotAnalyzeData;
		}
		//该数组为将要得到的数据的key值
		String[] requestUserMessage = {"userNickname","hteacherNickname","longlattype","longitude","latitude","word","geography"};
		Map<String,Object> requestJsonBodyMap = jsonAnalyze.json2Map(requestJsonBody);
		//得到的相应的key的数据值
		Object[] responseUserMessage = dataProcess.getMapValueByKey(requestJsonBodyMap, requestUserMessage);
		//检查得到的数据值是否为空
		if(dataProcess.dataIsNull(responseUserMessage)||dataProcess.arrayHasNull(responseUserMessage))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Student student = tryCatchStudentService.getStudentByNickname((String)responseUserMessage[0]);
		
		if(dataProcess.dataIsNull(student))
		{
			return returnStatus.Fail;
		}

		LongLat longLat = new LongLat();
		longLat.setHteacherNickname((String)responseUserMessage[1]);
		longLat.setLonglattype((String)responseUserMessage[2]);
		longLat.setLongitude((String)responseUserMessage[3]);
		longLat.setLatitude((String)responseUserMessage[4]);
		longLat.setWord((String)responseUserMessage[5]);
		longLat.setGeography((String)responseUserMessage[6]);
		longLat.setStudent(student);
		Date newDate = new Date();
		longLat.setLongLatDate(newDate);	
		boolean saveStatus = tryCatchLongLatService.longLatSaveOrUpdate(longLat);
		System.out.println("saveStatus辅导员发送预警信息测试------------->"+saveStatus);
		if(!saveStatus)
		{
			return returnStatus.Fail;
		}
		//得到的相应的key的数据值
		List<String> fileKind = (List<String>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "fileKind");
		if(dataProcess.dataIsNull(fileKind)||dataProcess.listHasNull(fileKind))
		{
			return returnStatus.CannotAnalyzeData;
		}
		boolean saveFileStatus = false;
		final String DateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
		//项目的根目录
		final String  RealPath = request.getSession().getServletContext().getRealPath("/");
		//项目目录名字
		final String  realPath = request.getContextPath()+"/";
		System.out.println("realPath"+realPath);
		System.out.println("RealPath"+RealPath);
		final int fileKindSize = fileKind.size();
		for(int i = 0; i < fileKindSize; i++)
		{
			String fileKindName = fileKind.get(i);
			final String fileURL = RealPath + fileKindName;
			boolean checkStatus = HandleFile.checkAndDeletedFile(fileURL);
			if(!checkStatus)
			{
				return returnStatus.Fail;
			}
			boolean fileIsTimerDeleted = true;
			List<Map<String,Object>> fileList = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, fileKindName);
			if(dataProcess.dataIsNull(fileList)||dataProcess.listHasNull(fileList))
			{
				return returnStatus.CannotAnalyzeData;
			}
			final int fileListSize = fileList.size();
			for(int j = 0; j < fileListSize; j++)
			{
				//得到文件的详细信息
				Map<String,Object> fileMap = fileList.get(j);
				if(fileMap==null||"".equals(fileMap))
				{
					return returnStatus.CannotAnalyzeData;
				}
				String fileExtension = (String)fileMap.get("fileExtension");
				byte[] filesByte = Base64.decode((String)fileMap.get(fileKindName));
				if(filesByte==null||"".equals(filesByte))
				{
					return returnStatus.CannotAnalyzeData;
				}
				BeSavingFile beSavingFile = new BeSavingFile();
				beSavingFile.setFileURL(fileURL);
				beSavingFile.setFileExtension(fileExtension);
				beSavingFile.setTimerDeleted(fileIsTimerDeleted);
				beSavingFile.setFilesByte(filesByte);		
				String[] str = fu.saveFile(beSavingFile);
				if("1".equals(str[0]))
				{
					saveFileStatus = true;
				}
				else
				{
					saveFileStatus = false;
					return returnStatus.Fail;
				}
				//保存文件。 str[1]为文件的在本地的URL，str[2]为文件的名字
				if("picture".equals(fileKindName))
				{	//绝对地址
					String imageURL = str[1];
					String imageName = str[2];
					String extensions = imageName.substring(imageName.indexOf(".")); 
					//相对路径
					String imageRURL =imageURL.substring((imageURL.indexOf("\\school5\\")));
					System.out.println("imageRURL"+imageRURL);
					longLatImageService.save(IP+imageRURL.replaceAll("\\\\", "/")+extensions, longLat,imageName, filesByte);
					//将客户端上传的大图转换为缩略图       
					String pictureURL = imageURL + "." + fileExtension;
					
					String fileNameRegex = DateStr;
					String fileExtensionRegex = "." + fileExtension;
					String[] fileName = pictureURL.split(fileNameRegex);
					String[] fileExtensionName = fileName[1].split(fileExtensionRegex);
					
					String thumbnailPictureURL = RealPath +"thumbnailPicture"+"\\" +DateStr;
					
					System.out.println("pictureURL :" + pictureURL);
					System.out.println("thumbnailPictureURL :" + thumbnailPictureURL);
					System.out.println("fileExtensionName[0] :" + fileExtensionName[0]);
					System.out.println("fileName[0] :" + fileName[0]);
					System.out.println("fileName[1] :" + fileName[1]);
					//存在数据库中的相对地址
					String reUrlString  = realPath +"thumbnailPicture"+"\\" +DateStr;
					boolean changeImage = tryCatchImageScale.imageScale(pictureURL,  thumbnailPictureURL, fileExtensionName[0], fileExtension);
					System.out.println("转换大图"+ imageURL + imageName +"为缩略图 ：" + changeImage);
					if(changeImage)
					{
						smallLongLatService.save(IP+(reUrlString + fileName[1]).replaceAll("\\\\", "/"), longLat, imageName, filesByte);
					}
					else
					{
						return returnStatus.Fail;
					}
				}
				else if("speech".equals(fileKindName))
				{
					String voiceURL = str[1];
					String voiceName = str[2];
					String extensions = voiceName.substring(voiceName.indexOf(".")); 
					String voiceRURL =voiceURL.substring((voiceURL.indexOf("\\school5\\")));
					longLatVoiceService.save(IP+voiceRURL.replaceAll("\\\\", "/")+extensions, longLat, voiceName, filesByte);
				}
			}
		}
		if(saveFileStatus)
		{
			System.out.println("保存成功!!!");
			return returnStatus.Success;
		}
		else
		{
			return returnStatus.Fail;
		}		
	}
}

