/*
*@包名：com.b505.web        
*@文档名：SectionController.java 
*@功能：新闻栏目管理 
*@作者：李振强        
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Section;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchSectionService;

@Controller
public class SectionController 
{
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private TryCatchSectionService tryCatchSectionService;
	
	/*
	 * @方法名：sectionAdd(@RequestBody String requestJsonBody)
	 * @功能：添加新闻栏目
	 * @功能说明：添加新闻栏目
	 * @作者：李振强
	 * @创建时间：2014.5.18
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/sectionAdd.html")
	@ResponseBody
	public String sectionAdd(@RequestBody String requestJsonBody) throws Exception
	{
		@SuppressWarnings("unchecked")
		List<String> sectionNameList = (List<String>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "sectionName");
		if(dataProcess.dataIsNull(sectionNameList)||dataProcess.listHasNull(sectionNameList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<Section> sectionList = new ArrayList<Section>();
		final int sectionNameListSize = sectionNameList.size();
		for(int i =0; i < sectionNameListSize; i++)
		{
			Section section = new Section();
			section.setSectionName(sectionNameList.get(i));
			sectionList.add(section);
		}
		if(!tryCatchSectionService.saveSectionByBatch(sectionList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	/*
	 * @方法名：sectionDel(@RequestBody String requestJsonBody)
	 * @功能：删除新闻栏目
	 * @功能说明：删除新闻栏目
	 * @作者：李振强
	 * @创建时间：2014.5.18
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/sectionDel.html")
	@ResponseBody
	public String sectionDel(@RequestBody String requestJsonBody) throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> sectionList = (List<Map<String, Object>>) analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "sectionList");
		if(dataProcess.dataIsNull(sectionList)||dataProcess.listHasNull(sectionList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		final int sectionListSize = sectionList.size();
		for(int i = 0; i < sectionListSize; i++)
		{
			Map<String,Object> sectionMap = sectionList.get(i);
			String[] sectionKey = {"sectionId","sectionName"};

			Object[] sectionValue = dataProcess.getMapValueByKey(sectionMap, sectionKey);
			if(!tryCatchSectionService.deleteSectionBySectionName((Integer)sectionValue[0], (String)sectionValue[1]))

			{
				return returnStatus.Fail;
			}
		}
		return returnStatus.Success;
	}
	
	/*
	 * @方法名：sectionUpdate(@RequestBody String requestJsonBody)
	 * @功能：更新新闻栏目
	 * @功能说明：更新新闻栏目
	 * @作者：李振强
	 * @创建时间：2014.5.18
	 * @修改时间：2014.5.18
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/sectionUpdate.html")
	@ResponseBody
	public String sectionUpdate(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> sectionList = (List<Map<String, Object>>) analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "sectionList");
		if(dataProcess.dataIsNull(sectionList)||dataProcess.listHasNull(sectionList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchSectionService.updateSectionByBatch(sectionList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	
	
}

