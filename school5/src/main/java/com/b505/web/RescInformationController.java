package com.b505.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.b505.bean.Resc;
import com.b505.json.JsonAnalyze;
import com.b505.service.IRescService;
import com.b505.tools.StatusMap;

@Controller
public class RescInformationController {
	@Autowired
	private IRescService rescService;
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@RequestMapping(value="/rescGet.html")
	@ResponseBody
	public String getRescGetMethod() throws IOException{
		String Fail = statusMap.status("Fail");
		List<Resc> list = new ArrayList<Resc>();
		list = rescService.getAll();
		String json=null;
		try {
			json = jsonAnalyze.list2Json(list);
		} catch (Exception e) {
			// TODO: handle exception
			return Fail;
		}
		return json;
	}
}
