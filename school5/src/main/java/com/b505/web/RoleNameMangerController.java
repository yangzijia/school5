package com.b505.web;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.ChildrenMenu;
import com.b505.bean.ParentMenu;
import com.b505.bean.Resc;
import com.b505.bean.RoleName;
import com.b505.json.JsonAnalyze;
import com.b505.service.IRoleNameService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;

@Controller
public class RoleNameMangerController {
	@Autowired
	private IRoleNameService roleNameService;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@RequestMapping(value="/addRescToRole.html")
	@ResponseBody
	public String updateRole_Resc(@RequestBody String requestJsonBody)throws Exception{
		if(dataProcess.dataIsNull(requestJsonBody))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Integer roleNameId = Integer.parseInt((String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "roleNameId"));
		if(dataProcess.dataIsNull(roleNameId))
		{
			return returnStatus.CannotAnalyzeData;
		}
		@SuppressWarnings("unchecked")
		List<Integer> list = (List<Integer>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody,"rescIdList");
		if(dataProcess.dataIsNull(list)||dataProcess.listHasNull(list))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<Resc> rescList = new ArrayList<Resc>();
		for(int i=0;i<list.size();i++){
			Resc resc = new Resc();
			resc.setId(list.get(i));
			rescList.add(resc);
		}
		RoleName roleName  = roleNameService.get(roleNameId);
		roleName.setResc(rescList);
		try {
			roleNameService.update(roleName);
		} catch (Exception e) {
			// TODO: handle exception
			return returnStatus.Fail;
		}
			return returnStatus.Success;
	}
	@RequestMapping(value="/addChildrenMenuToRole.html")
	@ResponseBody
	public String updateRole_Childrenmenu(@RequestBody String requestJsonBody) throws Exception, Exception{
		if(dataProcess.dataIsNull(requestJsonBody))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Integer roleNameId = Integer.parseInt((String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "roleNameId"));
		if(dataProcess.dataIsNull(roleNameId))
		{
			return returnStatus.CannotAnalyzeData;
		}
		@SuppressWarnings("unchecked")
		List<Integer> list = (List<Integer>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody,"childrenMenuIdList");
		if(dataProcess.dataIsNull(list)||dataProcess.listHasNull(list))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<ChildrenMenu> childrenMeunList = new ArrayList<ChildrenMenu>();
		for(int i=0;i<list.size();i++){
			ChildrenMenu childrenMenu = new ChildrenMenu();
			childrenMenu.setId(list.get(i));
			childrenMeunList.add(childrenMenu);
			
		}
		RoleName roleName  = roleNameService.get(roleNameId);
		roleName.setChildrenMenus(childrenMeunList);
		try {
			roleNameService.update(roleName);
		} catch (Exception e) {
			// TODO: handle exception
			return returnStatus.Fail;
		}
			return returnStatus.Success;
	}
	@RequestMapping(value="/addParentMenuToRole.html")
	@ResponseBody
	public String updateRole_Parentmenu(@RequestBody String requestJsonBody) throws Exception, Exception{
		if(dataProcess.dataIsNull(requestJsonBody))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Integer roleNameId = Integer.parseInt((String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "roleNameId"));
		if(dataProcess.dataIsNull(roleNameId))
		{
			return returnStatus.CannotAnalyzeData;
		}
		@SuppressWarnings("unchecked")
		List<Integer> list = (List<Integer>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody,"parentMenuIdList");
		if(dataProcess.dataIsNull(list)||dataProcess.listHasNull(list))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<ParentMenu> parentMenuList = new ArrayList<ParentMenu>();
		for(int i=0;i<list.size();i++){
			ParentMenu parentMenu = new ParentMenu();
			parentMenu.setId(list.get(i));
			parentMenuList.add(parentMenu);
			
		}
		RoleName roleName  = roleNameService.get(roleNameId);
		roleName.setParentMenus(parentMenuList);
		try {
			roleNameService.update(roleName);
		} catch (Exception e) {
			// TODO: handle exception
			return returnStatus.Fail;
		}
			return returnStatus.Success;
	}
}
