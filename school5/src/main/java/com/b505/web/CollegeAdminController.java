package com.b505.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.b505.bean.Roll_Alert;
import com.b505.bean.util.LongLatUtil;
import com.b505.bean.util.LongLatUtil1;
import com.b505.bean.util.Roll_AlertUtil;
import com.b505.json.JsonAnalyze;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.SessionGet;
import com.b505.tools.TryCatchCollegeAdminService;


@Controller
public class CollegeAdminController
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
	private TryCatchCollegeAdminService tryCatchCollegeAdminService;
    
    
    
    /*
	 * 学院教学科查询预警信息
	 */	
	@RequestMapping(value = "/getCollegeAdminShowEy.html")
	public String getCollegeAdminShowEmergency() {
		return "collegeAdminShowEy";
	}
	/*
	 * 学院教学科查询预警成绩
	 */
	@RequestMapping(value="/webCollegeAdmingetAlert.html")
	public String getCollegeAdminAlert(){
		return "CollegeAdmingetStudentAlert";
	}
    /**
	 * @author lyf
	 * @time 2015-11-22
	 * @function web学院教学科查询预警消息
	 * @param requestJsonBody
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/CollegeAdmingetEy.html")
	@ResponseBody
	public String CollegeAdmingetEy()throws Exception {

		String string;
		List<LongLatUtil> longLatUtil = tryCatchCollegeAdminService.getLongLatUtilByCollegeAdmin();
		if (longLatUtil == null || "".equals(longLatUtil)
				|| longLatUtil.size() < 0) {
			return returnStatus.Fail;
		}
		List<LongLatUtil1> longLatUtil1 = new ArrayList<LongLatUtil1>();
		for(int i=0;i<longLatUtil.size();i++){
			LongLatUtil1 latUtil1 = new LongLatUtil1();
			int mapId = (int)longLatUtil.get(i).getMapId();
			String longlattype = longLatUtil.get(i).getLonglattype();
			Date longLatdate = longLatUtil.get(i).getLongLatDate();
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm"); //格式化当前日期
			String longLatDate = dateFm.format(longLatdate);
			String studentNumber = longLatUtil.get(i).getStudentNumber();
			String studentName = longLatUtil.get(i).getStudentName();
			String word = longLatUtil.get(i).getWord();
			String geography = longLatUtil.get(i).getGeography();
			
			latUtil1.setMapId(mapId);
			latUtil1.setLonglattype(longlattype);
			latUtil1.setLongLatDate(longLatDate);
			latUtil1.setStudentNumber(studentNumber);
			latUtil1.setStudentName(studentName);
			latUtil1.setWord(word);
			latUtil1.setGeography(geography);
			
			longLatUtil1.add(latUtil1);
		}	
		
		if(dataProcess.dataIsNull(longLatUtil1)||dataProcess.listHasNull(longLatUtil1))
		{
			return returnStatus.Fail;
		}else{
			string = jsonAnalyze.list2Json(longLatUtil1);
		}		
		return string;
	}
	
	/**
	 * @author 贾少游
	 * @time 2016.5.22
	 * @function web学院教学科查询预警成绩
	 * @param requestJsonBody
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value="CollegeAdmingetAlert.html")
	@ResponseBody
	public String CollegeAdmingetStudentAlert(HttpServletRequest request) throws Exception{
		
		String string;
		List<Roll_Alert>list=new ArrayList<>();
		list=tryCatchCollegeAdminService.getCollegeAdminAlert();
		if (list==null||"".equals(list)||list.size()<0)
		{
			return returnStatus.Fail;
		}
		
//	    Map<String, Object> map1=new HashMap<String, Object>();
//		List<Roll_Alert>list2=new ArrayList<>();
		List<Roll_AlertUtil>list2=new ArrayList<>();
		final int listSize=list.size();
		for (int i = 0; i <listSize; i++)
		{
			Roll_Alert alert=list.get(i);
		   //重新定义一个新的bean，然后根据字段返回给前台	
			Roll_AlertUtil roll_AlertUtil=new Roll_AlertUtil();
			roll_AlertUtil.setId(alert.getStudent().getId());
			roll_AlertUtil.setCourse(alert.getCourse());
			roll_AlertUtil.setCoursetype(alert.getCoursetype());
			roll_AlertUtil.setName(alert.getName());
			roll_AlertUtil.setScore(alert.getScore());
			roll_AlertUtil.setOpinion(alert.getOpinion());
			
//			Student student=new Student();
//			roll_AlertUtil.setId(alert.getStudent().getId());
//			roll_Alert.setStudent(roll_AlertUtil);
//			
//			roll_Alert.setCourse(alert.getCourse());
//			roll_Alert.setScore(alert.getScore());
//			roll_Alert.setCoursetype(alert.getCoursetype());
//			roll_Alert.setOpinion(alert.getOpinion());
//			roll_Alert.setName(alert.getName());
			/*
			 * 用map封装不可行，前台获取不到数据
			 */
//			String[] studentKey={"snumber","course","score","coursetype","opinion"};
//		    Object[] studentValue={alert.getSnumber(),alert.getCourse(),alert.getScore(),alert.getCoursetype(),alert.getOpinion()};
//		    Map<String, Object>map2=dataProcess.getMapByStringArray(studentKey, studentValue);
//		    map1.put(""+i, map2);
		    list2.add(roll_AlertUtil);
		    
		}
		if (dataProcess.dataIsNull(list2)||dataProcess.listHasNull(list2))
		{
			return returnStatus.Fail;
		}else{
			string=jsonAnalyze.list2Json(list2);
		}
		
		return string;
	}
}
