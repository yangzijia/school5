/*
*@包名：com.b505.web        
*@文档名：StudentChangeInformationController.java
*@功能： 学生信息修改     
*@作者：李振强        
*@创建时间：2014.3.12  
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.Student;
import com.b505.json.JsonAnalyze;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.Regex;
import com.b505.tools.ReturnStatus;
//import com.b505.tools.SSHA;
import com.b505.tools.DigestUtils;
import com.b505.tools.SessionGet;
import com.b505.tools.TryCatchStudentService;
import com.b505.tools.TryCatchUserService;

@Controller
public class StudentChangeInformationController
{
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	//private SSHA sSHA;
	private DigestUtils du;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private AnalyzeData analyzeData;	
	@Autowired
	private Regex regex;
	@Autowired
	private SessionGet sessionGet;
	
	/*
	 * @方法名：studentChange(@RequestBody String requestJsonBody)
	 * @功能：学生修改个人信息
	 * @功能说明：学生修改个人信息
	 * @作者：李振强
	 * @创建时间：2014.3.10
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value = "/studentChange.html")
	@ResponseBody
	public String studentChange(@RequestBody String requestJsonBody)throws Exception
	{
		@SuppressWarnings("unchecked")
		Map<String,Object> userInformation = (Map<String,Object>)analyzeData.clientDataBeAnalyzedToServiceDataMap(requestJsonBody);

		//这是一个String 键数组
		String[] studentNicknameKey = {"userNickname", "role"};
		//这是一个object 值数组
		Object[] studentNicknameValue = dataProcess.getMapValueByKey(userInformation, studentNicknameKey);
		if(dataProcess.dataIsNull(studentNicknameValue)||dataProcess.arrayHasNull(studentNicknameValue))
		{
			return returnStatus.CannotAnalyzeData;
		}
		//studentNicknameValue[1]是object数组里的第二个值
		if(!"Role_Student".equals(studentNicknameValue[1]))
		{
			return returnStatus.NotHaveUser;
		}
		String[] studentPasswordKey = {"oldPassword", "newPassword"};
		Object[] studentPasswordValue = dataProcess.getMapValueByKey(userInformation, studentPasswordKey);
		
		String[] studentPhoneKey = {"phone"};
		Object[] studentPhoneValue = dataProcess.getMapValueByKey(userInformation, studentPhoneKey);
	
		if(dataProcess.dataIsNull(studentPasswordValue)||dataProcess.arrayHasNull(studentPasswordValue))
		{
			if(dataProcess.dataIsNull(studentPhoneValue)||dataProcess.arrayHasNull(studentPhoneValue))
			{
				return returnStatus.CannotAnalyzeData;
			}
			if(!regex.phoneRegex((String)studentPhoneValue[0]))
			{
				return returnStatus.PhoneError;
			}
			if(!tryCatchStudentService.updatePhoneByNickName((String)studentPhoneValue[0], (String)studentNicknameValue[0]))
			{
				return returnStatus.Fail;
			}
		}else
		 {
			if(!du.digest((String)studentPasswordValue[0]).equals(tryCatchUserService.getUserByNickname((String)studentNicknameValue[0]).getPassword()))
			{
				return returnStatus.OldPasswordError;
			}
			if(!tryCatchUserService.updatePasswordByNickName(du.digest((String)studentPasswordValue[1]), (String)studentNicknameValue[0]))
			{
				return returnStatus.Fail;
			}
			if(!dataProcess.dataIsNull(studentPhoneValue)&&!dataProcess.arrayHasNull(studentPhoneValue))
			{
				if(!regex.phoneRegex((String)studentPhoneValue[0]))
				{
					return returnStatus.PhoneError;
				}
				if(!tryCatchStudentService.updatePhoneByNickName((String)studentPhoneValue[0], (String)studentNicknameValue[0]))
				{
					return returnStatus.Fail;
				}
			}
		}
		return returnStatus.Success;
	}	
	
	
	/*客户端显示学生个人信息
	 * @方法名：studentShow(@RequestBody String requestJsonBody)
	 * @功能：学生查看个人信息
	 * @功能说明：学生查看个人信息
	 * @作者：李振强
	 * @创建时间：2014.5.30
	 * @修改时间：2014.5.30
	 * @修改备注：
	 */
	@RequestMapping(value = "/studentShow.html")
	@ResponseBody
	public String studentShow(@RequestBody String requestJsonBody)throws Exception
	{
		String studentNickname = (String)dataProcess.getMapValueByString(jsonAnalyze.json2Map(requestJsonBody), "userNickname");
		
		if(dataProcess.dataIsNull(studentNickname))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Student student = tryCatchStudentService.getStudentByNickname(studentNickname);
		if(dataProcess.dataIsNull(student))
		{
			return returnStatus.NotHaveUser;
		}
		String[] studentKey = {"name", "phone", "cardId", "college","grade", "profession", "class","studentNumber","studentImageURL"};
		String[] studentValue = {student.getStudentName(), student.getStudentPhone(), student.getStudentCardId(), student.getCollege().getCollegeName(), student.getStudentClass().getYearClass(), student.getStudentClass().getProfession(), student.getStudentClass().getClassId(),student.getId(),student.getUser().getImgUrl()};
		Map<String,Object> studentMap = dataProcess.getMapByStringArray(studentKey, studentValue);
		if(dataProcess.dataIsNull(studentMap))
		{
			return returnStatus.Fail;
		}
		return jsonAnalyze.map2Json(studentMap);
	}
	

	/*web端显示学生个人信息
	 * @方法名：studentWebShow(@RequestBody String requestJsonBody)
	 * @功能：学生查看个人信息
	 * @功能说明：学生查看个人信息
	 * @作者：李振强
	 * @创建时间：2014.5.30
	 */

	@RequestMapping(value="/studentWebShow.html")
	@ResponseBody
	public String getStudentWebInformation(HttpServletRequest request) throws Exception{
        String studentNickName=sessionGet.getUserInfo().getUsername();
		/*LoginUser user = (LoginUser) request.getSession().getAttribute("user");	
		String studentNickName = user.getNickName();*/
		System.out.println("studentNickName--->"+studentNickName);
        
		if(dataProcess.dataIsNull(studentNickName))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Student student = tryCatchStudentService.getStudentByNickname(studentNickName);
		
		System.out.println("student--->"+student);
		if(dataProcess.dataIsNull(student))
		{
			return returnStatus.NotHaveUser;
		}
		String[] studentKey = {"name", "phone", "cardId", "college","grade", "profession", "studentClass","studentNumber","imgUrl"};
		String[] studentValue = {student.getStudentName(), student.getStudentPhone(), student.getStudentCardId(), student.getCollege().getCollegeName(), student.getStudentClass().getYearClass(), student.getStudentClass().getProfession(), student.getStudentClass().getClassId(),student.getId(),student.getUser().getImgUrl()};
		Map<String,Object> studentMap = dataProcess.getMapByStringArray(studentKey, studentValue);
		System.out
				.println("studentMap====="+studentMap);
		if(dataProcess.dataIsNull(studentMap))
		{
			return returnStatus.Fail;
		}
		return jsonAnalyze.map2Json(studentMap);
		
	}	
	
	/*web端管理员查看学生详细信息
	 * @方法名：getStudentWebShow(HttpServletRequest request)
	 * @功能：查看学生详细信息
	 * @功能说明：查看学生详细信息
	 * @作者：you
	 * @创建时间：2016.10.23
	 */
	@RequestMapping(value="/getStudentWebShow.html")
	@ResponseBody
	public String getStudentWebShow(HttpServletRequest request)throws Exception{
		System.out.println("执行了吗");
		String StudentNumber=(String) request.getSession().getAttribute("StudentNumber");
		System.out.println("StudentNumber---->"+StudentNumber);
		if(dataProcess.dataIsNull(StudentNumber))
		{
			return returnStatus.CannotAnalyzeData;
		}
		Student student = tryCatchStudentService.getStudentByNickname(StudentNumber);
		
		System.out.println("student--->"+student);
		if(dataProcess.dataIsNull(student))
		{
			return returnStatus.NotHaveUser;
		}
		String[] studentKey = {"nickName","name", "phone", "cardId", "college","grade", "profession", "studentClass","studentNumber","imgUrl"};
		String[] studentValue = {student.getId(),student.getStudentName(), student.getStudentPhone(), student.getStudentCardId(), student.getCollege().getCollegeName(), student.getStudentClass().getYearClass(), student.getStudentClass().getProfession(), student.getStudentClass().getClassId(),student.getId(),student.getUser().getImgUrl()};
		Map<String,Object> studentMap = dataProcess.getMapByStringArray(studentKey, studentValue);
		System.out
				.println("studentMap====="+studentMap);
		if(dataProcess.dataIsNull(studentMap))
		{
			return returnStatus.Fail;
		}
		return jsonAnalyze.map2Json(studentMap);
	}
	
	
}

