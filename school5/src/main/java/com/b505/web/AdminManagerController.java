package com.b505.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminManagerController 
{	
	@RequestMapping(value="/admin.html")
	public String getAdminMethod(HttpServletRequest request){
		return"admin";
	}
	@RequestMapping(value="/parentMenuManager.html")
	public String getParentMenuManagerMethod(){
		return "parentMenuManager";
	}
	@RequestMapping(value="/administratorAddManager.html")
	public String getAadministratorAddManagerMetnod(){
		return "administratorAddManager";
	}
	@RequestMapping(value="/administratorManager.html")
	public String getAdministratorManagerMetod(){
		return "administratorManager";
	}
	@RequestMapping(value="/childrenMenuManager.html")
	public String getChildrenMenuManagerMenthod(){
		return "childrenMenuManager";
	}
	
	@RequestMapping(value="/left.html")
	public String getLeftJsp(){
		return "left";
	}
	
	@RequestMapping(value="/leaderManager.html")
	public String getleaderManagerMethod()
	{
		return "leaderManager";
	}
	@RequestMapping(value="/leaderAddManager.html")
	public String getleaderAddManagerMethod()
	{
		return "leaderAddManager";
	}
	@RequestMapping(value="/gradeManager.html")
	public String getGraString(){
		return "gradeManager";
	}
	@RequestMapping(value="/teacherManager.html")
	public String getTeacherManagerMetod(){
		return "teacherManager";
	}
	@RequestMapping(value="/studentManager.html")
	public String getStudentManagerMethod(){
		return "studentManager";
	}
	@RequestMapping(value="/studentUnregisteredManager.html")
	public String getceshiManagerMethod(){
		return "studentUnregisteredManager";
	}
	@RequestMapping(value="/headTeacherManager.html")
	public String getHeadTeacherManagerMethod(){
		return "headTeacherManager";
	}
	@RequestMapping(value="/studentDetail.html")
	public String getStudentDetailMethod(HttpServletRequest request){
		String StudentNumber=request.getParameter("id");
		HttpSession hp=request.getSession();
		hp.setAttribute("StudentNumber",StudentNumber);
		return "studentDetail";
	}
	@RequestMapping(value="/newsSectionManager.html")
	public String getSectionManagerMethod(){
		return "newsSectionManager";
	}
	@RequestMapping(value="/newsManager.html")
	public String getNewsManagerMethod(){
		return "newsManager";
	}
	@RequestMapping(value="/collegeManager.html")
	public String getCollegeManagerMethod(){
		return "collegeManager";
	}
	@RequestMapping(value="/callOverManager.html")
	public String getcallOverManager(){
		return "callOverManager";
	}
	@RequestMapping(value="/ImportDataManager.html")
	public String getImportDataManagerMethod(){
		return "ImportDataManager";
	}
	@RequestMapping(value="/rescManager.html")
	public String getRescManagerMethod(){
		return "rescManager";
	}
	@RequestMapping(value="/userAndRoleManager.html")
	public String getUserRoleManagerMethod(){
		return "userAndRoleManager";
	}
	@RequestMapping(value="/roleNameManager.html")
	public String getRoleNameManagerMethod(){
		return "roleNameManager";
	}
	@RequestMapping(value="/roleAndUrlManager.html")
	public String getRoleAndMangerMethod(){
		return "roleAndUrlManager";
	}
	@RequestMapping(value="/leaderCallOverCheckOut.html")
	public String getLeaderCallOverCheckOutMenthod(){
		return "leaderCallOverCheckOut";
	}
	@RequestMapping(value="/leaderCheckOutDetail.html")
	public String getCheckOutMethod(HttpServletRequest request){
		String studentNumber = request.getParameter("StudentNumber");
		String studentName = request.getParameter("StudentName");
		HttpSession hp = request.getSession();
		hp.setAttribute("StudentNumber", studentNumber);
		hp.setAttribute("StudentName", studentName);
		System.out.println("StudentNumber"+studentNumber);
		System.out.println("studentName"+studentName);
		return "leaderCheckOutDetail";
	}
	@RequestMapping(value="/roleAndChildrenMenuManager.html")
	public String getRoleAndChildrenJsp(){
		return "roleAndChildrenMenu";
	}
	@RequestMapping(value="/roleAndParentMenuManager.html")
	public String getRoleAndParentMenuJsp(){
		return "roleAndParentMenu";
	}
	@RequestMapping(value="/headerTeacherCallOverout.html")
	public String getHeadTeacherCallOverJsp(){
		
		return "headTeacherCallOverCheckOut";
	}
	@RequestMapping(value="/newsUpdateManager.html")
	public String getUpdateNewsJsp(HttpServletRequest request){
		 Integer newsId =Integer.parseInt(request.getParameter("newsId")) ;
		HttpSession hp = request.getSession();
		hp.setAttribute("newsId", newsId);
		System.out.println("newsId"+newsId);
		return "newsUpdateInput";
	}
	@RequestMapping(value="/studentInformation.html")
	public String getStudentInformation(){
		return "studentinformation";
	}
	@RequestMapping(value="/teacherInformation.html")
	public String getTeacherInformationMetod(){
		return "teacherinformation";
	}
	@RequestMapping(value="/leaderInformation.html")
	public String getLeaderInformationMetod(){
		return "leaderinformation";
	}
	@RequestMapping(value="/administratorInformation.html")
	public String getAdministratorJsp(){
		return"administratorinformation";
	}
	@RequestMapping(value="/headTeacherInformation.html")
	public String getHeadTeacherInforMationJsp(){
		return "headteacherinformation";
	}
}
