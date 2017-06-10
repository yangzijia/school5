package com.b505.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Grade;
import com.b505.bean.Roll_Alert;
import com.b505.bean.util.AdminControllerUtil;
import com.b505.bean.util.GradeHBUtil;
import com.b505.bean.util.GradeUtil;
import com.b505.json.JsonAnalyze;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ReturnStatus;
import com.b505.tools.SessionGet;
import com.b505.tools.TryCatchAdminControllerService;
import com.b505.tools.TryCatchGradeService;
import com.b505.tools.TryCatchStudentService;


@Controller
public class HeadteacherAdminController
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
	private TryCatchGradeService tryCatchGradeService;
    @Autowired
    private TryCatchStudentService tryCatchStudentService;
    @Autowired
	private TryCatchAdminControllerService tryCatchAdminControllerService;
    @Autowired
	private AnalyzeData analyzeData;
    
    /*
     * 辅导员查看本班学生留降级信息
     */
    @RequestMapping(value="getWebAdminControllerByGradeID.html")
    public String getWebAdminController(){
    	return "headteachercollegeAdmin";
    }
    
    /*
     * 辅导员查看不及格成绩的详细信息
     */
    @RequestMapping(value="HeadteacherCheckOutRollAlert.html")
    public String getHeadteacherCheckOutRollAlert(HttpServletRequest request)
    {
    	String StudentNumber=request.getParameter("StudentNumber");
    	String StudentName=request.getParameter("StudentName");
    	request.getSession().setAttribute("StudentNumber", StudentNumber);
    	request.getSession().setAttribute("StudentName", StudentName);
    	return "HeadteacherCheckOutRollAlert";
    }
    
    /**
     * @作者：JSY
     * @功能：根据辅导员的昵称得到班级的id
     * @功能说明：根据辅导员的昵称得到班级的id
     * @时间：2016.5.23
     */
    @RequestMapping(value="/getGradeUtilBynickName.html")
	@ResponseBody
	public String getGradeUtils(HttpServletRequest request) throws Exception{
    	String gradeUtilString = null;
    	String headteachername=sessionGet.getUserInfo().getUsername();
    	if (headteachername==null||"".equals(headteachername))
		{
			return returnStatus.CannotAnalyzeData;
		}else {
			List<GradeUtil>gradeUtils=tryCatchGradeService.getGradeUtilsByHeadTeacherNickName(headteachername);
			//System.out.println("gradeUtils----->"+gradeUtils);
		    if (dataProcess.dataIsNull(gradeUtils)||dataProcess.listHasNull(gradeUtils))
			{
				return returnStatus.Fail;
			}
		 
		    List<GradeHBUtil>gradeHBUtils=new ArrayList<GradeHBUtil>();
		    final int size=gradeUtils.size();
		    for (int i = 0; i < size; i++)
			{
				GradeUtil gu=gradeUtils.get(i);
				GradeHBUtil gb =new GradeHBUtil();
				gb.setGrade(gu.getYearClass()+"级"+gu.getProfession()+gu.getClassId()+"班");
				gb.setId(gu.getGradeId());
				gradeHBUtils.add(gb);
			}
		    
		    gradeUtilString=jsonAnalyze.list2Json(gradeHBUtils);
		}
		return gradeUtilString;
    	
    }
    
    /**
     * @author 少游
     * @param 辅导员根据班级ID查询不及格的同学
     * @time 2016.10.20
     * @throws Exception
     */
     @RequestMapping(value="/WebAdminControllerByGradeID.html")
     @ResponseBody
     public String getAdminControllerByGradeID(@RequestBody String requestJsonBody)throws Exception{
    	 
    	 String gradeID = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "gradeID");
    	 //测试数据
    	 //String gradeID="35";
    	 //System.out.println("gradeID------>"+gradeID);
    	 if (dataProcess.dataIsNull(gradeID))
		{
			return returnStatus.CannotAnalyzeData;
			
		} 
    	 Grade grade=new Grade();
    	 grade=tryCatchGradeService.getClassByGradeID(Integer.parseInt(gradeID));
    	 System.out.println(grade);
    	 if (dataProcess.dataIsNull(grade))
		{
			return returnStatus.CannotAnalyzeData;
		}
    	 
    	List<AdminControllerUtil> Adminlist=tryCatchAdminControllerService.getAdminControllerUtilByGrade(grade.getYearClass(), grade.getProfession(), grade.getClassId());
    	System.out.println("Adminlist----->"+Adminlist);
    	if (dataProcess.dataIsNull(Adminlist)||dataProcess.listHasNull(Adminlist))
		{
    		return returnStatus.Fail;
		}
    	String[] responseKey = {"Adminlist"};
		Object[] responseValue = {Adminlist};
		Map<String,Object> responseMap = dataProcess.getMapByStringArray(responseKey, responseValue);
		return jsonAnalyze.map2Json(responseMap);	
    	
     }
     
     /**
      * @作者：JSY
      * @功能：辅导员查看本班留降级学生的详细信息
      * @时间：2016.6.4
      */
     @RequestMapping(value="/getstudentDetailStudentNumber.html")
     @ResponseBody
     public String getheadteacherCheckOutDetail(HttpServletRequest request)throws Exception{
    	
    	 System.out.println("执行了吗");
    	 String studentNumber=(String) request.getSession().getAttribute("StudentNumber");
    	 String StudentName=(String) request.getSession().getAttribute("StudentName");
    	
    	 if ("".equals(studentNumber))
		{
			return returnStatus.CannotAnalyzeData;
		}else {
			
			Roll_Alert ra=tryCatchGradeService.getRoll_AlertBystudentNumber(studentNumber,StudentName);
			System.out.println("ra的值为：--》"+ra);
			if (dataProcess.dataIsNull(ra))
			{
				return returnStatus.Fail;
			}
			//将预警成绩放入键值对中
			String[] key={"name","score","course","coursetype","opinion"};
			String[] value={ra.getName(),ra.getScore(),ra.getCourse(),ra.getCoursetype(),ra.getOpinion()};
			Map<String, Object>mapRoll=dataProcess.getMapByStringArray(key, value);
			System.out.println(jsonAnalyze.map2Json(mapRoll));
			 return jsonAnalyze.map2Json(mapRoll);
		}
    	
    	 
     }
     
}
