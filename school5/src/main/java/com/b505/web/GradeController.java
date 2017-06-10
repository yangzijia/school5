package com.b505.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.College;
import com.b505.bean.Grade;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchCollegeService;
import com.b505.tools.TryCatchGradeService;

@Controller
public class GradeController
{
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchCollegeService tryCatchCollegeService;
	@Autowired
	private TryCatchGradeService tryCatchGradeService;
	
	
	/*
	 * @方法名：gradeAdd(@RequestBody String requestJsonBody)
	 * @功能：添加年级
	 * @功能说明：添加年级
	 * @作者：李振强
	 * @创建时间：2014.5.18
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/gradeAdd.html")
	@ResponseBody
	public String gradeAdd(@RequestBody String requestJsonBody) throws Exception
	{
		System.out.println("requestJsonBody :" + requestJsonBody);
		Object collegeID = analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "collegeid");
		if(dataProcess.dataIsNull(collegeID))
		{
			return returnStatus.CannotAnalyzeData;
		}
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> gradeNameList = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "gradeList");
		if(dataProcess.dataIsNull(gradeNameList)||dataProcess.listHasNull(gradeNameList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		College college = tryCatchCollegeService.getCollege("collegeid", Integer.parseInt((String)collegeID));
		List<Grade> gradeList = new ArrayList<Grade>();
		final int gradeNameListSize = gradeNameList.size();
		for(int i =0; i < gradeNameListSize; i++)
		{
			Grade grade = new Grade();
			String[] gradeKey = {"yearClass","profession","classId"};

			Object[] gradeValue = dataProcess.getMapValueByKey(gradeNameList.get(i), gradeKey);

			grade.setCollege(college);
			grade.setYearClass((String)gradeValue[0]);
			grade.setProfession((String)gradeValue[1]);
			grade.setClassId((String)gradeValue[2]);
			gradeList.add(grade);
		}
		if(!tryCatchGradeService.saveGradeByBatch(gradeList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	
	/*
	 * @方法名：gardeUpdate(@RequestBody String requestJsonBody)
	 * @功能：更新年级
	 * @功能说明：更新年级
	 * @作者：李振强
	 * @创建时间：2014.5.18
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/gradeUpdate.html")
	@ResponseBody
	public String gardeUpdate(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> gradeNameList = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "gradeList");
		if(dataProcess.dataIsNull(gradeNameList)||dataProcess.listHasNull(gradeNameList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchGradeService.updateGradeByBatch(gradeNameList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;	
	}
	
	
	/*
	 * @方法名：gradeDel(@RequestBody String requestJsonBody)
	 * @功能：删除年级
	 * @功能说明：删除年级
	 * @作者：李振强
	 * @创建时间：2014.5.18
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/gradeDel.html")
	@ResponseBody
	public String gradeDel(@RequestBody String requestJsonBody) throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> gradeNameList = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "gradeList");
		System.out.println("gradeNameList------>"+gradeNameList);
		if(dataProcess.dataIsNull(gradeNameList)||dataProcess.listHasNull(gradeNameList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchGradeService.deleteGradeByBatch(gradeNameList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
}

