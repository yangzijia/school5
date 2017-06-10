package com.b505.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b505.bean.ChildrenMenu;
import com.b505.bean.ParentMenu;
import com.b505.bean.RoleName;
import com.b505.json.JsonAnalyze;
import com.b505.service.IChildrenMenuService;
import com.b505.service.IRescService;
import com.b505.service.IRoleNameService;

@Service
public class GetMenu {
	@Autowired
	private IRescService rescService;
	@Autowired
	private IRoleNameService roleNameService;
	@Autowired
	private IChildrenMenuService childrenMenuSerivce;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	

	public List<Map<String, Object>> getMap(String role){
		String role1=role.substring(1, role.length()-1);
		String []role2=role1.split(",");
		List<RoleName> roleList = roleNameService.getAll();
		
		//根据用户的所有角色拿到的parentmenU；
		Map<String, Object> parentMenus=new HashMap<String, Object>();
		for (int i = 0; i < role2.length; i++) {
			String roleName = role2[i];
			for (RoleName role3 : roleList)
			{
				if (roleName.equals(role3.getRoleName())){
					List<ParentMenu> parentMenuList = role3.getParentMenus();
					final int parentMenuListSize = parentMenuList.size();
					for(int j = 0; j < parentMenuListSize; j++){
						String parentMenuName=parentMenuList.get(j).getMenuName();
						
						String parentMenuId=parentMenuList.get(j).getId().toString();
						parentMenus.put(parentMenuId, parentMenuName);
					}
				} 
			}
			
		}
		
		System.out.println("parentMenus:"+parentMenus);
		@SuppressWarnings("rawtypes")
		Set keySet = parentMenus.keySet();
		
		//根据用户的角色角色，拿到子菜单。
		 
		List<Map<String,Object>> parentMenuListMap = new ArrayList<Map<String,Object>>();
		
		for (int i = 0; i < role2.length; i++) {
			String roleName = role2[i];
			System.out.println("roleName------>"+roleName);
			 for (Iterator<?> iter = keySet.iterator(); iter.hasNext();){
				 
				   String key = (String) iter.next();
				   System.out.println("key=="+key);
				   String value = (String) parentMenus.get(key);
				   System.out.println("value=="+value);
				 
				   List<ChildrenMenu> childrenMenuList = childrenMenuSerivce.getChildrenMenusByRoleAndParent(roleName, value);
				   System.out.println("childrenMenuList------>"+childrenMenuList);
				   if(childrenMenuList == null){
						
						continue;
					}
				   
				   List<Map<String,Object>> childrenMenuListMap = new ArrayList<Map<String,Object>>();
				   final int childrenMenuListSize = childrenMenuList.size();
					for(int j = 0; j < childrenMenuListSize; j++)
					{
						ChildrenMenu childrenMenu = childrenMenuList.get(j);
						Map<String,Object> childrenMenuMap = new HashMap<String,Object>();
						childrenMenuMap.put("id", childrenMenu.getId());
						childrenMenuMap.put("childrenmenuName", childrenMenu.getChildrenmenuName());
						childrenMenuMap.put("url", childrenMenu.getUrl());
						childrenMenuListMap.add(childrenMenuMap);
					}
					
					Map<String,Object> parentMenuMap = new HashMap<String,Object>();
					parentMenuMap.put("parentMenuName", value);
					parentMenuMap.put("childrenMenu", childrenMenuListMap);
					parentMenuListMap.add(parentMenuMap);
				   
				   }
			 
		}
		

		System.out.println("parentMenuListMap:"+parentMenuListMap);
		return parentMenuListMap;
		
	}
	
	
}
