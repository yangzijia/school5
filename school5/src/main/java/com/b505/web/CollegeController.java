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
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.Regex;
import com.b505.tools.ReturnObjectByAttribute;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchCollegeService;

@Controller
public class CollegeController
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
	private ReturnObjectByAttribute returnObjectByAttribute;
	@Autowired
	private Regex regex;
	
	/*
	 * @方法名：collegeAdd(@RequestBody String requestJsonBody)
	 * @功能：添加学院
	 * @功能说明：添加学院
	 * @作者：李振强
	 * @创建时间：2014.5.18
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/collegeAdd.html")
	@ResponseBody
	public String collegeAdd(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<String> collegeNameList = (List<String>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "collegeName");
		if(dataProcess.dataIsNull(collegeNameList)||dataProcess.listHasNull(collegeNameList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<College> collegeList = new ArrayList<College>();
		final int collegeListSize = collegeNameList.size();
		for(int i = 0; i < collegeListSize; i++)
		{
			College college = new College();
			if(!regex.nameRegex(collegeNameList.get(i)))
			{
				return returnStatus.NameCodedError;
			}
			college.setCollegeName(collegeNameList.get(i));
			collegeList.add(college);
		}
		if(!tryCatchCollegeService.saveCollegeByBatch(collegeList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	/*
	 * @方法名：collegeDel(@RequestBody String requestJsonBody)
	 * @功能：删除学院
	 * @功能说明：删除学院
	 * @作者：李振强
	 * @创建时间：2014.5.18
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/collegeDel.html")
	@ResponseBody
	public String collegeDel(@RequestBody String requestJsonBody)throws Exception
	{
		System.out.println("requestJsonBody"+requestJsonBody);
		@SuppressWarnings("unchecked")
		List<Integer> collegeIDList = (List<Integer>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "collegeList");
		if(dataProcess.dataIsNull(collegeIDList)||dataProcess.listHasNull(collegeIDList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchCollegeService.deleteCollegeByBatch(collegeIDList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	/*
	 * @方法名：collegeUpdate(@RequestBody String requestJsonBody)
	 * @功能：更新学院
	 * @功能说明：更新学院
	 * @作者：李振强
	 * @创建时间：2014.5.18
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/collegeUpdate.html")
	@ResponseBody
	public String collegeUpdate(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> collegeNameList = (List<Map<String, Object>>) analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "collegeList");
		if(dataProcess.dataIsNull(collegeNameList)||dataProcess.listHasNull(collegeNameList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchCollegeService.updateCollegeByBatch(collegeNameList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
}
