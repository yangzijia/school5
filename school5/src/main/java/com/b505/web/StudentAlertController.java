package com.b505.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Roll_Alert;

import com.b505.json.JsonAnalyze;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.SessionGet;
import com.b505.tools.TryCatchStudentService;


@Controller
public class StudentAlertController
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
    @Autowired
    private SessionGet sessionGet;
    @Autowired
    private DataProcess dataProcess;
    @Autowired
	private ReturnStatus returnStatus;
    @Autowired
	private TryCatchStudentService tryCatchStudentService;
    
    @RequestMapping(value="/webstudentAlert.html")
	public String getstudentAlert(){
		return "studentAlert";
	}
    /*
     * @作者：JSY
     * @功能：查询学生预警成绩
     * @功能说明：查询学生预警成绩
     * @创建时间：2016.5.20
     */
    
	
	@RequestMapping(value="getStudentAlert.html")
	@ResponseBody
	public String getStudentAlertInformation(HttpServletRequest request) throws Exception{
		//拿到用户信息
		String nickName=sessionGet.getUserInfo().getUsername();
		
		System.out.println("nickName---->"+nickName);

		if (dataProcess.dataIsNull(nickName))
		{
			return returnStatus.CannotAnalyzeData;
		}else {
			List<Roll_Alert> List=tryCatchStudentService.getAlertByNickname(nickName);
			System.out.println("List---->"+List);
			
			//List<Map<String, String>>，List里面放的是map数组
			List<Map<String, String>>list2=new ArrayList<>();
			
			if (dataProcess.dataIsNull(List)||dataProcess.listHasNull(List))
			{
				 return returnStatus.CannotAnalyzeData;
			}
//			Map<String, Object>alertMap=new HashMap<String, Object>();
			final int Listsize=List.size();
			for (int i = 0; i < Listsize; i++)
			{
				//封装数据：将数据封装到list数组中，然后转化成json数据返回到前台
				Roll_Alert alert=List.get(i);
				
				Map<String, String> map=new HashMap<>();
				
				map.put("snumber", alert.getStudent().getId());
				map.put("course", alert.getCourse());
				map.put("score", alert.getScore());
				map.put("coursetype", alert.getCoursetype());
				map.put("opinion", alert.getOpinion());
				list2.add(map);
//				String[] studentKey={"snumber","course","score","coursetype","opinion"};
//			    String[] studentValue={List.get(i).getSnumber(),List.get(i).getCourse(),List.get(i).getScore(),List.get(i).getCoursetype(),List.get(i).getOpinion()};
//			    Map<String, Object>map=dataProcess.getMapByStringArray(studentKey, studentValue);
//			    alertMap.put(""+i, map);
//			    System.out.println("alertMap---->"+alertMap);
				
			}
			
			return jsonAnalyze.Listmap2Json(list2);
		}
		
	}
}
