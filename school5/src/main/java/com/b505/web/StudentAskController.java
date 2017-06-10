package com.b505.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Student;
import com.b505.bean.StudentAsk;
import com.b505.bean.Type;
import com.b505.bean.util.GradeId;
import com.b505.bean.util.TeacherId;
//import com.b505.bean.util.TeacherRegisterUtil;
import com.b505.bean.util.studentAndstudetAsk;
import com.b505.json.JsonAnalyze;
import com.b505.json.UserJsonAnalyze;
import com.b505.service.IStudentService;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.Regex;
import com.b505.tools.ReturnStatus;
import com.b505.tools.SessionGet;
import com.b505.tools.StatusMap;
import com.b505.tools.TryCatchGradeService;
import com.b505.tools.TryCatchStudentAskService;
import com.b505.tools.TryCatchStudentService;
import com.b505.tools.TryCatchTeacherClassService;
import com.b505.tools.TryCatchTeacherService;


@Controller
public class StudentAskController {
	
	
	@Autowired
	private UserJsonAnalyze userJsonAnalyze;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private TryCatchStudentAskService tryCatchStudentAskService;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private TryCatchTeacherClassService trTeacherClassService;
	@Autowired
	private TryCatchTeacherService trTeacherService;
	@Autowired
	private TryCatchStudentService trStudentService;
	@Autowired
	private SessionGet sessionGet;	
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private IStudentService studentService;
	@Autowired
	private TryCatchGradeService trCatchGradeService;
	@Autowired
	private Regex regex;

	
	/*
	 * @方法名：getStudentAsk()
	 * @功能：学生请假页面
	 * @功能说明：学生请假页面
	 * @创建时间：2015.06.06
	 */
	@RequestMapping(value="/webstudentAsk.html")
	public String getStudentAsk(){
		return "studentAsk";
	}
	
	
	/*
	 * @方法名：getstudentAskDetail()
	 * @功能：学生用户与教师用户查询个人请假信息详情页面
	 * @功能说明：显示学生详细的请假信息
	 * @创建时间：2015.09.18
	 */
	@RequestMapping(value="/studentAskDetail2.html")
	public String getstudentAskDetail2(HttpServletRequest request){
		
		String askid = request.getParameter("askid");
		request.getSession().setAttribute("askid",askid);
		return "studentAskDetail2";
	}
	
	/*
	 * @方法名：getStudentmyAsk()
	 * @功能：学生查询个人请假信息页面
	 * @功能说明：学生查询个人请假信息页面，查看假条是否已得到批准
	 * @创建时间：2015.06.06
	 */
	@RequestMapping(value="/myAsk.html")
	public String getStudentmyAsk(){
		return "myAsk";
	}
	
	/*
	 * @方法名：getHeadTeaAskSearch()
	 * @功能：辅导员查看学生在线请假信息界面
	 * @功能说明：辅导员查看学生在线请假信息界面,并审批假条
	 * @创建时间：2015.09.10
	 */
	@RequestMapping(value="/webheadTeaAskSearch.html")
	public String getHeadTeaAskSearch(){
		return "headTeaAskSearch";
	}
	
	/*
	 * @方法名：getHeadTeaAskSearch1()
	 * @功能：辅导员查看审批通过的假条
	 * @创建时间：2015.11.14
	 */
	@RequestMapping(value="/webheadTeaAskSearch1.html")
	public String getHeadTeaAskSearch1(){
		return "headTeaAskSearch1";
	}
	
	/*
	 * @方法名：getHeadTeaAskSearch2()
	 * @功能：辅导员查看审批未通过的假条
	 * @创建时间：2015.11.14
	 */
	@RequestMapping(value="/webheadTeaAskSearch2.html")
	public String getHeadTeaAskSearch2(){
		return "headTeaAskSearch2";
	}
	
	/*
	 * @方法名：getHeadTeaAskSearch3()
	 * @功能：辅导员查看学生已销假的假条
	 * @创建时间：2015.11.14
	 */
	@RequestMapping(value="/webheadTeaAskSearch3.html")
	public String getHeadTeaAskSearch3(){
		return "headTeaAskSearch3";
	}
	
	/*
	 * @方法名：getLeaderAskSearch()
	 * @功能：领导查看学生请假信息界面
	 * @功能说明：领导查看学生请假信息界面
	 * @创建时间：2015.10.23
	 */
	@RequestMapping(value="/leaderAskSea.html")
	public String getLeaderAskSearch(){
		return "leaderAskSea";
	}
	
	
	/*
	 * @方法名：getAdminAskSearch()
	 * @功能：管理员查看学生请假信息界面
	 * @功能说明：管理员查看学生请假信息界面,并可清空辅导员以批准销假的假条
	 * @创建时间：2015.10.23
	 */
	@RequestMapping(value="/adminAskSearch.html")
	public String getAdminAskSearch(){
		return "adminAskSearch";
	}
	
	/*
	 * @方法名：getTeacherAskSearch()
	 * @功能：任课教师查看学生请假信息界面
	 * @功能说明：任课教师查看学生请假信息界面
	 * @创建时间：2015.10.25
	 */
	@RequestMapping(value="/teacherAskSea.html")
	public String getTeacherAskSearch(){
		return "teacherAskSea";
	}
	
	/*
	 * @方法名：studentAsk(@RequestBody String requestJsonBody,HttpServletRequest request)
	 * @功能：保存学生请假信息
	 * @功能说明：学生请假时将其所填信息插入数据库中
	 * @修改：在保存数据时添加了家长的姓名和联系方式，并且对手机号进行验证
	 * @修改人：JSY
	 * @修改时间：2016.4.10
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/studentAsk.html")
	@ResponseBody
	public String studentAsk(@RequestBody String requestJsonBody) throws Exception
	{
		System.out.println(requestJsonBody);
		if(requestJsonBody==null||"".equals(requestJsonBody)||requestJsonBody.length()<0)
		{
			return returnStatus.CannotAnalyzeData;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		
		map = userJsonAnalyze.askJsonAnalyze(requestJsonBody);
		

		if(map == null)
		{
			System.err.println("map == null");
			return returnStatus.CannotAnalyzeData;
		}
		
		StudentAsk  sAsk = (StudentAsk)map.get("sAsk");
		System.out.println("sAsk的值为：------》"+sAsk);
		//判断父母手机号的格式是否正确
		String ParentsPhone= sAsk.getParentsPhone();
		
		if (!regex.phoneRegex(ParentsPhone))
		{   
			//返回手机号格式不正确
			return returnStatus.PhoneError;
		}
		
		if(sAsk==null)
		{
			System.err.println("出错了！");
			return returnStatus.CannotAnalyzeData;
		}
		
		//将请假信息存入ask表中
		try
		{
			tryCatchStudentAskService.saveStudentAsk(sAsk);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("出错了！");
			return returnStatus.Fail;
		}

		//检测请假信息是否存入成功
		//测试方法有问题，服务端已修改。需要重新与ios和android端测试测试。		
		//String nickName = sAsk.getUser().getNickName();
		String nickName = sAsk.getStudent().getUser().getNickName();
		String teacherName = sAsk.getHeadteacher();
		
		//int askSuccess = tryCatchStudentAskService.hasAskSuccess(sAsk.getStunumber());
		
		//验证修改hql语句  teachername不关联---修改完毕
		int askSuccess = tryCatchStudentAskService.hasAskSuccess(nickName,teacherName);
		System.out.println("askSuccess===="+askSuccess);
        if(askSuccess==1)
		{
        	//请假成功
			return returnStatus.Success;
		}
		else if(askSuccess==0)
		{
			//请假失败
			return returnStatus.Fail;
		}
		else
		{
			//出现错误，请等待
			return returnStatus.Auditing;
		}
	}
	
	/*
	 * @服务端
	 * @方法名：getHeadTeaNameBynickName()
	 * @功能：根据学生昵称得到其辅导员的姓名
	 * @功能说明：根据学生学号先得到其所在班级，再由班级得到辅导员id，再由辅导员id得到辅导员的姓名
	 * @创建时间：2015.06.10
	 * @二次修改时间：2016.04.07
	 * @修改人：JSY
	 */
	@RequestMapping(value = "/getHeadTeaNameBynickName.html")
	@ResponseBody
	public String getHeadTeaNameBynickName(HttpServletRequest request)throws Exception
	{
		String studentNickname = sessionGet.getUserInfo().getUsername();
		/*LoginUser user = (LoginUser) request.getSession().getAttribute("user");	
		String studentNickname = user.getNickName();*/
		if(dataProcess.dataIsNull(studentNickname))
		{
			return returnStatus.CannotAnalyzeData;
		}
		//方法1：由nickName得到班级id，在student表中
		GradeId gradeid = tryCatchStudentAskService.getClassIdByNickname(studentNickname);
		
		if(gradeid.equals(null)){
			return returnStatus.Fail;
		}	
		int gid = gradeid.getGid();
		//int gid=32;测试使用
		//方法2：由班级id得到辅导员id，在teacher_class表中----->错误，已修改：在headteacher_class表中
		TeacherId tId = trTeacherClassService.getTidByS_C_ID(gid);
		int tid = tId.getTid();
		
		//3由教师的id号得到辅导员的姓名，在teacher表中
	    //Map<String, Object> hteachername = trTeacherService.getTeaNameByTid(tid);
		String hteachername = trTeacherService.getTeaNameByTid1(tid);
		
		if(dataProcess.dataIsNull(hteachername))
		{
			return returnStatus.NotHaveUser;
		}
		
		//将辅导员姓名放入键值对中
		String[] key = {"teacherName"};
		String[] value ={hteachername};

		Map<String,Object> hMap = dataProcess.getMapByStringArray(key, value);
		
		if(dataProcess.dataIsNull(hMap))
		{
			return returnStatus.Fail;
		}		
		//转为json返回给页面
		return jsonAnalyze.map2Json(hMap);
	}
	
	/*getHeadTeaNameByClientnickName
	 * @客户端
	 * @方法名：getHeadTeaNameByClientnickName()
	 * @功能：客户端根据学生昵称得到其辅导员的姓名和手机号
	 * @功能说明：根据学生学号先得到其所在班级，再由班级得到辅导员id，再由辅导员id得到辅导员的姓名和手机号
	 * @修改时间：2016.3.26
	 * @修改人：贾少游
	 * 
	 */
	@RequestMapping(value = "/getHeadTeaNameByClientnickName.html")
	@ResponseBody
	public String getHeadTeaNameByClientnickName(@RequestBody String requestJsonBody)throws Exception
	{
		String studentNickname = (String)dataProcess.getMapValueByString(jsonAnalyze.json2Map(requestJsonBody), "Nickname");
	    //测试
		//String studentNickname="201542853";
		if(dataProcess.dataIsNull(studentNickname))
		{
			return returnStatus.CannotAnalyzeData;
		}
		//方法1：由nickName得到班级id，在student表中
		GradeId gradeid = tryCatchStudentAskService.getClassIdByNickname(studentNickname);
		if(gradeid.equals(null)){
			return returnStatus.Fail;
		}	
		int gid = gradeid.getGid();
		//int gid=18;测试使用
		//方法2：由班级id得到辅导员id，在teacher_class表中,,,,,,错误，已修改：在headteacher_class表中
		TeacherId tId = trTeacherClassService.getTidByS_C_ID(gid);
		int tid = tId.getTid();
		//方法3：由教师的id号得到辅导员的姓名和手机号，在teacher表中
		Map<String, Object> hteachername = trTeacherService.getTeaNameByTid(tid);
		
		if(dataProcess.dataIsNull(hteachername))
		{
			return returnStatus.NotHaveUser;
		}
		
	/**	
	 * //将辅导员姓名放入键值对中
		String[] key = {"hteachername",};
		String[] value ={hteachername};

		Map<String,Object> hMap = dataProcess.getMapByStringArray(key, value);
		
	    if(dataProcess.dataIsNull(hMap))
		{			return returnStatus.Fail;
		}		
		//转为json返回给页面
	* 
    **/
		return jsonAnalyze.map2Json(hteachername);
	}
	
	/*
	 * @方法名：getStudentAsksByGradeIdandStatus(@RequestBody String requestJsonBody)
	 * @功能：辅导员由班级id和请假状态获取该班学生部分请假信息列表
	 * @功能说明：辅导员由班级id和请假状态获取该班学生部分请假信息列表
	 * @创建时间：2015.06.17
	 * @修改时间：2015.09.23,二次修改：2015.11.04
	 */
	@RequestMapping(value = "/getStudentAsksByGradeIdandType.html")
	@ResponseBody
	public String getStudentAsksByGradeIdandStatus(@RequestBody String requestJsonBody)throws Exception
	{
System.out.println("getStudentAsksByGradeIdandType=="+requestJsonBody);
		String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
		Map<String,Object> map = new HashMap<String,Object>();
		Integer gradeId ;
		String statusString;
		try
		{
			 map = jsonAnalyze.json2Map(requestJsonBody);	

			 gradeId = (int)map.get("gradeId");
			 statusString = (String)map.get("type");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("辅导员查询其管理班级的学生请假信息;出错");
			return cannotAnalyzeData;
		}
		List<StudentAsk> sAskList = tryCatchStudentAskService.getSAskByGradeIdandStatus(gradeId, statusString);	
		
		if(dataProcess.dataIsNull(sAskList)){
			
			String sAskJson = null;
			return sAskJson;		
			
		}
		
		List<studentAndstudetAsk> SandAskList = new ArrayList<studentAndstudetAsk>(); 		
		
		//sAskList和studentNameList长度一样的
		for(int i=0;i<sAskList.size();i++){
			
			studentAndstudetAsk sandAsk = new studentAndstudetAsk();
			
			int askid = sAskList.get(i).getAskid();
			sandAsk.setAskid(askid);
	
			String studentName = sAskList.get(i).getStudent().getStudentName();
			sandAsk.setStudentName(studentName);
			
			String type = sAskList.get(i).getType();
			sandAsk.setType(type);
			
			String starttime = sAskList.get(i).getTimestart();
			sandAsk.setStarttime(starttime);
			
			String endtime = sAskList.get(i).getTimeend();
			sandAsk.setEndtime(endtime);
			
			String status = sAskList.get(i).getStatus();
			sandAsk.setStatus(status);
			
			SandAskList.add(sandAsk);	
		}
		
		//将请假信息转由list为json
		String sAskJson = jsonAnalyze.list2Json(SandAskList);
		System.out.println("sAskJson===="+sAskJson);

		if(dataProcess.dataIsNull(sAskJson))
		{
			return returnStatus.Fail;
		}
		
		//将json格式的学生请假信息列表返回给客户端，结果可以有很多条
		return sAskJson;
			
	}

	
	/*
	 * @方法名：getType
	 * @功能：获取请假类型
	 * @创建时间：2015.09.19
	 * @修改后，此方法不用
	 */
	@RequestMapping(value="/getType.html")
	@ResponseBody
	public String getType()throws Exception 
	{
		List<Type> typeList = tryCatchStudentAskService.getType();
		if(dataProcess.dataIsNull(typeList)||dataProcess.listHasNull(typeList))
		{
			return returnStatus.Fail;
		}
		String string = jsonAnalyze.list2Json(typeList);
		if(dataProcess.dataIsNull(string))
		{
			return returnStatus.Fail;
		}
		 return string;
	}
	
	
	/*
	 * @方法名：getStudentAskBynickNameAndtype(@RequestBody String requestJsonBody)
	 * @功能：学生查看自己请假信息列表
	 * @功能说明：结果：查看辅导员是否已经批准自己的请假
	 * @创建时间：2015.09.16
	 */
	@RequestMapping(value = "/getStudentAskBynickNameAndtype.html")
	@ResponseBody
	public String getStudentAskBynickNameAndtype(@RequestBody String requestJsonBody)throws Exception
	{
		
		String studentNickname = sessionGet.getUserInfo().getUsername();
		//接收json格式的假条状态并转为String类型，与学生用户昵称一起，条件查询学生请假信息
		String type = (String)analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "type");

		
		//由学生昵称得到学生的请假信息列表
		List<StudentAsk> sAskList =trStudentService.getStudentAskBynickNameAndtype(studentNickname,type);
		
		
		//将请假信息转由list为json
		String sAskJson = jsonAnalyze.list2Json(sAskList);
		
		if(dataProcess.dataIsNull(sAskJson))
		{
			return returnStatus.Fail;
		}

		//将json格式的学生请假信息返回给客户端，结果可以有很多条
		System.out.println("sAskJson=========="+sAskJson);
		return sAskJson;
	}	

	
	/*
	 * @方法名：getstudentAskDetailByaskId(@RequestBody String requestJsonBody)
	 * @功能：学生用户与教师查看学生请假信息详情,信息需要一一对应
	 * @功能说明：结果：查看请假的详细信息
	 * @创建时间：2015.09.18
	 */
	@RequestMapping(value = "/getstudentAskDetailByaskId.html")
	@ResponseBody
	public String getstudentAskDetailByaskId(HttpServletRequest request)throws Exception
	{
			
		//获取askid,利用askid的唯一性来将学生请假信息一一对应
		
		int askid = Integer.parseInt((String)request.getSession().getAttribute("askid"));
		
		//由学生昵称得到学生请假的详细信息，不可取，一个人的多条请假信息无法一一对应，智只会显示第一条
		//StudentAsk sAsk = trStudentService.getstudentAskDetailBynickName(studentnickName);
		//由请假id查询该条请假信息，并将信息中的学生昵称取出，以及用户查询学生部分个人信息
		StudentAsk sAsk = trStudentService.getstudentAskDetailByaskId(askid);
	    
		//String studentnickName = sAsk.getUser().getNickName();
		String studentnickName = sAsk.getStudent().getUser().getNickName();
		Student student = trStudentService.getStudentByNickname(studentnickName);
		
		if(dataProcess.dataIsNull(sAsk)||(dataProcess.dataIsNull(student)))
		{
			return returnStatus.Fail;
		}
		
		//将请假信息放入键值对中
		String[] key = {"studentName","teacherName","snumber","phone","StartTime","EndTime","type","status","reason","ParentsName","ParentsPhone"};
		String[] value ={student.getStudentName(),sAsk.getHeadteacher(),student.getId(),student.getStudentPhone(),sAsk.getTimestart(),sAsk.getTimeend(),sAsk.getType(),sAsk.getStatus(),sAsk.getReason(),sAsk.getParentsName(),sAsk.getParentsPhone()};

		Map<String,Object> hMap = dataProcess.getMapByStringArray(key, value);
		
		if(dataProcess.dataIsNull(hMap))
		{
			return returnStatus.Fail;
		}
		
		//将学生请假信息的详情转为json，并返回给页面
		return jsonAnalyze.map2Json(hMap);
	}
	
	/*
	 * @方法名：askDel(@RequestBody String requestJsonBody)
	 * @功能：学生取消请假申请或者教师同意学生用户销假
	 * @功能说明：学生取消请假申请或者教师同意学生用户销假
	 * @创建时间：2015.09.21
	 */
	@RequestMapping(value="/askDel.html")
	@ResponseBody
	public String askDel(@RequestBody String requestJsonBody)throws Exception
	{
		//接收json格式的askids，并转为String格式
		@SuppressWarnings("unchecked")
		List<Integer> askidList = (List<Integer>) analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "askids");
		
		if(dataProcess.dataIsNull(askidList)||dataProcess.listHasNull(askidList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchStudentAskService.deleteAskByBatch(askidList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	
	/*
	 * @方法名：askUpdate(@RequestBody String requestJsonBody)
	 * @功能：学生用户批量销假
	 * @功能说明：学生用户批量销假
	 * @创建时间：2015.09.21
	 */
	@RequestMapping(value="/askUpdate.html")
	@ResponseBody
	public String askUpdate(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		//接收json格式的askids和status，并转为String格式
		List<Map<String,Object>> askList = (List<Map<String, Object>>) analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "askList");
		System.out.println("askList----->"+askList);
		if(dataProcess.dataIsNull(askList)||dataProcess.listHasNull(askList))
		{
			return returnStatus.CannotAnalyzeData;
		}
		if(!tryCatchStudentAskService.updateAskByBatch(askList))
		{
			return returnStatus.Fail;
		}
		return returnStatus.Success;
	}
	
	/*
	 * @方法名：adminSeachStudentAsk(@RequestBody String requestJsonBody)
	 * @功能：管理员查看各班学生请假信息,实现方法与辅导员类似，只是班级是所有班
	 * @作者：lyf
	 * @创建时间:2015.10.25
	 * @修改时间:2015.11.05
	 */
	@RequestMapping(value="/adminSeachStudentAsk.html")
	@ResponseBody
	public String adminSeachStudentAsk(@RequestBody String requestJsonBody) throws Exception
	{
		//状态返回
		//String Fail = statusMap.status("Fail");	
		String cannotAnalyzeData = statusMap.status("cannotAnalyzeData");
		Map<String,Object> map = new HashMap<String,Object>();
		//Integer gradeID = 0;
		String yearClass ;
		String profession ;
		String classId ;
		String statusString;
		try
		{
			 map = jsonAnalyze.json2Map(requestJsonBody);	
			 //需通过yearClass、profession、classId解析出gradeID
			 yearClass = (String)map.get("yearClass");
			 profession =(String)map.get("profession");
			 classId = (String)map.get("classId");
			 statusString = (String)map.get("type");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("管理员查询学生请假信息;出错");
			return cannotAnalyzeData;
		}
		
		//方法1：通过年级专业班级，解析到gradeId
		//==============================(存储过程)
		int gradeId = trCatchGradeService.getGradeIdByGrade(yearClass, profession, classId);	
		//方法2：由gradeId和请假状态得到学生请假信息列表
		List<StudentAsk> sAskList = tryCatchStudentAskService.getSAskByGradeIdandStatus(gradeId, statusString);
	
		
		if(dataProcess.dataIsNull(sAskList)){
			
			String sAskJson = null;
			return sAskJson;		
			
		}	
		
		List<studentAndstudetAsk> SandAskList = new ArrayList<studentAndstudetAsk>(); 
			
		for(int i=0;i<sAskList.size();i++){
			
			studentAndstudetAsk sandAsk = new studentAndstudetAsk();
			
			int askid = sAskList.get(i).getAskid();
			sandAsk.setAskid(askid);
	
			String studentName = sAskList.get(i).getStudent().getStudentName();
			sandAsk.setStudentName(studentName);
			
			String type = sAskList.get(i).getType();
			sandAsk.setType(type);
			
			String starttime = sAskList.get(i).getTimestart();
			sandAsk.setStarttime(starttime);
			
			String endtime = sAskList.get(i).getTimeend();
			sandAsk.setEndtime(endtime);
			
			String status = sAskList.get(i).getStatus();
			sandAsk.setStatus(status);
			
			SandAskList.add(sandAsk);	
		}
		
		//将请假信息转由list为json
		String sAskJson = jsonAnalyze.list2Json(SandAskList);
		System.out.println("sAskJson===="+sAskJson);

		if(dataProcess.dataIsNull(sAskJson))
		{
			return returnStatus.Fail;
		}
		
		//将json格式的学生请假信息列表返回给客户端，结果可以有很多条
		return sAskJson;  
		
	}
	
	/**
	 * @客户端
	 * @author 少游
	 * @方法名：getStudentAsksByClientandStatus(@RequestBody String requestJsonBody ) throws Exception
	 * @功能：辅导员通过手机端查看请假学生的详细信息
	 * @创建时间：2016.11.18
	 */
	
	@RequestMapping(value="getStudentAsksByClientandStatus.html")
	@ResponseBody
	public String getStudentAsksByClientandStatus(@RequestBody String requestJsonBody) throws Exception{
System.out.println(requestJsonBody);
		Map<String, Object>map =new HashMap<String, Object>();
		Integer gradeId;
		String type;
		try
		{
			//将客户端传来的json数据转化成map数据
			map=jsonAnalyze.json2Map(requestJsonBody);
			//得到班级号
			 gradeId=(Integer) map.get("gradeId");
			//gradeId=32;
			 System.out.println("gradeId------->"+gradeId);
			//得到要查询的请假类型
			 //type=(String) map.get("status");
			type="申请中";
			 System.out.println("type--->"+type);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("辅导员查询其管理班级的学生请假信息;出错");
			return returnStatus.CannotAnalyzeData;
		}
		//获取学生请假列表
		List<StudentAsk> sAskList = tryCatchStudentAskService.getSAskByGradeIdandStatus(gradeId, type);	
		System.out.println("sAskList---->"+sAskList);
		if (sAskList==null||dataProcess.listHasNull(sAskList))
		{
			return returnStatus.CannotAnalyzeData;
		}else {
			Map<String,Object> newsSectionMap = new HashMap<String,Object>();
			//List<studentAndstudetAsk>list=new ArrayList<studentAndstudetAsk>();
			//List<Map<String, String>>list=new ArrayList<>();
			final int size=sAskList.size();
			for (int i = 0; i < size; i++)
			{
				StudentAsk studentAsk=sAskList.get(i);

				String[] sectionMapKey = {"askid","studentName","type","status","starttime","endtime"};
				Object[] sectionMapValue = {studentAsk.getAskid(), studentAsk.getStudent().getStudentName(), studentAsk.getType(),studentAsk.getStatus(),studentAsk.getTimestart(), studentAsk.getTimeend()};
				Map<String,Object> map1 = dataProcess.getMapByStringArray(sectionMapKey, sectionMapValue);
				System.out.println("map1------>"+map1);
				newsSectionMap.put(i+"",map1);
				System.out.println("newsSectionMap---->"+newsSectionMap);
			}
			
			//将请假信息转由list为json
			String sAskJson = jsonAnalyze.map2Json(newsSectionMap);
			return sAskJson;
		}
	
	}
	/**
	 * @客户端
	 * @author 少游
	 * @方法：UpdateAsk(@RequestBody String requestJsonBody)throws Exception
	 * @功能：导员在手机端在线审批假条
	 * @time：2016.11.18
	 */
	
	@RequestMapping(value="/Updateask.html")
	@ResponseBody
	public String UpdateAsk(@RequestBody String requestJsonBody)throws Exception
	{
System.out.println(requestJsonBody);
		System.out.println("执行了吗？");
		if (requestJsonBody==null||dataProcess.dataIsNull(requestJsonBody))
		{
			String[] sectionMapKey= {"notHave"};
			Object[] sectionMapValue={"CannotAnalyzeData"};
			Map<String, Object> map1=dataProcess.getMapByStringArray(sectionMapKey, sectionMapValue);
System.out.println("----***=="+jsonAnalyze.map2Json(map1));
			return jsonAnalyze.map2Json(map1);
		}
		//接收json格式的askids和status，并转为String格式
		@SuppressWarnings("unchecked")
		List<Map<String, Object>>list=(List<Map<String, Object>>) analyzeData.clientDataBeAnalyzedToServiceDataList2String(requestJsonBody, "askList");
		System.out.println("list------》"+list);
		if(dataProcess.dataIsNull(list)||dataProcess.listHasNull(list))
		{
			String[] sectionMapKey= {"notHave"};
			Object[] sectionMapValue={"CannotAnalyzeData"};
			Map<String, Object> map1=dataProcess.getMapByStringArray(sectionMapKey, sectionMapValue);
			System.out.println("----***=="+jsonAnalyze.map2Json(map1));
			return jsonAnalyze.map2Json(map1);
		}
		if(!tryCatchStudentAskService.updateAskByBatch(list))
		{
			String[] sectionMapKey={"Fail"};
			Object[] sectionMapValue={"Fail"};
			Map<String, Object>map2=dataProcess.getMapByStringArray(sectionMapKey, sectionMapValue);
			System.out.println("----***=="+jsonAnalyze.map2Json(map2));
			return jsonAnalyze.map2Json(map2);
		}
		String[] sectionMapKey={"Success"};
		Object[] sectionMapValue={"Success"};
		Map<String, Object>map3=dataProcess.getMapByStringArray(sectionMapKey, sectionMapValue);
		System.out.println("----***=="+jsonAnalyze.map2Json(map3));
		return jsonAnalyze.map2Json(map3);
	}
}
