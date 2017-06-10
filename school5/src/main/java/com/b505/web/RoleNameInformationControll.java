package com.b505.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.util.RoleNameUtil;
import com.b505.json.JsonAnalyze;
import com.b505.service.IRoleNameService;
import com.b505.tools.StatusMap;

@Controller
public class RoleNameInformationControll {
	@Autowired
	private IRoleNameService roleNameService;
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@RequestMapping(value="/roleNameGet.html")
	@ResponseBody
	public String getRoleNameGetMehod() throws IOException{
		String Fail = statusMap.status("Fail");
		List<RoleNameUtil> list = roleNameService.getRoleNameUtils();
		String str = null;
		if(list.size()>0){
			try {
				str =jsonAnalyze.list2Json(list);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Fail;
			} 
			System.out.println(str);
			return str;
		}else {
			return Fail;
		}
		
	}
}
