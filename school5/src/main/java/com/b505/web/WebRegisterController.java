/*
*@包名：com.b505.web        
*@文档名：WebRegisterController.java 
*@功能：Web用户注册
*@作者：陈炳旭   
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.College;
import com.b505.bean.Grade;
import com.b505.bean.LoginUser;
import com.b505.bean.util.GradeUtil;
import com.b505.json.JsonAnalyze;
import com.b505.service.ICollegeService;
import com.b505.service.IGradeService;

@Controller

public class WebRegisterController {
	
	@Autowired
	private IGradeService gradeService ;
	@Autowired
	private ICollegeService collegeService;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	
	
	/*
	 * @方法名：getStudentRegister()
	 * @功能：学生注册页面
	 * @功能说明：学生注册页面
	 * @作者：陈炳旭
	 * @创建时间：2014.3.15
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/webstudentRegister.html")
	public String getStudentRegister(){
		return "studentRegister";
	}
	
	
	/*
	 * @方法名：getTeacherRegister()
	 * @功能：普通老师注册页面
	 * @功能说明：普通老师注册页面
	 * @作者：陈炳旭
	 * @创建时间：2014.3.15
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/webteacherRegister.html")
	public String getTeacherRegister(){
		return "webteacherregister";
	}
	
	
	/*
	 * @方法名：getHeadTeacherRegister(HttpServletRequest req,HttpServletResponse res)
	 * @功能：辅导员注册页面
	 * @功能说明：辅导员注册页面
	 * @作者：陈炳旭
	 * @创建时间：2014.3.15
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/webheadteacherRegister.html")
	public String getHeadTeacherRegister(HttpServletRequest req,HttpServletResponse res){
		return "webHeadteacherRegister";
		}
	/*
	 * @方法名：getCollegeMethod()
	 * @功能：辅导员注册获得学院信息
	 * @功能说明：辅导员注册页面
	 * @作者：陈炳旭
	 * @创建时间：2014.3.15
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/getCollege.html")
	@ResponseBody
	public String getCollegeMethod(){
		List<College> college = collegeService.getAll();
		String collegeString = null;
		try {
			collegeString = jsonAnalyze.list2Json(college);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return collegeString;
	}
	/*
	 * @方法名：getGrade(HttpServletRequest request,College college)
	 * @功能：辅导员注册根据学院名称获得专业班级年级信息
	 * @功能说明：辅导员注册页面
	 * @作者：陈炳旭
	 * @创建时间：2014.3.15
	 * @修改时间：2014.4.26
	 */
	@RequestMapping(value="/getGrade.html")
	@ResponseBody
	public String getGrade(HttpServletRequest request,College college) {
		
		System.out.println(college.getCollegeName());
		List<GradeUtil> gradeUtils=gradeService.getGradeByCollege(college.getCollegeName());
		String gradeUtilstoJson = null;
		try {
			 gradeUtilstoJson = jsonAnalyze.list2Json(gradeUtils);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		return gradeUtilstoJson;
	}
	@RequestMapping(value="/headTeacherRegisterCheck.html")
	@ResponseBody
	public String headTeacherRegisterCheck(HttpServletRequest request,LoginUser user ,Grade grade ) throws IOException{
			System.out.println("dddddd");
			System.out.println(grade.getProfession());
			System.out.println(user.getRole());
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("success", "success");
			String str = jsonAnalyze.map2Json(map1);
			
		
		return  str;
	}
	@RequestMapping(value="/loginSuccess.html")
	public String getLoginSuccess()
	{
		return "success";
	}
	
	@RequestMapping(value="/getAllCollege.html")
	@ResponseBody
	public String getAllCollegeMethod(){
		List <College> list = collegeService.getAll();
		String coString = null;
		try {
			coString = jsonAnalyze.list2Json(list);
			System.out.println("coString:"+coString);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println( e);
		}
		return coString;
	}
	@RequestMapping(value="/uploadImage.html")
	public String getString(){
		return "uploadImage";
	}
}
