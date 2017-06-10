
package com.b505.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.LongLat;
import com.b505.bean.Teacher;
import com.b505.bean.util.HteacherLongLatUtil;
import com.b505.bean.util.LongLatUtil;
import com.b505.bean.util.LongLatUtil1;
import com.b505.json.JsonAnalyze;
import com.b505.service.ILongLatService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchGradeService;
import com.b505.tools.TryCatchLongLatService;
import com.b505.tools.TryCatchStudentService;
import com.b505.tools.TryCatchTeacherService;

@Controller
public class HeadTeacherEmergencyController {
	@Autowired
	private ILongLatService longLatService;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private TryCatchLongLatService tryCatchLongLatService;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private TryCatchGradeService trCatchGradeService;
	@Autowired
	private TryCatchTeacherService tryCatchTeacherService;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	
	@RequestMapping(value = "/headTeacherShowEmergency.html")
	public String getHeadTeacherShowEmergency() {
		return "headTeacherShowEy";
	}
	/*
	 * 学校领导查询预警信息
	 */
	@RequestMapping(value = "/leaderShowEmergency.html")
	public String getLeaderShowEmergency() {
		return "leaderShowEy";
	}

	@RequestMapping(value = "/getTeacherShowEy.html")
	public String getLeaderShowTeacherEmergency() {
		return "leaderShowTeacherEy";
	}
	
	/*
	 * 学生科查询预警信息
	 */
	@RequestMapping(value = "/getStudentAdminShowEy.html")
	public String getStudentAdminShowEmergency() {
		return "studentAdminShowEy";
	}
	
	
	
	/*
	 * 后勤查询预警信息
	 */	
	@RequestMapping(value = "/getLogisticsAdminShowEy.html")
	public String getLogisticsAdminShowEmergency() {
		return "logisticsAdminShowEy";
	}
	
	/*
	 * 宿管科查询预警信息
	 */	
	@RequestMapping(value = "/getDormAdminShowEy.html")
	public String getDormAdminShowEmergency() {
		return "dormAdminShowEy";
	}
	
	@RequestMapping(value = "/getEmergencyByGrade.html")
	@ResponseBody
	public String getEmergencyByGrade(@RequestBody String requestJsonBody)throws Exception {
		String string;
		Map<String,Object> map = new HashMap<String,Object>();
		Integer gradeId;
		try
		{
			 map = jsonAnalyze.json2Map(requestJsonBody);	
			 gradeId = (int)map.get("gradeID");
		}catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("辅导员查询紧急情况;出错");
			return null;
		}
		if (dataProcess.dataIsNull(gradeId)) {
			return returnStatus.CannotAnalyzeData;
		}
		List<LongLatUtil> longLatUtil = tryCatchLongLatService
				.getLongLatUtilByGradeId(gradeId);

		if (longLatUtil == null || "".equals(longLatUtil)|| longLatUtil.size() < 0) {
			return returnStatus.Fail;
		} 
		List<LongLatUtil1> longLatUtil1 = new ArrayList<LongLatUtil1>();
		for(int i=0;i<longLatUtil.size();i++){
			LongLatUtil1 latUtil1 = new LongLatUtil1();
			int mapId = (int)longLatUtil.get(i).getMapId();
			String longlattype = longLatUtil.get(i).getLonglattype();
			Date longLatdate = longLatUtil.get(i).getLongLatDate();
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化当前日期
			String longLatDate = dateFm.format(longLatdate);
			String studentNumber = longLatUtil.get(i).getStudentNumber();
			String studentName = longLatUtil.get(i).getStudentName();
			String word = longLatUtil.get(i).getWord();
			String geography = longLatUtil.get(i).getGeography();
			
			latUtil1.setMapId(mapId);
			latUtil1.setLonglattype(longlattype);
			latUtil1.setLongLatDate(longLatDate);
			latUtil1.setStudentNumber(studentNumber);
			latUtil1.setStudentName(studentName);
			latUtil1.setWord(word);
			latUtil1.setGeography(geography);
			
			longLatUtil1.add(latUtil1);
		}	
		
		if(dataProcess.dataIsNull(longLatUtil1)||dataProcess.listHasNull(longLatUtil1))
		{
			return returnStatus.Fail;
		}else{
			string = jsonAnalyze.list2Json(longLatUtil1);
		}
		
		return string;
	}

	/**
	 * 服务端领导查看学生紧急情况
	 */
	@RequestMapping(value = "/LeadergetEmergencyByGrade.html")
	@ResponseBody
	public String LeadergetEmergencyByGrade(@RequestBody String requestJsonBody)throws Exception {
		
		System.out.println("requestJsonBody"+" "+requestJsonBody);
		Map<String,Object> map = new HashMap<String,Object>();
		/*String yearClass ;
		String profession ;
		String classId ;*/
		String string;
		String type;
		String STime;
		String ETime;
		try
		{
			 map = jsonAnalyze.json2Map(requestJsonBody);	
			 type = (String)map.get("longlattype");
			 STime = (String)map.get("STime");
			 ETime = (String)map.get("ETime");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("领导查询紧急情况;出错");
			return null;
		}
		//初始化加载所有紧急情况给领导看,包含辅导员预警信息
		if("".equals(type)||"".equals(STime)||"".equals(ETime)){
			//此处加载学生预警信息
			List<LongLatUtil> longLatUtil = tryCatchLongLatService
					.getAllLongLatUtil();
			if (longLatUtil == null || "".equals(longLatUtil)
					|| longLatUtil.size() < 0) {
				return returnStatus.Fail;
			} 
			List<LongLatUtil1> longLatUtils = new ArrayList<LongLatUtil1>();
			for(int i=0;i<longLatUtil.size();i++){
				LongLatUtil1 latUtil1 = new LongLatUtil1();
				int mapId = (int)longLatUtil.get(i).getMapId();
				String longlattype = longLatUtil.get(i).getLonglattype();
				Date longLatdate = longLatUtil.get(i).getLongLatDate();
				SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化当前日期
				String longLatDate = dateFm.format(longLatdate);
				String studentNumber = longLatUtil.get(i).getStudentNumber();
				String studentName = longLatUtil.get(i).getStudentName();
				String word = longLatUtil.get(i).getWord();
				String geography = longLatUtil.get(i).getGeography();
				
				latUtil1.setMapId(mapId);
				latUtil1.setLonglattype(longlattype);
				latUtil1.setLongLatDate(longLatDate);
				latUtil1.setStudentNumber(studentNumber);
				latUtil1.setStudentName(studentName);
				latUtil1.setWord(word);
				latUtil1.setGeography(geography);
				
				longLatUtils.add(latUtil1);
			}	
			
			if(dataProcess.dataIsNull(longLatUtils)||dataProcess.listHasNull(longLatUtils))
			{
				return returnStatus.Fail;
			}
			//=================================================================================
			//此处加载辅导员预警信息
			List<HteacherLongLatUtil> hteacherlongLatUtil = tryCatchLongLatService
					.getHteacherAllLongLatUtil();
			if (hteacherlongLatUtil == null || "".equals(hteacherlongLatUtil)
					|| longLatUtil.size() < 0) {
				return jsonAnalyze.list2Json(longLatUtils);
			} 	
			return jsonAnalyze.list2Json(longLatUtils);
		//按日期查询	
		}else{
			List<LongLatUtil> longLatUtil = tryCatchLongLatService.getLongLatUtilByDate(STime,ETime);
			if (longLatUtil == null || "".equals(longLatUtil)
					|| longLatUtil.size() < 0) {
				return returnStatus.Fail;
			} 
			List<LongLatUtil1> longLatUtil1 = new ArrayList<LongLatUtil1>();
			for(int i=0;i<longLatUtil.size();i++){
				LongLatUtil1 latUtil1 = new LongLatUtil1();
				int mapId = (int)longLatUtil.get(i).getMapId();
				String longlattype = longLatUtil.get(i).getLonglattype();
				Date longLatdate = longLatUtil.get(i).getLongLatDate();
				SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化当前日期
				String longLatDate = dateFm.format(longLatdate);
				String studentNumber = longLatUtil.get(i).getStudentNumber();
				String studentName = longLatUtil.get(i).getStudentName();
				String word = longLatUtil.get(i).getWord();
				String geography = longLatUtil.get(i).getGeography();
				
				latUtil1.setMapId(mapId);
				latUtil1.setLonglattype(longlattype);
				latUtil1.setLongLatDate(longLatDate);
				latUtil1.setStudentNumber(studentNumber);
				latUtil1.setStudentName(studentName);
				latUtil1.setWord(word);
				latUtil1.setGeography(geography);
				
				longLatUtil1.add(latUtil1);
			}	
			
			if(dataProcess.dataIsNull(longLatUtil1)||dataProcess.listHasNull(longLatUtil1))
			{
				return returnStatus.Fail;
			}else{
				string = jsonAnalyze.list2Json(longLatUtil1);
			}		
			return string;
		
		}
	}
	
	/**
	 * 服务端领导查看教师紧急情况
	 */
	@RequestMapping(value = "/LeadergetTeacherEmergencyByGrade.html")
	@ResponseBody
	public String LeadergetTeacherEmergencyByGrade(@RequestBody String requestJsonBody)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String string;
		String type;
		String STime;
		String ETime;
		try
		{
			 map = jsonAnalyze.json2Map(requestJsonBody);	
			 type = (String)map.get("longlattype");
			 STime = (String)map.get("STime");
			 ETime = (String)map.get("ETime");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("领导查询辅导员紧急情况;出错");
			return null;
		}
		//初始化加载所有辅导员预警信息
		if("".equals(type)||"".equals(STime)||"".equals(ETime)){
			
			//此处加载辅导员预警信息
			List<HteacherLongLatUtil> hteacherlongLatUtil = tryCatchLongLatService
					.getHteacherAllLongLatUtil();
			if (hteacherlongLatUtil == null || "".equals(hteacherlongLatUtil)
					|| hteacherlongLatUtil.size() < 0) {
				return returnStatus.Fail;
			} 
			List<LongLatUtil1> longLatUtil1 = new ArrayList<LongLatUtil1>();
			for(int i=0;i<hteacherlongLatUtil.size();i++){
				LongLatUtil1 latUtil2 = new LongLatUtil1();
				int mapId = (int)hteacherlongLatUtil.get(i).getMapId();
				String longlattype = hteacherlongLatUtil.get(i).getLonglattype();
				Date longLatdate = hteacherlongLatUtil.get(i).getLongLatDate();
				SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化当前日期
				String longLatDate = dateFm.format(longLatdate);
				//此处学生学号为教师工号
				String studentNumber = hteacherlongLatUtil.get(i).getTeacherNumber();
				//此处教师姓名由教师工号取到
				String studentName = tryCatchTeacherService.getTeachernameByNickName(studentNumber);
				String word = hteacherlongLatUtil.get(i).getWord();
				String geography = hteacherlongLatUtil.get(i).getGeography();
				
				latUtil2.setMapId(mapId);
				latUtil2.setLonglattype(longlattype);
				latUtil2.setLongLatDate(longLatDate);
				latUtil2.setStudentNumber(studentNumber);
				latUtil2.setStudentName(studentName);
				latUtil2.setWord(word);
				latUtil2.setGeography(geography);
				
				longLatUtil1.add(latUtil2);
			}		
			return jsonAnalyze.list2Json(longLatUtil1);
		//按日期查询
		}else{
			List<LongLatUtil> longLatUtil = tryCatchLongLatService.getTeacherLongLatUtilByDate(STime,ETime);
			if (longLatUtil == null || "".equals(longLatUtil)
					|| longLatUtil.size() < 0) {
				return returnStatus.Fail;
			} 
			List<LongLatUtil1> longLatUtil1 = new ArrayList<LongLatUtil1>();
			for(int i=0;i<longLatUtil.size();i++){
				LongLatUtil1 latUtil1 = new LongLatUtil1();
				int mapId = (int)longLatUtil.get(i).getMapId();
				String longlattype = longLatUtil.get(i).getLonglattype();
				Date longLatdate = longLatUtil.get(i).getLongLatDate();
				SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化当前日期
				String longLatDate = dateFm.format(longLatdate);
				String studentNumber = longLatUtil.get(i).getStudentNumber();
				String studentName = longLatUtil.get(i).getStudentName();
				String word = longLatUtil.get(i).getWord();
				String geography = longLatUtil.get(i).getGeography();
				
				latUtil1.setMapId(mapId);
				latUtil1.setLonglattype(longlattype);
				latUtil1.setLongLatDate(longLatDate);
				latUtil1.setStudentNumber(studentNumber);
				latUtil1.setStudentName(studentName);
				latUtil1.setWord(word);
				latUtil1.setGeography(geography);
				
				longLatUtil1.add(latUtil1);
			}	
			
			if(dataProcess.dataIsNull(longLatUtil1)||dataProcess.listHasNull(longLatUtil1))
			{
				return returnStatus.Fail;
			}else{
				string = jsonAnalyze.list2Json(longLatUtil1);
			}		
			return string;
		
		}
	}
	
	/**
	 * 服务端领导按类型查看紧急情况
	 */
	@RequestMapping(value = "/LeadergetEmergencyByType.html")
	@ResponseBody
	public String LeadergetEmergencyByType(@RequestBody String requestJsonBody)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String string;
		String type;
		try
		{
			 map = jsonAnalyze.json2Map(requestJsonBody);	
			 type = (String)map.get("longlattype");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("领导按类型查询紧急情况;出错");
			return null;
		}

	   //按类型查询	
	   if("".equals(type)&&type.equals(null)){
		   
		      return returnStatus.Fail;

		}else{
			List<LongLatUtil> longLatUtil = tryCatchLongLatService.getLongLatUtilByLongLattype(type);
			if (longLatUtil == null || "".equals(longLatUtil)
					|| longLatUtil.size() < 0) {
				return returnStatus.Fail;
			} 
			List<LongLatUtil1> longLatUtil1 = new ArrayList<LongLatUtil1>();
			for(int i=0;i<longLatUtil.size();i++){
				LongLatUtil1 latUtil1 = new LongLatUtil1();
				int mapId = (int)longLatUtil.get(i).getMapId();
				String longlattype = longLatUtil.get(i).getLonglattype();
				Date longLatdate = longLatUtil.get(i).getLongLatDate();
				SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化当前日期
				String longLatDate = dateFm.format(longLatdate);
				String studentNumber = longLatUtil.get(i).getStudentNumber();
				String studentName = longLatUtil.get(i).getStudentName();
				String word = longLatUtil.get(i).getWord();
				String geography = longLatUtil.get(i).getGeography();
				
				latUtil1.setMapId(mapId);
				latUtil1.setLonglattype(longlattype);
				latUtil1.setLongLatDate(longLatDate);
				latUtil1.setStudentNumber(studentNumber);
				latUtil1.setStudentName(studentName);
				latUtil1.setWord(word);
				latUtil1.setGeography(geography);
				
				longLatUtil1.add(latUtil1);
			}	
			
			if(dataProcess.dataIsNull(longLatUtil1)||dataProcess.listHasNull(longLatUtil1))
			{
				return returnStatus.Fail;
			}else{
				string = jsonAnalyze.list2Json(longLatUtil1);
			}		
			return string;
		}
	}
	
	/**
	 * 服务端领导按类型查看教师紧急情况
	 */
	@RequestMapping(value = "/LeadergetTeacherEmergencyByType.html")
	@ResponseBody
	public String LeadergetTeacherEmergencyByType(@RequestBody String requestJsonBody)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String string;
		String type;
		try
		{
			 map = jsonAnalyze.json2Map(requestJsonBody);	
			 type = (String)map.get("longlattype");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("领导按类型查询教师紧急情况;出错");
			return null;
		}

	   //按类型查询	
	   if("".equals(type)&&type.equals(null)){
		   
		      return returnStatus.Fail;

		}else{
			List<LongLatUtil> longLatUtil = tryCatchLongLatService.getTeacherLongLatUtilByLongLattype(type);
			if (longLatUtil == null || "".equals(longLatUtil)
					|| longLatUtil.size() < 0) {
				return returnStatus.Fail;
			} 
			List<LongLatUtil1> longLatUtil1 = new ArrayList<LongLatUtil1>();
			for(int i=0;i<longLatUtil.size();i++){
				LongLatUtil1 latUtil1 = new LongLatUtil1();
				int mapId = (int)longLatUtil.get(i).getMapId();
				String longlattype = longLatUtil.get(i).getLonglattype();
				Date longLatdate = longLatUtil.get(i).getLongLatDate();
				SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化当前日期
				String longLatDate = dateFm.format(longLatdate);
				String studentNumber = longLatUtil.get(i).getStudentNumber();
				String studentName = longLatUtil.get(i).getStudentName();
				String word = longLatUtil.get(i).getWord();
				String geography = longLatUtil.get(i).getGeography();
				
				latUtil1.setMapId(mapId);
				latUtil1.setLonglattype(longlattype);
				latUtil1.setLongLatDate(longLatDate);
				latUtil1.setStudentNumber(studentNumber);
				latUtil1.setStudentName(studentName);
				latUtil1.setWord(word);
				latUtil1.setGeography(geography);
				
				longLatUtil1.add(latUtil1);
			}	
			
			if(dataProcess.dataIsNull(longLatUtil1)||dataProcess.listHasNull(longLatUtil1))
			{
				return returnStatus.Fail;
			}else{
				string = jsonAnalyze.list2Json(longLatUtil1);
			}		
			return string;
		}
	}
	
	@RequestMapping(value = "/headTeacherShowEyDetail.html")
	public String getHeadTeacherShowEy(HttpServletRequest request) {
		Long mapId = Long.parseLong(request.getParameter("mapId"));
		HttpSession hp = request.getSession();
		hp.setAttribute("mapId", mapId);
		return "headTeacherDetailEy";
	}
	
	@RequestMapping(value = "/LeaderShowTeacherEyDetail.html")
	public String getLeaderShowEy(HttpServletRequest request) {
		Long mapId = Long.parseLong(request.getParameter("mapId"));
		HttpSession hp = request.getSession();
		hp.setAttribute("mapId", mapId);
		return "leaderDetailEy";
	}
	
	/**
	 * 辅导员、领导查询紧急情况的详细信息
	 */
	@RequestMapping(value = "/headTeacherGetEmergencyByStudentNumberAndMapId.html")
	@ResponseBody
	public String getEmergencyByStudentNumber(HttpServletRequest request)
			throws IOException {
		HttpSession hp = request.getSession();
		Long mapId = (Long) hp.getAttribute("mapId");
		Map<String, Object> longLatMap = new HashMap<String, Object>();
		LongLat longLat = tryCatchLongLatService.getLongLatByLatById(mapId);
		if (dataProcess.dataIsNull(longLat)) {
			return returnStatus.Fail;
		}
		//此处肯定是学生
		longLatMap.put("studentNumber", longLat.getStudent().getId());
		longLatMap.put("studentName", longLat.getStudent().getStudentName());
		longLatMap.put("studentPhone", longLat.getStudent().getStudentPhone());
		longLatMap.put("yearClass", longLat.getStudent().getStudentClass()
				.getYearClass());
		longLatMap.put("profession", longLat.getStudent().getStudentClass()
				.getProfession());
		longLatMap.put("studentClass", longLat.getStudent().getStudentClass()
				.getClassId());
		String pictrueURL = longLat.getLongLatImage().get(0).getImgurl();
		longLatMap.put("pictrueURL",
				pictrueURL.substring(pictrueURL.indexOf("picture")));
		String imageURL = longLat.getLongLatVoice().get(0).getVoiceUrl();
		longLatMap.put("voiceURL",
				imageURL.substring(imageURL.indexOf("speech")));
		longLatMap.put("word", longLat.getWord());
		if (dataProcess.dataIsNull(longLatMap)) {
			return returnStatus.Fail;
		}
		String json = jsonAnalyze.map2Json(longLatMap);
		return json;

	}
	
	/**
	 * 领导查询辅导员紧急情况的详细信息
	 */
	@RequestMapping(value = "/leaderGetTeacherEmergencyByTeachernickNameAndMapId.html")
	@ResponseBody
	public String getTeacherEmergencyByTeachernickName(HttpServletRequest request)
			throws IOException {
		HttpSession hp = request.getSession();
		Long mapId = (Long) hp.getAttribute("mapId");
		Map<String, Object> longLatMap = new HashMap<String, Object>();
		LongLat longLat = tryCatchLongLatService.getLongLatByLatById(mapId);
		if (dataProcess.dataIsNull(longLat)) {
			return returnStatus.Fail;
		}
		//此处肯定是辅导员
		longLatMap.put("studentNumber", longLat.getHteacherNickname());
		Teacher teacher;
		String teacherName = null;
		String teacherPhone = null;
		try {
			teacher = tryCatchTeacherService.getTeacherByNickname(longLat.getHteacherNickname());
			teacherName = teacher.getTeacherName();
			teacherPhone = teacher.getTeacherPhone();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		longLatMap.put("teachernickName", longLat.getHteacherNickname());
		longLatMap.put("teacherName", teacherName);
		longLatMap.put("teacherPhone", teacherPhone);
		String pictrueURL = longLat.getLongLatImage().get(0).getImgurl();
		longLatMap.put("pictrueURL",
				pictrueURL.substring(pictrueURL.indexOf("picture")));
		String imageURL = longLat.getLongLatVoice().get(0).getVoiceUrl();
		longLatMap.put("voiceURL",
				imageURL.substring(imageURL.indexOf("speech")));
		longLatMap.put("word", longLat.getWord());
		if (dataProcess.dataIsNull(longLatMap)) {
			return returnStatus.Fail;
		}
		String json = jsonAnalyze.map2Json(longLatMap);
		return json;
	}

	@RequestMapping(value = "/deleteMapByMapId.html")
	@ResponseBody
	public String deleteMapByMapId(@RequestBody String requestJsonBody) throws Exception {
		@SuppressWarnings("unchecked")
		List<Long> mapId = (List<Long>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if (dataProcess.dataIsNull(mapId)) {
			return returnStatus.CannotAnalyzeData;
		}
		for (int  i = 0; i < mapId.size(); i++) {
			LongLat longLat = new LongLat();
			longLat.setMapId(Long.valueOf(mapId.get(i)+""));
			if (!tryCatchLongLatService.deletedLongLatByID(longLat)) {
				return returnStatus.Fail;
			}
		}
			return returnStatus.Success;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function web学生科查询预警消息
	 * @param requestJsonBody
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/StudentAdmingetEy.html")
	@ResponseBody
	public String StudentAdmingetEy()throws Exception {
	
		String string;
		List<LongLatUtil> longLatUtil = tryCatchLongLatService.getLongLatUtilByStudentAdmin();
		if (longLatUtil == null || "".equals(longLatUtil)
				|| longLatUtil.size() < 0) {
			return returnStatus.Fail;
		}
		List<LongLatUtil1> longLatUtil1 = new ArrayList<LongLatUtil1>();
		for(int i=0;i<longLatUtil.size();i++){
			LongLatUtil1 latUtil1 = new LongLatUtil1();
			int mapId = (int)longLatUtil.get(i).getMapId();
			String longlattype = longLatUtil.get(i).getLonglattype();
			Date longLatdate = longLatUtil.get(i).getLongLatDate();
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm"); //格式化当前日期
			String longLatDate = dateFm.format(longLatdate);
			String studentNumber = longLatUtil.get(i).getStudentNumber();
			String studentName = longLatUtil.get(i).getStudentName();
			String word = longLatUtil.get(i).getWord();
			String geography = longLatUtil.get(i).getGeography();
			
			latUtil1.setMapId(mapId);
			latUtil1.setLonglattype(longlattype);
			latUtil1.setLongLatDate(longLatDate);
			latUtil1.setStudentNumber(studentNumber);
			latUtil1.setStudentName(studentName);
			latUtil1.setWord(word);
			latUtil1.setGeography(geography);
			
			longLatUtil1.add(latUtil1);
		}	
		
		if(dataProcess.dataIsNull(longLatUtil1)||dataProcess.listHasNull(longLatUtil1))
		{
			return returnStatus.Fail;
		}else{
			string = jsonAnalyze.list2Json(longLatUtil1);
		}		
		return string;
	}
	
	
	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function web后勤集团查询预警消息
	 * @param requestJsonBody
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/LogisticsAdmingetEy.html")
	@ResponseBody
	public String LogisticsAdmingetEy() throws Exception {
	
		String string;
		List<LongLatUtil> longLatUtil = tryCatchLongLatService.getLongLatUtilByLogisticsAdmin();
		if (longLatUtil == null || "".equals(longLatUtil)
				|| longLatUtil.size() < 0) {
			return returnStatus.Fail;
		}
		List<LongLatUtil1> longLatUtil1 = new ArrayList<LongLatUtil1>();
		for(int i=0;i<longLatUtil.size();i++){
			LongLatUtil1 latUtil1 = new LongLatUtil1();
			int mapId = (int)longLatUtil.get(i).getMapId();
			String longlattype = longLatUtil.get(i).getLonglattype();
			Date longLatdate = longLatUtil.get(i).getLongLatDate();
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化当前日期
			String longLatDate = dateFm.format(longLatdate);
			String studentNumber = longLatUtil.get(i).getStudentNumber();
			String studentName = longLatUtil.get(i).getStudentName();
			String word = longLatUtil.get(i).getWord();
			String geography = longLatUtil.get(i).getGeography();
			
			latUtil1.setMapId(mapId);
			latUtil1.setLonglattype(longlattype);
			latUtil1.setLongLatDate(longLatDate);
			latUtil1.setStudentNumber(studentNumber);
			latUtil1.setStudentName(studentName);
			latUtil1.setWord(word);
			latUtil1.setGeography(geography);
			
			longLatUtil1.add(latUtil1);
		}	
		
		if(dataProcess.dataIsNull(longLatUtil1)||dataProcess.listHasNull(longLatUtil1))
		{
			return returnStatus.Fail;
		}else{
			string = jsonAnalyze.list2Json(longLatUtil1);
		}		
		return string;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function web宿管科查询预警消息
	 * @param requestJsonBody
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/DormAdmingetEy.html")
	@ResponseBody
	public String DormAdmingetEy()throws Exception {
	
		String string;
		List<LongLatUtil> longLatUtil = tryCatchLongLatService.getLongLatUtilByDormAdmin();
		if (longLatUtil == null || "".equals(longLatUtil)
				|| longLatUtil.size() < 0) {
			return returnStatus.Fail;
		} 
		List<LongLatUtil1> longLatUtil1 = new ArrayList<LongLatUtil1>();
		for(int i=0;i<longLatUtil.size();i++){
			LongLatUtil1 latUtil1 = new LongLatUtil1();
			int mapId = (int)longLatUtil.get(i).getMapId();
			String longlattype = longLatUtil.get(i).getLonglattype();
			Date longLatdate = longLatUtil.get(i).getLongLatDate();
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化当前日期
			String longLatDate = dateFm.format(longLatdate);
			String studentNumber = longLatUtil.get(i).getStudentNumber();
			String studentName = longLatUtil.get(i).getStudentName();
			String word = longLatUtil.get(i).getWord();
			String geography = longLatUtil.get(i).getGeography();
			
			latUtil1.setMapId(mapId);
			latUtil1.setLonglattype(longlattype);
			latUtil1.setLongLatDate(longLatDate);
			latUtil1.setStudentNumber(studentNumber);
			latUtil1.setStudentName(studentName);
			latUtil1.setWord(word);
			latUtil1.setGeography(geography);
			
			longLatUtil1.add(latUtil1);
		}	
		
		if(dataProcess.dataIsNull(longLatUtil1)||dataProcess.listHasNull(longLatUtil1))
		{
			return returnStatus.Fail;
		}else{
			string = jsonAnalyze.list2Json(longLatUtil1);
		}		
		return string;
	}
}
