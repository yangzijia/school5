/*
 *@包名：com.b505.web        
 *@文档名：SaveCallOverInformationController.java 
 *@功能：保存考勤信息 
 *@作者：李振强        
 *@创建时间：2014.4.20   
 *@版权：河北北方学院信息技术研究所 
 */
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

import com.b505.bean.CallOver;
import com.b505.bean.CallOverCount;
import com.b505.bean.Student;
import com.b505.json.JsonAnalyze;
import com.b505.service.ICallOverService;
import com.b505.service.IStudentService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchCallOverCountService;
import com.b505.tools.TryCatchCallOverService;
import com.b505.tools.TryCatchTeacherService;

@Controller
public class SaveCallOverInformationController {
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private IStudentService studentService;
	@Autowired
	private ICallOverService callOverService;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchCallOverService tryCatchCallOverService;
	@Autowired
	private TryCatchCallOverCountService tryCatchCallOverCountService;
	@Autowired
	private TryCatchTeacherService tryCatchTeacherService;

	/*
	 * @方法名：getSaveOutcome(@RequestBody String requestJsonBody)
	 * @功能：保存点名结果 
	 * @功能说明：保存点名结果
	 * @作者：李振强
	 * @创建时间：2014.4.20
	 * @修改时间：2014.4.26
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveOutcome.html")
	@ResponseBody
	public String saveOutcome(@RequestBody String requestJsonBody)
			throws Exception {

		Map<String, Object> OutcomeMap = new HashMap<String, Object>();
		// 解析客户端数据
		OutcomeMap = jsonAnalyze.json2Map(requestJsonBody);
		System.out.println("OutcomeMap" + OutcomeMap);
		// 任课教师姓名
		String teacherName = tryCatchTeacherService
				.getTeachernameByNickName((String) OutcomeMap
						.get("teacherName"));

		// 课程名
		String course = (String) OutcomeMap.get("course");
		// 点名日期
		String date1 = (String) OutcomeMap.get("date");
		//学期需要根据日期进行计算,此处先默认为本学期
		String term = "第一学期";
		if (teacherName == null || "".equals(teacherName) || course == null
				|| "".equals(course)) {
			return returnStatus.CannotAnalyzeData;
		}
		// 点名时正常上课的学生列表
		Map<String, Object> allStudent = (Map<String, Object>) OutcomeMap
				.get("allStudent");
		if (allStudent == null || "".equals(allStudent)) {
			return returnStatus.CannotAnalyzeData;
		}
		Map<String, Object> studentMap = new HashMap<String, Object>();
		
		/*Date newsDate = new Date();
		// 点名时的日期,,,,,,需从客户端获取。然后格式化
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(newsDate);*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date =  sdf.parse(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<CallOver> callOverList = new ArrayList<CallOver>();
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
						.println("SaveCallOverInformationController_saveOutcome_studentService.get(\"id\", studentNumber);出错");
				return returnStatus.Fail;
			}
			// 上课状况（签到、旷课、请假）
			String status = (String) studentMap.get("status");
			// 节数
			String calssTime = (String) studentMap.get("classTime");
			if (status == null || "".equals(status) || calssTime == null
					|| "".equals(calssTime)) {
				return returnStatus.CannotAnalyzeData;
			}
			CallOver callOver = new CallOver();
			callOver.setStatus(status);
			callOver.setClassTime(calssTime);
			callOver.setStudent(student);
			callOver.setTeacherName(teacherName);
			callOver.setDate(date);
			callOver.setTerm(term);
			callOver.setClassName(course);
			CallOverCount callOverCount = tryCatchCallOverCountService
					.getCallOverCountBysnumber(studentNumber);
			if (status.equals("旷课")) {
				if (callOverCount == null) {
					CallOverCount callOverCount2 = new CallOverCount();
					callOverCount2.setStudent(student);
					callOverCount2.setTruant(1);
					callOverCount2.setTerm(term);
					if (!tryCatchCallOverCountService.save(callOverCount2)) {
						return returnStatus.Fail;
					}
				} else {
					callOverCount.setTruant(callOverCount.getTruant() + 1);
					if (!tryCatchCallOverCountService.updata(callOverCount)) {
						return returnStatus.Fail;
					}
				}
			} else if (status.equals("请假")) {
				if (callOverCount == null) {
					CallOverCount callOverCount2 = new CallOverCount();
					callOverCount2.setStudent(student);
					callOverCount2.setSickLeave(1);
					callOverCount2.setTerm(term);
					if (!tryCatchCallOverCountService.save(callOverCount2)) {
						return returnStatus.Fail;
					}
				} else {
					callOverCount.setTruant(callOverCount.getSickLeave() + 1);
					if (!tryCatchCallOverCountService.updata(callOverCount)) {
						return returnStatus.Fail;
					}
				}

			} else if (status.equals("迟到")) {
				if (callOverCount == null) {
					CallOverCount callOverCount2 = new CallOverCount();
					callOverCount2.setStudent(student);
					callOverCount2.setLateNumber(1);
					callOverCount2.setTerm(term);
					if (!tryCatchCallOverCountService.save(callOverCount2)) {
						return returnStatus.Fail;
					}
				} else {
					callOverCount.setTruant(callOverCount.getLateNumber() + 1);
					if (!tryCatchCallOverCountService.updata(callOverCount)) {
						return returnStatus.Fail;
					}
				}

			}

			callOverList.add(callOver);
		}
		if (!tryCatchCallOverService.saveCallOverByBatch(callOverList)) {
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}

	/*
	 * @方法名：updateOutcome(@RequestBody String requestJsonBody)
	 * 
	 * @功能：根据学号、节数、日期返回 当天该学生的考勤情况
	 * 
	 * @功能说明：根据学号、节数、日期返回当天该学生的考勤情况
	 * 
	 * @作者：李振强
	 * 
	 * @创建时间：2014.5.30
	 * 
	 * @修改时间：2014.5.30
	 */
	@RequestMapping(value = "/updateOutcome.html")
	@ResponseBody
	public String updateOutcome(@RequestBody String requestJsonBody)
			throws Exception {
		String[] updataKey = { "classTime", "studentNumber" };
		Object[] updataValue = (Object[]) analyzeData
				.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody,
						updataKey);
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		CallOver callover = tryCatchCallOverService.getStudentCallOver(date,
				(String) updataValue[0], (String) updataValue[1]);
		if (dataProcess.dataIsNull(callover)) {
			return returnStatus.Fail;
		}
		Student student = callover.getStudent();
		String[] studentMessageKey = { "studentName", "studentNumber",
				"studyStatus" };
		Object[] studentMessageValue = { student.getStudentName(),
				student.getId(), callover.getStatus() };
		Map<String, Object> studentMap = dataProcess.getMapByStringArray(
				studentMessageKey, studentMessageValue);
		if (dataProcess.dataIsNull(studentMap)) {
			return returnStatus.Fail;
		}
		return jsonAnalyze.map2Json(studentMap);
	}

	/*
	 * @方法名：updateOutcome(@RequestBody String requestJsonBody)
	 * 
	 * @功能：更新学生的考勤情况
	 * 
	 * @功能说明：更新学生的考勤情况
	 * 
	 * @作者：李振强
	 * 
	 * @创建时间：2014.06.08
	 * 
	 * @修改时间：2014.06.08
	 */
	@RequestMapping(value = "/updateStudentOutcome.html")
	@ResponseBody
	public String updateStudentOutcome(@RequestBody String requestJsonBody)
			throws Exception {
		String[] updateKey = { "classTime", "studentNumber", "studyStatus",
				"oldStudyStatus" };
		Object[] updateValue = (Object[]) analyzeData
				.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody,
						updateKey);
		if (dataProcess.dataIsNull(updateValue)
				|| dataProcess.arrayHasNull(updateValue)) {
			return returnStatus.CannotAnalyzeData;
		}
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String classTime = (String) updateValue[0];
		String studentNumber = (String) updateValue[1];
		String studyStatus = (String) updateValue[2];
		String oldStudyStatus = (String) updateValue[3];
		CallOverCount callOverCount = tryCatchCallOverCountService
				.getCallOverCountBysnumber(studentNumber);
		if (dataProcess.dataIsNull(callOverCount)) {
			return returnStatus.Fail;
		}
		boolean b = false;
		boolean c = false;
		if (oldStudyStatus.equals("请假")) {

			if (studyStatus.equals("正常")) {
				b = tryCatchCallOverService.deleteCallOver(date, classTime,
						studentNumber);
				if (b) {
					callOverCount
							.setSickLeave(callOverCount.getSickLeave() - 1);
					c = tryCatchCallOverCountService.updata(callOverCount);
				} else {
					return returnStatus.Fail;
				}

			} else if (studyStatus.equals("迟到")) {

				b = tryCatchCallOverService.updateCallOver(classTime,
						studentNumber, studyStatus, date);
				if (b) {
					callOverCount
							.setSickLeave(callOverCount.getSickLeave() - 1);
					callOverCount
							.setLateNumber(callOverCount.getLateNumber() + 1);
					c = tryCatchCallOverCountService.updata(callOverCount);
				} else {
					return returnStatus.Fail;
				}

			} else if (studentNumber.equals("旷课")) {
				b = tryCatchCallOverService.updateCallOver(classTime,
						studentNumber, studyStatus, date);
				if (b) {
					callOverCount
							.setSickLeave(callOverCount.getSickLeave() - 1);
					callOverCount.setTruant(callOverCount.getTruant() + 1);
					c = tryCatchCallOverCountService.updata(callOverCount);
				} else {
					return returnStatus.Fail;
				}

			} else {
				return returnStatus.Fail;
			}

		} else if (oldStudyStatus.equals("迟到")) {

			if (studyStatus.equals("正常")) {
				b = tryCatchCallOverService.deleteCallOver(date, classTime,
						studentNumber);
				if (b) {
					callOverCount
							.setLateNumber(callOverCount.getLateNumber() - 1);
					c = tryCatchCallOverCountService.updata(callOverCount);
				} else {
					return returnStatus.Fail;
				}

			} else if (studyStatus.equals("请假")) {

				b = tryCatchCallOverService.updateCallOver(classTime,
						studentNumber, studyStatus, date);
				if (b) {
					callOverCount
							.setLateNumber(callOverCount.getLateNumber() - 1);
					callOverCount
							.setSickLeave(callOverCount.getSickLeave() + 1);
					c = tryCatchCallOverCountService.updata(callOverCount);
				} else {
					return returnStatus.Fail;
				}

			} else if (studyStatus.equals("旷课")) {

				b = tryCatchCallOverService.updateCallOver(classTime,
						studentNumber, studyStatus, date);
				if (b) {
					callOverCount
							.setLateNumber(callOverCount.getLateNumber() - 1);
					callOverCount.setTruant(callOverCount.getTruant() + 1);
					c = tryCatchCallOverCountService.updata(callOverCount);
				} else {
					return returnStatus.Fail;
				}

			} else {
				return returnStatus.Fail;
			}
		} else if (oldStudyStatus.equals("旷课")) {

			if (studyStatus.equals("正常")) {
				b = tryCatchCallOverService.deleteCallOver(date, classTime,
						studentNumber);
				if (b) {
					callOverCount.setTruant(callOverCount.getTruant() - 1);
					c = tryCatchCallOverCountService.updata(callOverCount);
				} else {
					return returnStatus.Fail;
				}

			} else if (studyStatus.equals("请假")) {

				b = tryCatchCallOverService.updateCallOver(classTime,
						studentNumber, studyStatus, date);
				if (b) {
					callOverCount.setTruant(callOverCount.getTruant() - 1);
					callOverCount
							.setSickLeave(callOverCount.getSickLeave() + 1);
					c = tryCatchCallOverCountService.updata(callOverCount);
				} else {
					return returnStatus.Fail;
				}

			} else if (studyStatus.equals("迟到")) {

				b = tryCatchCallOverService.updateCallOver(classTime,
						studentNumber, studyStatus, date);
				if (b) {
					callOverCount.setTruant(callOverCount.getTruant() - 1);
					callOverCount
							.setLateNumber(callOverCount.getLateNumber() + 1);
					c = tryCatchCallOverCountService.updata(callOverCount);
				} else {
					return returnStatus.Fail;
				}

			} else {
				return returnStatus.Fail;
			}

		}
		if (b && c) {
			return returnStatus.Success;
		} else {
			return returnStatus.Fail;
		}

	}
}
