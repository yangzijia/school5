package com.b505.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b505.bean.LoginUser;
import com.b505.bean.RoleName;
import com.b505.json.JsonAnalyze;
import com.b505.service.IRoleNameService;
import com.b505.service.IUserRoleService;

@Service
public class TryCatchUserRoleService {
	
	@Autowired
	private IUserRoleService userRoleService;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private IRoleNameService roleNameService;
	
	/*public String getRole(String requestJsonBody){
	
	List<Map<String, String>>list=new ArrayList<>();
      try {
    	  LoginUser user=userRoleService.get("nickName", requestJsonBody);
    	  List<RoleName> roles=user.getRoles();
			Map<String, String>map=new HashMap<String, String>();
    	    StringBuffer roBuffer=new StringBuffer();
			for (int i = 0; i < roles.size(); i++) {
				
				if (i!=roles.size()-1) {
					roBuffer.append(roles.get(i).getRemark());
					roBuffer.append(",");
				}else {
					roBuffer.append(roles.get(i).getRemark());
				}
				
				map.put("nickName", requestJsonBody);
				map.put("roleName", roBuffer.toString());
			}
			
			list.add(map);
			
			String json = jsonAnalyze.Listmap2Json(list);
			System.out.println("json:"+json);
			return json;

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     return null;
	}*/
	
	public String getAllRole(String requestJsonBody){
		
		List<Map<String, String>>list=new ArrayList<>();
      try {
    	  LoginUser user=userRoleService.get("nickName", requestJsonBody);
    	  List<RoleName> roles=user.getRoles();
    	  

			List<RoleName> roleList = roleNameService.getAll();
			for (int i = 0; i < roleList.size(); i++) {
				Map<String, String>map=new HashMap<String, String>();
				map.put("roleId", roleList.get(i).getId().toString());
				map.put("roleName", roleList.get(i).getRoleName());
				map.put("roleDiscribe", roleList.get(i).getRemark());
							
					for (int j = 0; j < roles.size(); j++) {
						System.out.println("roleList.get(i).getRoleName():"+roleList.get(i).getId()+"  roles.get(j).getRoleName(): "+roles.get(j).getId());
						if (roleList.get(i).getId().equals(roles.get(j).getId())) {
							
							map.put("isHave", "yes");
							
						}
					}
					
				list.add(map);
				
			}
					
			String json = jsonAnalyze.Listmap2Json(list);
			System.out.println("json:"+json);
			return json;

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     return null;
	}

}
