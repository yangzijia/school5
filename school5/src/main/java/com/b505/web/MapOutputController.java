/*
*@包名：com.b505.web        
*@文档名：MapOutputController.java 
*@功能：地图信息输出    
*@作者：李振强        
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Grade;
import com.b505.bean.LongLat;
import com.b505.bean.LongLatImage;
import com.b505.bean.LongLatVoice;
import com.b505.bean.SmallLongLatImage;
import com.b505.bean.Student;
import com.b505.bean.Teacher;
import com.b505.bean.LoginUser;
import com.b505.bean.util.LongLatClientUtil;
import com.b505.json.JsonAnalyze;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.MapOutputGetStudentFile;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchFileDownload;
import com.b505.tools.TryCatchGradeService;
import com.b505.tools.TryCatchInteger;
import com.b505.tools.TryCatchLongLatService;
import com.b505.tools.TryCatchStudentService;
import com.b505.tools.TryCatchTeacherService;
import com.b505.tools.TryCatchUserService;

@Controller
public class MapOutputController 
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
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private TryCatchTeacherService tryCatchTeacherService;
	@Autowired
	private TryCatchLongLatService tryCatchLongLatService;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private TryCatchGradeService tryCatchGradeService;
	@Autowired
	private ReturnObjectByAttribute returnObjectByAttribute;
	@Autowired
	private TryCatchFileDownload tryCatchFileDownload;
	@Autowired
	private TryCatchInteger tryCatchInteger;
	@Autowired
	private MapOutputGetStudentFile mapOutputGetStudentFile;
	
	/*
	 * @方法名：getGradeAndLongLat(@RequestBody String requestJsonBody)
	 * @功能：得到班级共享的地图信息的数量
	 * @功能说明：返回辅导员管理的班级及每个班级共享的地图信息的数量
	 * @作者：李振强
	 * @创建时间：2014.4.15
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/mapOutNumber.html")
	@ResponseBody
	public String getGradeAndLongLat(@RequestBody String requestJsonBody)throws Exception
	{
		String[] requestKey = {"userNickname"};
		Object[] responseValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestKey);
		if(dataProcess.dataIsNull(responseValue)||dataProcess.arrayHasNull(responseValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		LoginUser user = tryCatchUserService.getUserByNickname((String)responseValue[0]);
		if(dataProcess.dataIsNull(user))
		{
			return returnStatus.Fail;
		}
		if(!"Role_HeadTeacher".equals(user.getRole()))
		{
			return returnStatus.NotHaveUser;
		}
		Teacher headTeacher = tryCatchTeacherService.getTeacherByNickname((String)responseValue[0]);
		if(dataProcess.dataIsNull(headTeacher))
		{
			return returnStatus.Fail;
		}
		List<Grade> teacherGradeList = headTeacher.getTeacherClass();
		
		Map<String,Object> gradeAndLongLat = new HashMap<String,Object>();
		
		final int teacherGradeListSize = teacherGradeList.size();
		for(int i = 0; i < teacherGradeListSize; i++)
		{
			Grade grade = teacherGradeList.get(i);
			Integer gradeID = grade.getGradeId();
			String gradeName = grade.getYearClass();
			String profession = grade.getProfession();
			String classID = grade.getClassId();
			String[] gradeMapKey = {"gradeID" , "gradeName" ,"profession" ,"classID"};
			Object[] gradeMapValue = {gradeID , gradeName ,profession , classID};
			Map<String,Object> gradeMap = dataProcess.getMapByStringArray(gradeMapKey, gradeMapValue);
			
			Map<String,Object> studentLongLat = new HashMap<String,Object>();
			long longLatLength = tryCatchLongLatService.getLongLatLengthByGrade(teacherGradeList.get(i).getYearClass(), teacherGradeList.get(i).getProfession(), teacherGradeList.get(i).getClassId());
			studentLongLat.put("grade", gradeMap);
			studentLongLat.put("longLatLength", longLatLength);
			gradeAndLongLat.put(""+i, studentLongLat);
		}
		if(dataProcess.dataIsNull(teacherGradeList)||dataProcess.listHasNull(teacherGradeList))
		{
			return returnStatus.Fail;
		}
		String longLatJson = jsonAnalyze.map2Json(gradeAndLongLat);
		if(dataProcess.dataIsNull(longLatJson))
		{
			return returnStatus.Fail;
		}
		return longLatJson;
	}
	
	
	/*
	 * @方法名：getLongLat(@RequestBody String requestJsonBody)
	 * @功能：得到学生共享的图片、语音
	 * @功能说明：根据学号返回这个学生共享的图片、语音
	 * @作者：李振强
	 * @创建时间：2014.4.15
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/mapOutLongLat.html")
	@ResponseBody
	public String getLongLat(HttpServletRequest request, @RequestBody String requestJsonBody)throws Exception
	{
		String[] requestKey = {"studentID"};
		Object[] responseValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestKey);
		if(dataProcess.dataIsNull(responseValue)||dataProcess.arrayHasNull(responseValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List <LongLat> longLatList = tryCatchLongLatService.getLongLatByStudentID((String)responseValue[0]);
		if(dataProcess.dataIsNull(longLatList)||dataProcess.listHasNull(longLatList))
		{
			return returnStatus.Fail;
		}
		Map<String,Object> studentLongLatMap = new HashMap<String,Object>();
		final int longLatListSize = longLatList.size();
		for(int j = 0; j < longLatListSize; j++)
		{
			LongLat longLat = longLatList.get(j);
			@SuppressWarnings("unchecked")
			Map<String,Object> longLatMap = (Map<String,Object>)mapOutputGetStudentFile.getStudentFileURL(request,longLat);
			studentLongLatMap.put(""+j, longLatMap);	
		}
		String json = jsonAnalyze.map2Json(studentLongLatMap);
		if(dataProcess.dataIsNull(json))
		{
			return returnStatus.Fail;
		}
		return json;
	}
	/**
	 * @方法名称：getLongLatMehtod(HttpServletRequest request)
	 * @功能：领导用户登录后直接查看最新的十条预警消息（给客户端返回）
	 * @author 贾少游
	 * @修改时间：2016.3.15
	 */
	@RequestMapping(value="/getLongLat.html")
	@ResponseBody
	public String getLongLatMehtod(HttpServletRequest request) throws Exception{
		
		//这里不需要前台传送数据，领导登录后直接查看预警消息
		//String userName=(String)dataProcess.getMapValueByString(jsonAnalyze.json2Map(requestJsonBody), "Nickname");
		List<LongLat> longLatList= new ArrayList<LongLat>();
			longLatList=tryCatchLongLatService.getAllLongLat();
			
			System.out.println("longLatList"+" "+longLatList);
			if(dataProcess.dataIsNull(longLatList))
			{
				return returnStatus.Fail;
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> longLatMap= new HashedMap();
			List<LongLatClientUtil> longLatClientUtil= new ArrayList<LongLatClientUtil>();
			
			for (int i = 0; i < longLatList.size(); i++)
			{
				LongLatClientUtil latClientUtil = new LongLatClientUtil();
				Student student = longLatList.get(i).getStudent();
				latClientUtil.setLonglattype(longLatList.get(i).getLonglattype());
				latClientUtil.setLatitude(longLatList.get(i).getLatitude());
				latClientUtil.setLongitude(longLatList.get(i).getLongitude());
				latClientUtil.setGeography(longLatList.get(i).getGeography());
				latClientUtil.setNickname(student.getUser().getNickName());
				latClientUtil.setHeadSculpture(student.getUser().getImgUrl());
				latClientUtil.setStudentName(student.getStudentName());
				latClientUtil.setStudentNumber(student.getId());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				latClientUtil.setLongLatDate(format.format(longLatList.get(i).getLongLatDate()));
				latClientUtil.setWord(longLatList.get(i).getWord());
				List<LongLatImage> latImages=longLatList.get(i).getLongLatImage();
				if (latImages!=null)
				{
					if (latImages.size()>0)
					{
						latClientUtil.setPictureURL(latImages.get(0).getImgurl());
					}
				}
				List<LongLatVoice>latVoices=longLatList.get(i).getLongLatVoice();
				if (latVoices!=null)
				{
					if (latVoices.size()>0)
					{
						latClientUtil.setSpeechURL(latVoices.get(0).getVoiceUrl());
					}
				}
				List<SmallLongLatImage>smallLongLatImages=longLatList.get(i).getSmallLongLatImages();
				if (smallLongLatImages!=null)
				{
					if (smallLongLatImages.size()>0)
					{
						latClientUtil.setThumbnailPictureURL(smallLongLatImages.get(0).getImgurl());
					}
				}
				longLatMap.put("longLat"+i, latClientUtil);
			}
			if(dataProcess.dataIsNull(longLatClientUtil)){
				return  returnStatus.Fail;
			}
			@SuppressWarnings("unchecked")
			Map<String, Object>map =new HashedMap();
			map.put("LongLat", longLatMap);
			String json=jsonAnalyze.map2Json(map);
			if (dataProcess.dataIsNull(json))
			{
				return returnStatus.Fail;
			}
			return json;
		}
	
	/**
	 * @修改：LongLatClientUtil添加longlattype字段，给客户端返回预警信息
	 * @功能：领导通过查询学院，专业，班级来查看学生的预警信息
	 * @修改时间：2015.11.21
	 */
	@RequestMapping(value="/getLongLatByGrade.html")
	@ResponseBody
	public String getLongLatByGradeMehtod(HttpServletRequest request,@RequestBody String requestJsonBody) throws Exception{
		String[] requestKey = {"gradeID","grade","profession","classID"};
		Object[] responseValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, requestKey);
		if(dataProcess.dataIsNull(responseValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Integer gradeID = tryCatchInteger.String2Number((String)responseValue[0]);
		if(dataProcess.dataIsNull(gradeID))
		{
			gradeID = tryCatchGradeService.getGradeIDByGradePropertyClassID((String)responseValue[1], (String)responseValue[2], (String)responseValue[3]);
		}
		if(dataProcess.dataIsNull(gradeID))
		{
			return returnStatus.Fail;
		}
		List <LongLat> longLatList = new ArrayList<LongLat>();
		longLatList = tryCatchLongLatService.getLongLatByGrade(gradeID);
		if(dataProcess.dataIsNull(longLatList))
		{
			return returnStatus.Fail;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> longLatMap = new HashedMap();
		List<LongLatClientUtil> latClientUtils = new ArrayList<LongLatClientUtil>();
		for(int i=0;i<longLatList.size();i++){
			//修改部分 LongLatClientUtil中添加longlattype
			LongLatClientUtil latClientUtil = new LongLatClientUtil();
			Student student = longLatList.get(i).getStudent();
			latClientUtil.setLonglattype(longLatList.get(i).getLonglattype());
			latClientUtil.setNickname(student.getUser().getNickName());
			latClientUtil.setHeadSculpture(student.getUser().getImgUrl());
			latClientUtil.setStudentName(student.getStudentName());
			latClientUtil.setStudentNumber(student.getId());
			latClientUtil.setGeography(longLatList.get(i).getGeography());
			latClientUtil.setLatitude(longLatList.get(i).getLatitude());
			latClientUtil.setLongitude(longLatList.get(i).getLongitude());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			latClientUtil.setLongLatDate(format.format(longLatList.get(i).getLongLatDate()));
			latClientUtil.setWord(longLatList.get(i).getWord());
			List<LongLatImage> latImages = longLatList.get(i).getLongLatImage();
			if (latImages!=null){
				if(latImages.size()>0)latClientUtil.setPictureURL(latImages.get(0).getImgurl());
				}
			List<SmallLongLatImage> smallLongLatImages = longLatList.get(i).getSmallLongLatImages();
			if(smallLongLatImages!=null){
				if(smallLongLatImages.size()>0)latClientUtil.setThumbnailPictureURL(smallLongLatImages.get(0).getImgurl());
			}
			List<LongLatVoice> latVoices = longLatList.get(i).getLongLatVoice();
			if(latVoices!=null){
				if(latVoices.size()>0)latClientUtil.setSpeechURL(latVoices.get(0).getVoiceUrl());
			}
			longLatMap.put("longLat"+i, latClientUtil);
		}
		if(dataProcess.dataIsNull(latClientUtils)){
			return  returnStatus.Fail;
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> map = new HashedMap();
		map.put("longLat", longLatMap);
		String json = jsonAnalyze.map2Json(map);
		if(dataProcess.dataIsNull(json))
		{
			return returnStatus.Fail;
		}
		return json;
	}
	
	/**
	 * @author lyf
	 * @功能：给客户端学生科返回预警信息
	 * @时间：2015.11.23
	 */
	@RequestMapping(value="/CilentStudentAdmingetEy.html")
	@ResponseBody
	public String getLongLatClientUtilByStudentAdmin(HttpServletRequest request) throws Exception{
		List <LongLat> longLatList = new ArrayList<LongLat>();
		longLatList = tryCatchLongLatService.getLongLatClientUtilByStudentAdmin();
		if(dataProcess.dataIsNull(longLatList))
		{
			return returnStatus.Fail;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> longLatMap = new HashedMap();
		List<LongLatClientUtil> latClientUtils = new ArrayList<LongLatClientUtil>();
		for(int i=0;i<longLatList.size();i++){
			//修改部分 LongLatClientUtil中添加longlattype
			LongLatClientUtil latClientUtil = new LongLatClientUtil();
			Student student = longLatList.get(i).getStudent();
			latClientUtil.setLonglattype(longLatList.get(i).getLonglattype());
			latClientUtil.setNickname(student.getUser().getNickName());
			latClientUtil.setHeadSculpture(student.getUser().getImgUrl());
			latClientUtil.setStudentName(student.getStudentName());
			latClientUtil.setStudentNumber(student.getId());
			latClientUtil.setGeography(longLatList.get(i).getGeography());
			latClientUtil.setLatitude(longLatList.get(i).getLatitude());
			latClientUtil.setLongitude(longLatList.get(i).getLongitude());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			latClientUtil.setLongLatDate(format.format(longLatList.get(i).getLongLatDate()));
			latClientUtil.setWord(longLatList.get(i).getWord());
			List<LongLatImage> latImages = longLatList.get(i).getLongLatImage();
			if (latImages!=null){
				if(latImages.size()>0)latClientUtil.setPictureURL(latImages.get(0).getImgurl());
				}
			List<SmallLongLatImage> smallLongLatImages = longLatList.get(i).getSmallLongLatImages();
			if(smallLongLatImages!=null){
				if(smallLongLatImages.size()>0)latClientUtil.setThumbnailPictureURL(smallLongLatImages.get(0).getImgurl());
			}
			List<LongLatVoice> latVoices = longLatList.get(i).getLongLatVoice();
			if(latVoices!=null){
				if(latVoices.size()>0)latClientUtil.setSpeechURL(latVoices.get(0).getVoiceUrl());
			}
			longLatMap.put("longLat"+i, latClientUtil);
		}
		if(dataProcess.dataIsNull(latClientUtils)){
			return  returnStatus.Fail;
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> map = new HashedMap();
		map.put("longLat", longLatMap);
		String json = jsonAnalyze.map2Json(map);
		if(dataProcess.dataIsNull(json))
		{
			return returnStatus.Fail;
		}
		return json;
	}
	
	/**
	 * @author lyf
	 * @功能：给客户端学院教学科返回预警信息
	 * @时间：2015.11.23
	 */
	@RequestMapping(value="/CilentCollegeAdmingetEy.html")
	@ResponseBody
	public String getLongLatClientUtilByCilentCollegeAdmin(HttpServletRequest request) throws Exception{
		List <LongLat> longLatList = new ArrayList<LongLat>();
		longLatList = tryCatchLongLatService.getLongLatClientUtilByCollegeAdmin();
		if(dataProcess.dataIsNull(longLatList))
		{
			return returnStatus.Fail;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> longLatMap = new HashedMap();
		List<LongLatClientUtil> latClientUtils = new ArrayList<LongLatClientUtil>();
		for(int i=0;i<longLatList.size();i++){
			//修改部分 LongLatClientUtil中添加longlattype
			LongLatClientUtil latClientUtil = new LongLatClientUtil();
			Student student = longLatList.get(i).getStudent();
			latClientUtil.setLonglattype(longLatList.get(i).getLonglattype());
			latClientUtil.setNickname(student.getUser().getNickName());
			latClientUtil.setHeadSculpture(student.getUser().getImgUrl());
			latClientUtil.setStudentName(student.getStudentName());
			latClientUtil.setStudentNumber(student.getId());
			latClientUtil.setGeography(longLatList.get(i).getGeography());
			latClientUtil.setLatitude(longLatList.get(i).getLatitude());
			latClientUtil.setLongitude(longLatList.get(i).getLongitude());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			latClientUtil.setLongLatDate(format.format(longLatList.get(i).getLongLatDate()));
			latClientUtil.setWord(longLatList.get(i).getWord());
			List<LongLatImage> latImages = longLatList.get(i).getLongLatImage();
			if (latImages!=null){
				if(latImages.size()>0)latClientUtil.setPictureURL(latImages.get(0).getImgurl());
				}
			List<SmallLongLatImage> smallLongLatImages = longLatList.get(i).getSmallLongLatImages();
			if(smallLongLatImages!=null){
				if(smallLongLatImages.size()>0)latClientUtil.setThumbnailPictureURL(smallLongLatImages.get(0).getImgurl());
			}
			List<LongLatVoice> latVoices = longLatList.get(i).getLongLatVoice();
			if(latVoices!=null){
				if(latVoices.size()>0)latClientUtil.setSpeechURL(latVoices.get(0).getVoiceUrl());
			}
			longLatMap.put("longLat"+i, latClientUtil);
		}
		if(dataProcess.dataIsNull(latClientUtils)){
			return  returnStatus.Fail;
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> map = new HashedMap();
		map.put("longLat", longLatMap);
		String json = jsonAnalyze.map2Json(map);
		if(dataProcess.dataIsNull(json))
		{
			return returnStatus.Fail;
		}
		return json;
	}
	
	/**
	 * @author lyf
	 * @功能：给客户端后勤集团返回预警信息
	 * @时间：2015.11.23
	 */
	@RequestMapping(value="/CilentLogisticsAdmingetEy.html")
	@ResponseBody
	public String getLongLatClientUtilByLogisticsAdmin(HttpServletRequest request) throws Exception{
		List <LongLat> longLatList = new ArrayList<LongLat>();
		longLatList = tryCatchLongLatService.getLongLatClientUtilByLogisticsAdmin();
		if(dataProcess.dataIsNull(longLatList))
		{
			return returnStatus.Fail;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> longLatMap = new HashedMap();
		List<LongLatClientUtil> latClientUtils = new ArrayList<LongLatClientUtil>();
		for(int i=0;i<longLatList.size();i++){
			//修改部分 LongLatClientUtil中添加longlattype
			LongLatClientUtil latClientUtil = new LongLatClientUtil();
			Student student = longLatList.get(i).getStudent();
			latClientUtil.setLonglattype(longLatList.get(i).getLonglattype());
			latClientUtil.setNickname(student.getUser().getNickName());
			latClientUtil.setHeadSculpture(student.getUser().getImgUrl());
			latClientUtil.setStudentName(student.getStudentName());
			latClientUtil.setStudentNumber(student.getId());
			latClientUtil.setGeography(longLatList.get(i).getGeography());
			latClientUtil.setLatitude(longLatList.get(i).getLatitude());
			latClientUtil.setLongitude(longLatList.get(i).getLongitude());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			latClientUtil.setLongLatDate(format.format(longLatList.get(i).getLongLatDate()));
			latClientUtil.setWord(longLatList.get(i).getWord());
			List<LongLatImage> latImages = longLatList.get(i).getLongLatImage();
			if (latImages!=null){
				if(latImages.size()>0)latClientUtil.setPictureURL(latImages.get(0).getImgurl());
				}
			List<SmallLongLatImage> smallLongLatImages = longLatList.get(i).getSmallLongLatImages();
			if(smallLongLatImages!=null){
				if(smallLongLatImages.size()>0)latClientUtil.setThumbnailPictureURL(smallLongLatImages.get(0).getImgurl());
			}
			List<LongLatVoice> latVoices = longLatList.get(i).getLongLatVoice();
			if(latVoices!=null){
				if(latVoices.size()>0)latClientUtil.setSpeechURL(latVoices.get(0).getVoiceUrl());
			}
			longLatMap.put("longLat"+i, latClientUtil);
		}
		if(dataProcess.dataIsNull(latClientUtils)){
			return  returnStatus.Fail;
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> map = new HashedMap();
		map.put("longLat", longLatMap);
		String json = jsonAnalyze.map2Json(map);
		if(dataProcess.dataIsNull(json))
		{
			return returnStatus.Fail;
		}
		return json;
	}
	
	/**
	 * @author lyf
	 * @功能：给客户端宿管科返回预警信息
	 * @时间：2015.11.23
	 */
	@RequestMapping(value="/CilentDormAdmingetEy.html")
	@ResponseBody
	public String getLongLatClientUtilByDormAdmin(HttpServletRequest request) throws Exception{
		List <LongLat> longLatList = new ArrayList<LongLat>();
		longLatList = tryCatchLongLatService.getLongLatClientUtilByDormAdmin();
		if(dataProcess.dataIsNull(longLatList))
		{
			return returnStatus.Fail;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> longLatMap = new HashedMap();
		List<LongLatClientUtil> latClientUtils = new ArrayList<LongLatClientUtil>();
		for(int i=0;i<longLatList.size();i++){
			//修改部分 LongLatClientUtil中添加longlattype
			LongLatClientUtil latClientUtil = new LongLatClientUtil();
			Student student = longLatList.get(i).getStudent();
			latClientUtil.setLonglattype(longLatList.get(i).getLonglattype());
			latClientUtil.setNickname(student.getUser().getNickName());
			latClientUtil.setHeadSculpture(student.getUser().getImgUrl());
			latClientUtil.setStudentName(student.getStudentName());
			latClientUtil.setStudentNumber(student.getId());
			latClientUtil.setGeography(longLatList.get(i).getGeography());
			latClientUtil.setLatitude(longLatList.get(i).getLatitude());
			latClientUtil.setLongitude(longLatList.get(i).getLongitude());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			latClientUtil.setLongLatDate(format.format(longLatList.get(i).getLongLatDate()));
			latClientUtil.setWord(longLatList.get(i).getWord());
			List<LongLatImage> latImages = longLatList.get(i).getLongLatImage();
			if (latImages!=null){
				if(latImages.size()>0)latClientUtil.setPictureURL(latImages.get(0).getImgurl());
				}
			List<SmallLongLatImage> smallLongLatImages = longLatList.get(i).getSmallLongLatImages();
			if(smallLongLatImages!=null){
				if(smallLongLatImages.size()>0)latClientUtil.setThumbnailPictureURL(smallLongLatImages.get(0).getImgurl());
			}
			List<LongLatVoice> latVoices = longLatList.get(i).getLongLatVoice();
			if(latVoices!=null){
				if(latVoices.size()>0)latClientUtil.setSpeechURL(latVoices.get(0).getVoiceUrl());
			}
			longLatMap.put("longLat"+i, latClientUtil);
		}
		if(dataProcess.dataIsNull(latClientUtils)){
			return  returnStatus.Fail;
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> map = new HashedMap();
		map.put("longLat", longLatMap);
		String json = jsonAnalyze.map2Json(map);
		if(dataProcess.dataIsNull(json))
		{
			return returnStatus.Fail;
		}
		return json;
	}

}

