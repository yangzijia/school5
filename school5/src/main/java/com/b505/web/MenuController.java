/*
*@包名：com.b505.web        
*@文档名：MenuController.java 
*@功能：从数据库获得菜单   
*@作者：李振强        
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.ChildrenMenu;
import com.b505.bean.ParentMenu;
import com.b505.bean.util.ChildrenMenuUtil;
import com.b505.bean.util.ParentMenuUtil;
import com.b505.json.JsonAnalyze;
import com.b505.security.MySecurityMetadataSource;
import com.b505.security.WebUserDetails;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.GetMenu;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchChildrenMenuService;
import com.b505.tools.TryCatchParentMenuService;
import com.google.gson.Gson;

@Controller
public class MenuController 
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
	private TryCatchParentMenuService tryCatchParentMenuService;
	@Autowired
	private TryCatchChildrenMenuService tryCatchChildrenMenuService;
	@Autowired
	private MySecurityMetadataSource mySecurityMetadataSource;
	@Autowired
	private GetMenu getMenu;

	
	/*
	 * @方法名：parentMenuGetMethod()
	 * @功能：获得父菜单
	 * @功能说明：从数据库中取得菜单，并发送给Web端。
	 * @作者：李振强
	 * @创建时间：2014.4.25
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */	
	@RequestMapping(value="/parentMenuGet.html")
	@ResponseBody
	public String parentMenuGetMethod() throws Exception
	{
		List<ParentMenu> parentMenuList = tryCatchParentMenuService.getAllParentMenu();
		if(dataProcess.dataIsNull(parentMenuList)||dataProcess.listHasNull(parentMenuList))
		{
			return returnStatus.Fail;
		}
		String string = jsonAnalyze.list2Json(parentMenuList);
		if(dataProcess.dataIsNull(string))
		{
			return returnStatus.Fail;
		}
		 return string;
	}
	
	/*
	 * @方法名：getMenu()
	 * @功能：获得菜单
	 * @功能说明：从数据库中取得菜单，并发送给Web端。
	 * @作者：李振强
	 * @创建时间：2014.4.25
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value = "/menuGet.html")
	@ResponseBody
	public String getMenu(HttpServletRequest request) throws Exception
	{	
		WebUserDetails webUserDetails = (WebUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String role1	= webUserDetails.getAuthorities().toString();
		
		List<Map<String, Object>> menuStr=getMenu.getMap(role1);
		
		if(menuStr.size()>0){
			Gson gson = new Gson();
		    String json = gson.toJson(menuStr);
		    System.out.println("json--------->"+json);
		    return json;
		  
		}else{
			return null;
		}
		
		//String menuStrJson=JSONArray.fromObject(menuStr).toString();
//		System.out.println("menuStr:"+menuStr);
//		String role=role1.substring(1, role1.length()-1);
//		System.out.println("role："+role);
//		
//
//		Map<String, Collection<ConfigAttribute>> resourceMap = mySecurityMetadataSource.getResourceMap1();
//		System.out.println("resourceMap："+resourceMap);
//		Collection<ConfigAttribute> menu = resourceMap.get(role + "Menu");	
//		System.out.println("menu："+menu);
//		Object[] menuObj = menu.toArray();					
//		String menuStr = menuObj[0].toString();
//		System.out.println(" menuStr :" + menuStr);
//		return menuStr;
		
		
//		List<Map<String,Object>> menuList = new ArrayList<Map<String,Object>>();
//		List<ParentMenu> parentMenuList = tryCatchParentMenuService.getAllParentMenu();
//		if(dataProcess.dataIsNull(parentMenuList)||dataProcess.listHasNull(parentMenuList))
//		{
//			return returnStatus.Fail;
//		}
//		final int parentMenuListSize = parentMenuList.size();
//		for(int i = 0; i < parentMenuListSize; i++)
//		{
//			List<ChildrenMenu> childrenMenuList = tryCatchChildrenMenuService.getChildrenMenuByparent(parentMenuList.get(i).getMenuName());
//			String[] menuKey = {"childrenMenu" , "parentMenuName"};
//			Object[] menuValue = {childrenMenuList , parentMenuList.get(i).getMenuName()};
//			Map<String,Object> parentMap = dataProcess.getMapByStringArray(menuKey, menuValue);			
//			menuList.add(parentMap);
//		}
//		String json = jsonAnalyze.list2Json(menuList);
//		if(dataProcess.dataIsNull(json))
//		{
//			return returnStatus.Fail;
//		}
//		return json;
	}
	
	
	/*
	 * @方法名：parentMenuAddMethod(@RequestBody String requestJsonBody)
	 * @功能：添加父菜单
	 * @功能说明：添加父菜单
	 * @作者：李振强
	 * @创建时间：2014.4.25
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/parentMenuAdd.html")
	@ResponseBody
	public String parentMenuAddMethod(@RequestBody String requestJsonBody) throws Exception
	{
		String requestKey = "parentMenuName";
		@SuppressWarnings("unchecked")
		List<String> responseValueList = (List<String>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody , requestKey);
		if(dataProcess.dataIsNull(responseValueList)||dataProcess.listHasNull(responseValueList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		ArrayList<ParentMenu>list2 = new ArrayList<ParentMenu>();
		boolean stauts=false;
		final int responseValueListSize = responseValueList.size();
		for(int i = 0; i < responseValueListSize; i++)
		{
			stauts = tryCatchParentMenuService.isHaveParentMenu(responseValueList.get(i));
			if(stauts)
			{
				break;
			}
			else
			{
				ParentMenu parenmenu = new ParentMenu();
				parenmenu.setMenuName(responseValueList.get(i));
				list2.add(parenmenu);
			}
		}
		if(stauts==true)
		{
			return returnStatus.Fail;
		}
		if(!tryCatchParentMenuService.saveParentMenuByBatch(list2))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	/*
	 * @方法名：parentMenuDelMethod(@RequestBody String requestJsonBody)
	 * @功能：删除父菜单
	 * @功能说明：删除父菜单
	 * @作者：李振强
	 * @创建时间：2014.4.25
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/parentMenuDel.html")
	@ResponseBody
	public String parentMenuDelMethod(@RequestBody String requestJsonBody) throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Integer> menuList = (List<Integer>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "parentMenuId");
		if(dataProcess.dataIsNull(menuList)||dataProcess.listHasNull(menuList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchParentMenuService.deleteParentMenuByBatch(menuList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	/*
	 * @方法名：parentMenuUpdateMethod(@RequestBody String requestJsonBody)
	 * @功能：更新父菜单
	 * @功能说明：更新父菜单
	 * @作者：李振强
	 * @创建时间：2014.4.25
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/parentMenuUpdate.html")
	@ResponseBody
	public String parentMenuUpdateMethod(@RequestBody String requestJsonBody) throws Exception
	{
		List<Map<String, Object>> menuList = (List<Map<String, Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "parentMenuList");
		if(dataProcess.dataIsNull(menuList)||dataProcess.listHasNull(menuList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchParentMenuService.updateParentMenuByBatch(menuList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	/*
	 * @方法名：getChildrenMenuMethod(@RequestBody String requestJsonBody)
	 * @功能：得到子菜单
	 * @功能说明：得到子菜单
	 * @作者：李振强
	 * @创建时间：2014.4.25
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */
	@RequestMapping(value="/getChildrenMenu.html")
	@ResponseBody
	public String getChildrenMenuMethod(@RequestBody String requestJsonBody) throws Exception
	{
		String[] menuKey = {"parentMenuName"};
		Object[] menuValue = (Object[])analyzeData.clientDataBeAnalyzedToServiceDataMap2String(requestJsonBody, menuKey);
		if(dataProcess.dataIsNull(menuValue)||dataProcess.arrayHasNull(menuValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<ChildrenMenu> childrenMenuList = tryCatchChildrenMenuService.getChildrenMenuByparent((String)menuValue[0]);
		if(dataProcess.dataIsNull(childrenMenuList)||dataProcess.listHasNull(childrenMenuList))
		{
			return returnStatus.Fail;
		}
		String childrenMenuString  =  jsonAnalyze.list2Json(childrenMenuList);
		if(dataProcess.dataIsNull(childrenMenuString))
		{
			return returnStatus.Fail;
		}
		return childrenMenuString;
	}
	
	/*
	 * @方法名：childrenMenuDel(@RequestBody String requestJsonBody) 
	 * @功能：删除子菜单
	 * @功能说明：删除子菜单
	 * @作者：李振强
	 * @创建时间：2014.4.25
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */	
	@RequestMapping(value="/childrenMenuDel.html")
	@ResponseBody
	public String childrenMenuDel(@RequestBody String requestJsonBody) throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Integer> menuList = (List<Integer>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "childrenMenuId");
		if(dataProcess.dataIsNull(menuList)||dataProcess.listHasNull(menuList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchChildrenMenuService.deleteChildrenMenuByBatch(menuList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	
	/*
	 * @方法名：childrenMenuUpdateMethod(@RequestBody String requestJsonBody)
	 * @功能：更新子菜单
	 * @功能说明：更新子菜单
	 * @作者：李振强
	 * @创建时间：2014.4.25
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/childrenMenuUpdate.html")
	@ResponseBody
	public String childrenMenuUpdateMethod(@RequestBody String requestJsonBody) throws Exception
	{
		List<Map<String, Object>> menuList = (List<Map<String, Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "childrenMenuList");
		if(dataProcess.dataIsNull(menuList)||dataProcess.listHasNull(menuList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchChildrenMenuService.updateChildrenMenuByBatch(menuList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	
	/*
	 * @方法名：childrenMenuAddMethod(@RequestBody String requestJsonBody)
	 * @功能：添加子菜单
	 * @功能说明：添加子菜单
	 * @作者：李振强
	 * @创建时间：2014.4.25
	 * @修改时间：2014.5.17
	 * @修改说明：代码重构
	 */	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/childrenMenuAdd.html")
	@ResponseBody
	public String childrenMenuAddMethod(@RequestBody String requestJsonBody) throws Exception
	{
		Integer parentMenuId = Integer.parseInt((String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "parentMenuId"));
		if(dataProcess.dataIsNull(parentMenuId))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List<Map<String, Object>> list = (List<Map<String, Object>>)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "childrenMenuList");
		System.out.println("list----->"+list);
		if(dataProcess.dataIsNull(list)||dataProcess.listHasNull(list))
		{
			return returnStatus.CannotAnalyzeData;
		}
		List <ChildrenMenu> list2 = new ArrayList<ChildrenMenu>();
		boolean stauts=false;
		final int listSize = list.size();
		for(int i = 0; i < listSize; i++)
		{
			stauts =tryCatchChildrenMenuService.isHaveChrildrenMenu((String)list.get(i).get("url"));
			if(stauts)
			{
				break;
			}
			else
			{
				ParentMenu parenmenu = new ParentMenu();
				parenmenu.setId(parentMenuId);
				ChildrenMenu childrenMenu = new ChildrenMenu();
				childrenMenu.setChildrenmenuName((String)list.get(i).get("childrenmenuName"));
				childrenMenu.setUrl((String)list.get(i).get("url"));
				childrenMenu.setParentMenu(parenmenu);
				list2.add(childrenMenu);
				System.out.println("list2----->"+list2);
			}
		}
		if(stauts==true)
		{
			return returnStatus.Fail;
		}
		System.out.println("执行了吗？");
		if(!tryCatchChildrenMenuService.saveChildrenMenuByBatch(list2))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	@RequestMapping(value="/getChildrenMenuUtil.html")
	@ResponseBody
	public String getChildrenMenuUtils() throws IOException{
		List<ChildrenMenuUtil> childrenMenuUtils = tryCatchChildrenMenuService.getChildrenMenuUtils();
		if(dataProcess.dataIsNull(childrenMenuUtils)||dataProcess.listHasNull(childrenMenuUtils))
		{
			return returnStatus.Fail;
		}
		String string = jsonAnalyze.list2Json(childrenMenuUtils);
		if(dataProcess.dataIsNull(string))
		{
			return returnStatus.Fail;
		}
		 return string;
	}
	@RequestMapping(value="/getParentMenuUtil.html")
	@ResponseBody
	public String getParentMenuUtils() throws IOException{
		List<ParentMenuUtil> parentMenuUtils = tryCatchParentMenuService.getParentMenuUtils();
		if(dataProcess.dataIsNull(parentMenuUtils)||dataProcess.listHasNull(parentMenuUtils))
		{
			return returnStatus.Fail;
		}
		String string = jsonAnalyze.list2Json(parentMenuUtils);
		if(dataProcess.dataIsNull(string))
		{
			return returnStatus.Fail;
		}
		 return string;
	}
}



