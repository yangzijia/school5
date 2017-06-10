package com.b505.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Dormcallover;
import com.b505.bean.DormcalloverCount;
import com.b505.bean.Student;
import com.b505.bean.util.DormcalloverUtil;
import com.b505.json.JsonAnalyze;
import com.b505.service.IStudentService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchDormcalloverCountService;
import com.b505.tools.TryCatchDormcalloverService;
import com.b505.tools.TryCatchStudentService;

@Controller
public class DormcalloverController {

	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private IStudentService studentService;
	@Autowired
	private TryCatchDormcalloverService tryCatchDormcalloverService;
	@Autowired
	private TryCatchDormcalloverCountService tryCatchDormcalloverCountService;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private AnalyzeData analyzeData;
	
	@RequestMapping(value = "/dormCallOverCheckOut.html")
	public String dormCallOverCheckOut(){
		return "hteacherDormcalloverSearch";
	}
	
	/*
	 * @方法名：SaveDormcallover(@RequestBody String requestJsonBody)
	 * @功能：保存客户端班委宿舍考勤点名结果 
	 * @作者：lyf
	 * @创建时间：2015.11.22 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveDormcallover.html")
	@ResponseBody
	public String SaveDormcallover(@RequestBody String requestJsonBody)throws Exception {

		Map<String, Object> DormcalloverMap = new HashMap<String, Object>();
		// 解析客户端数据
		DormcalloverMap = jsonAnalyze.json2Map(requestJsonBody);

		// 点名班委,客户端手动输入
		String name = (String) DormcalloverMap.get("course");
		// 点名日期
		String date1 = (String) DormcalloverMap.get("date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date =  sdf.parse(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 点名时正常上课的学生列表
		Map<String, Object> allStudent = (Map<String, Object>) DormcalloverMap
				.get("allStudent");
		if (allStudent == null || "".equals(allStudent)) {
			return returnStatus.CannotAnalyzeData;
		}
		Map<String, Object> studentMap = new HashMap<String, Object>();
		
		List<Dormcallover> dormcalloverList = new ArrayList<Dormcallover>();
		Set<String> key = allStudent.keySet();
		Iterator<String> iter = key.iterator();
		while (iter.hasNext()) {
			// 得到一个学生
			studentMap = (Map<String, Object>) allStudent.get(iter.next());
			// 学号
			String studentNumber = (String) studentMap.get("studentNumber");
			if (studentNumber == null || "".equals(studentNumber)) {
				return returnStatus.CannotAnalyzeData;
			}
			Student student;
			try {
				// 通过学号得到学生信息
				student = studentService.get("id", studentNumber);
			} catch (Exception e) {
				e.printStackTrace();
				System.err
						.println("DormcalloverController_saveDormcallover_studentService.get(\"id\", studentNumber);出错");
				return returnStatus.Fail;
			}
			// 宿舍考勤状况(未就寝)
			String status = (String)studentMap.get("status");
			if (status == null || "".equals(status)) {
				return returnStatus.CannotAnalyzeData;
			}
			Dormcallover dormcallover = new Dormcallover();
			dormcallover.setName(name);
			dormcallover.setStudent(student);
			dormcallover.setDate(date);
			dormcallover.setStatus(status);
			DormcalloverCount dCount = tryCatchDormcalloverCountService.getDormcalloverCountBysnumber(studentNumber);
			if(dCount==null){
				DormcalloverCount dCount2 = new DormcalloverCount();
				dCount2.setStudent(student);
				dCount2.setNum(1);
				if(!tryCatchDormcalloverCountService.save(dCount2)){
					return returnStatus.Fail;
				}
			}else{
				dCount.setNum(dCount.getNum()+1);
				if(!tryCatchDormcalloverCountService.update(dCount)){
					return returnStatus.Fail;
				}
			}
			dormcalloverList.add(dormcallover);
		}	
		if (!tryCatchDormcalloverService.saveDormcalloverByBatch(dormcalloverList)) {
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 服务端辅导员查询宿舍考勤
	 * @param requestJsonBody
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/WebgetDormcalloverByGrade.html")
	@ResponseBody
	public String WebgetDormcalloverByGrade(@RequestBody String requestJsonBody)
			throws Exception{
		
		//修改date为空时，查询宿舍考勤信息
		Map<String, Object> map = new HashMap<String, Object>();//"gradeId":8  类型
		Integer gradeId = null;
		String date = null;
		String string;
		try{
			map = jsonAnalyze.json2Map(requestJsonBody);
			gradeId = (Integer)map.get("gradeId");
			date = (String)map.get("date");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("服务端辅导员查询宿舍考勤出错!!!");
		}
		if(date.equals(null)||"".equals(date)){
			List<Dormcallover> dormcalloverList = tryCatchDormcalloverService
					.getDormcalloversByGrade(gradeId);
			if (dormcalloverList == null || "".equals(dormcalloverList)
					|| dormcalloverList.size() < 0) {
				return returnStatus.Fail;
			}
			//向dormcalloverList中加入学生姓名和电话
			List<DormcalloverUtil> dUtilList = new ArrayList<DormcalloverUtil>();
			for(int i=0;i<dormcalloverList.size();i++){
				
				DormcalloverUtil dormcalloverUtil  = new DormcalloverUtil();
				int id = dormcalloverList.get(i).getId();
				String studentName = dormcalloverList.get(i).getStudent().getStudentName();
				String phone = dormcalloverList.get(i).getStudent().getStudentPhone();
				String name = dormcalloverList.get(i).getName();
				String status = dormcalloverList.get(i).getStatus();
				Date d = dormcalloverList.get(i).getDate();
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				String dString = df.format(d);
				dormcalloverUtil.setId(id);
				dormcalloverUtil.setStudentName(studentName);
				dormcalloverUtil.setPhone(phone);
				dormcalloverUtil.setName(name);
				dormcalloverUtil.setStatus(status);
				dormcalloverUtil.setDate(dString);
				
				dUtilList.add(dormcalloverUtil);
			}
			
			if (dUtilList == null || "".equals(dUtilList)
					|| dUtilList.size() < 0) {
				return returnStatus.Fail;
			} else {
				string = jsonAnalyze.list2Json(dUtilList);
			}
			return string;
		}else{
		
			List<Dormcallover> dormcalloverList = tryCatchDormcalloverService
					.getDormcalloversByGradeAndDate(gradeId, date);
			if (dormcalloverList == null || "".equals(dormcalloverList)
					|| dormcalloverList.size() < 0) {
				return returnStatus.Fail;
			}
			//向dormcalloverList中加入学生姓名和电话
			List<DormcalloverUtil> dUtilList = new ArrayList<DormcalloverUtil>();
			for(int i=0;i<dormcalloverList.size();i++){
				
				DormcalloverUtil dormcalloverUtil  = new DormcalloverUtil();
				int id = dormcalloverList.get(i).getId();
				String studentName = dormcalloverList.get(i).getStudent().getStudentName();
				String phone = dormcalloverList.get(i).getStudent().getStudentPhone();
				String name = dormcalloverList.get(i).getName();
				String status = dormcalloverList.get(i).getStatus();
				Date d = dormcalloverList.get(i).getDate();
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				String dString = df.format(d);
				dormcalloverUtil.setId(id);
				dormcalloverUtil.setStudentName(studentName);
				dormcalloverUtil.setPhone(phone);
				dormcalloverUtil.setName(name);
				dormcalloverUtil.setStatus(status);
				dormcalloverUtil.setDate(dString);
				
				dUtilList.add(dormcalloverUtil);
			}
			
			if (dUtilList == null || "".equals(dUtilList)
					|| dUtilList.size() < 0) {
				return returnStatus.Fail;
			} else {
				string = jsonAnalyze.list2Json(dUtilList);
			}
			return string;
		}
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 客户端辅导员查询宿舍考勤
	 * @param requestJsonBody
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDormcalloverByGrade.html")
	@ResponseBody
	public String getDormcalloverByGrade(@RequestBody String requestJsonBody)
			throws Exception{
		
		//修改date为空时，查询宿舍考勤信息
		String gradeId = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "gradeID"); 
		String string;
		
			List<Dormcallover> dormcalloverList = tryCatchDormcalloverService
					.getDormcalloversByGrade(Integer.parseInt(gradeId));
			if (dormcalloverList == null || "".equals(dormcalloverList)
					|| dormcalloverList.size() < 0) {
				return returnStatus.Fail;
			}
			//向dormcalloverList中加入学生姓名和电话
			List<DormcalloverUtil> dUtilList = new ArrayList<DormcalloverUtil>();
			for(int i=0;i<dormcalloverList.size();i++){
				
				DormcalloverUtil dormcalloverUtil  = new DormcalloverUtil();
				String studentName = dormcalloverList.get(i).getStudent().getStudentName();
				String phone = dormcalloverList.get(i).getStudent().getStudentPhone();
				String name = dormcalloverList.get(i).getName();
				String status = dormcalloverList.get(i).getStatus();
				Date d = dormcalloverList.get(i).getDate();
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				String dString = df.format(d);
				dormcalloverUtil.setStudentName(studentName);
				dormcalloverUtil.setPhone(phone);
				dormcalloverUtil.setName(name);
				dormcalloverUtil.setStatus(status);
				dormcalloverUtil.setDate(dString);
				
				dUtilList.add(dormcalloverUtil);
			}
			
			if (dUtilList == null || "".equals(dUtilList)
					|| dUtilList.size() < 0) {
				return returnStatus.Fail;
			} else {
				
				String[] responseKey = {"dUtilList"};
				Object[] responseValue = {dUtilList};
				Map<String,Object> responseMap = dataProcess.getMapByStringArray(responseKey, responseValue);		
				string = jsonAnalyze.map2Json(responseMap);
			}
	
			return string;
		}
	/**
	 * @author lyf
	 * @time 2015-11-24
	 * @function 辅导员批量删除宿舍考勤信息
	 * @param requestJsonBody
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/dormcalloverDel.html")
	@ResponseBody
	public String delDormcalloverByBatch(@RequestBody String requestJsonBody)throws Exception
	{	
		//接收json格式的ids，并转为String格式
		@SuppressWarnings("unchecked")
		List<Integer> ids = (List<Integer>) analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "ids");
		
		if(dataProcess.dataIsNull(ids)||dataProcess.listHasNull(ids))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchDormcalloverService.delDormcalloverByBatch(ids))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
}
