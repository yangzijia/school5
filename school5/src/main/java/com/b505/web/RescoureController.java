package com.b505.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Resc;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchRescService;

@Controller
public class RescoureController 
{
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private TryCatchRescService tryCatchRescService;
	
	
	/*
	 * @方法名：addResc(@RequestBody String requestJsonBody)
	 * @功能：添加资源
	 * @功能说明：添加资源
	 * @作者：李振强
	 * @创建时间：2014.06.08
	 * @修改时间：2014.06.08
	 */
	@RequestMapping(value="/addResc.html")
	@ResponseBody
	public String addResc(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rescListMap = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(rescListMap)||dataProcess.listHasNull(rescListMap))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<Resc> rescList = new ArrayList<Resc>();
		final int rescListSize = rescListMap.size();
		for(int i = 0; i < rescListSize; i++)
		{
			String[] rescKey = {"name", "remark"};
			String[] rescValue = (String[]) dataProcess.getMapValueByKey(rescListMap.get(i), rescKey);
			if(dataProcess.dataIsNull(rescValue)||dataProcess.arrayHasNull(rescValue))
			{
				return returnStatus.CannotAnalyzeData;
			}
			Resc resc = new Resc();
			resc.setName(rescValue[0]);
			resc.setRemark(rescValue[1]);
			rescList.add(resc);
		}
		if(!tryCatchRescService.saveRescByBatch(rescList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	
	/*
	 * @方法名：delResc(@RequestBody String requestJsonBody)
	 * @功能：删除资源
	 * @功能说明：删除资源
	 * @作者：李振强
	 * @创建时间：2014.06.08
	 * @修改时间：2014.06.08
	 */
	@RequestMapping(value="/delResc.html")
	@ResponseBody
	public String delResc(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Integer> rescList = (List<Integer>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(rescList)||dataProcess.listHasNull(rescList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchRescService.deleteRescByBatch(rescList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	/*
	 * @方法名：delResc(@RequestBody String requestJsonBody)
	 * @功能：更新资源
	 * @功能说明：更新资源
	 * @作者：李振强
	 * @创建时间：2014.06.08
	 * @修改时间：2014.06.08
	 */
	@RequestMapping(value="/updateResc.html")
	@ResponseBody
	public String updateResc(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rescListMap = (List<Map<String,Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList(requestJsonBody);
		if(dataProcess.dataIsNull(rescListMap)||dataProcess.listHasNull(rescListMap))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchRescService.updateRescByBatch(rescListMap))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
}

