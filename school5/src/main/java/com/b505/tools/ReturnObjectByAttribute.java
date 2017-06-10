package com.b505.tools;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Administrator;
import com.b505.bean.College;
import com.b505.bean.Grade;
import com.b505.bean.Leader;
import com.b505.bean.News;
import com.b505.bean.NewsImage;
import com.b505.bean.RoleName;
import com.b505.bean.Section;
import com.b505.bean.Student;
import com.b505.bean.Teacher;
import com.b505.bean.LoginUser;

@Component
public class ReturnObjectByAttribute
{
	@Autowired
	private SSHA sSHA;
	@Autowired
	private DigestUtils du;
	
	/*
	 * @方法名：getLeaderIDMap(String name, String phone)
	 * @功能：构造Leader的主键信息的Map集合
	 * @功能说明：构造Leader的主键信息的Map集合
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public Object getLeaderIDMap(String name, String phone)
	{
		Map<String,Object> idMap = new HashMap<String,Object>();
		idMap.put("name", name);
		idMap.put("phone", phone);
		return idMap;
	}
	
	public Leader returnLeader(String name,String phone,LoginUser user)
	{
		Leader leader = new Leader();
		leader.setName(name);
		leader.setPhone(phone);
		leader.setUser(user);
		return leader;
	}
	
	/*
	 * @方法名：returnUser(String nickname, String password)
	 * @功能：根据输入值实例化一个User
	 * @功能说明：根据输入值实例化一个User
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public LoginUser returnUser(String nickname, String password, String role, String status)throws Exception
	{
		LoginUser user = new LoginUser();
		user.setNickName(nickname);
		String pd =  sSHA.digest(password);
		user.setPassword(pd);
		user.setRole(role);
		user.setStatus(status);
		return user;
	}
	public LoginUser returnUser(String nickname, String password, String role) throws Exception
	{
		return this.returnUser(nickname,password, role, null);
	}
	public LoginUser returnUser(String nickname, String password)throws Exception
	{
		return this.returnUser(nickname, password, null);
	}
	public LoginUser returnUser(String nickname)throws Exception
	{
		return this.returnUser(nickname, null,null);
	}
	
	/*
	 * @方法名：returnAdmin(String name, String phone, User user)
	 * @功能：返回管理员
	 * @功能说明：返回管理员
	 * @作者：李振强
	 * @创建时间：2014.5.31
	 * @修改时间：2014.5.31
	 */
	public Administrator returnAdmin(Integer id,String name, String phone, LoginUser user) throws Exception
	{
		Administrator admin = new Administrator();
		admin.setId(id);
		admin.setName(name);
		admin.setPhone(phone);
		admin.setUser(user);
		return admin;
	}
	public Administrator returnAdmin(String name, String phone, LoginUser user) throws Exception
	{
		return this.returnAdmin(null, name, phone, user);
	}
	
	public Administrator returnAdmin(Integer id, String phone, LoginUser user) throws Exception
	{
		return this.returnAdmin(id, null, phone, user);
	}
	
	/*
	 * @方法名：setUser(String nickname)
	 * @功能：注册领导的user信息
	 * @功能说明：注册领导的user信息
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public LoginUser setLeaderOfUser(String nickname) throws Exception
	{
		List<RoleName> list = new ArrayList<RoleName>();
		RoleName rName = new RoleName();
		rName.setId(2);
		list.add(rName);
		LoginUser user = new LoginUser();
		user.setNickName(nickname);
		String password = du.digest("123456");
		user.setPassword(password);
		user.setRoles(list);
		user.setRole("Role_Leader");
		return user;
	}
	/*
	 * @方法名： returnStudent(String studentName, String studentPhone, String studentCardID, College college, Grade grade,User user)
	 * @功能：根据输入值实例化一个Student
	 * @功能说明：根据输入值实例化一个Student
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public Student returnStudent(String studentName, String studentPhone, String studentCardID, College college, Grade grade,LoginUser user)
	{
		Student student = new Student();
		student.setCollege(college);
		student.setStudentCardId(studentCardID);
		student.setStudentClass(grade);
		student.setStudentName(studentName);
		student.setStudentPhone(studentPhone);
		student.setUser(user);
		return student;
	}
	public Student returnStudent(String studentName,String studentPhone,String studnetCardID,College college, Grade grade)
	{
		return this.returnStudent(studentName, studentPhone, studnetCardID,college,grade,null);
	}
	public Student returnStudent(String studentName,String studentPhone,String studnetCardID,LoginUser user)
	{
		return this.returnStudent(studentName, studentPhone, studnetCardID,null,null,user);
	}
	public Student returnStudent(String studentName,String studentPhone,String studnetCardID)
	{
		return this.returnStudent(studentName, studentPhone, studnetCardID,null,null,null);
	}
	public Student returnStudent(String studnetCardID)
	{
		return this.returnStudent(null, null, studnetCardID,null,null,null);
	}
	
	/*
	 * @方法名：returnCollege(String collegeName)
	 * @功能：根据输入值实例化一个Student
	 * @功能说明：根据输入值实例化一个Student
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public College returnCollege(String collegeName)
	{
		College college = new College();
		college.setCollegeName(collegeName);
		college.setCollegeid(1);
		return college;
	}
	
	/*
	 * @方法名：returnGrade(String collegeName)
	 * @功能：根据输入值实例化一个Student
	 * @功能说明：根据输入值实例化一个Student
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public Grade returnGrade(String gradeName,String profession,String classID,College college)
	{
		Grade grade = new Grade();
		grade.setYearClass(gradeName);
		grade.setProfession(profession);
		grade.setClassId(classID);
		grade.setCollege(college);
		return grade;
	}
	
	/*
	 * @方法名：returnTeacher(String collegeName)
	 * @功能：根据输入值实例化一个Student
	 * @功能说明：根据输入值实例化一个Student
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public Teacher returnTeacher(Integer id,String teacherName,String teacherPhone,String teacherCardID,College college,List<Grade> teacherClass,LoginUser user )
	{
		Teacher teacher = new Teacher();
		teacher.setId(2);
		teacher.setTeacherName(teacherName);
		teacher.setTeacherPhone(teacherPhone);
		teacher.setTeacherCardId(teacherCardID);
		teacher.setCollege(college);
		teacher.setTeacherClass(teacherClass);
		teacher.setUser(user);
		teacher.setId(id);
		return teacher;
	}
	public Teacher returnTeacher(String teacherName,String teacherPhone,String teacherCardID,LoginUser user)
	{
		return this.returnTeacher(null,teacherName, teacherPhone, teacherCardID, null, null,user);
	}
	public Teacher returnTeacher(String teacherName,String teacherPhone,LoginUser user)
	{
		return this.returnTeacher(null,teacherName, teacherPhone, null, null, null,user);
	}
	
	/*
	 * @方法名：returnNews(String collegeName)
	 * @功能：根据输入值实例化一个News
	 * @功能说明：根据输入值实例化一个News
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.17
	 */
	public News returnNews(Integer id, String theme, String author, String date,String url, String content, String checker, String section)throws Exception
	{
		News news = new News();
		news.setId(id);
		news.setTheme(theme);
		news.setAuthor(author);
		news.setUrl(url);
		news.setContent(content);
		news.setChecker(checker);
		Section sec = new Section();
		sec.setSectionName(section);
		news.setSection(sec);
		SimpleDateFormat format = new SimpleDateFormat();
		Date reDare = format.parse(date);
		news.setReDate(reDare);
		return news;		
	}
	
	public News returnNews(Integer id, String theme, String author, String url, String content, String checker, Section section)throws Exception
	{
		News news = new News();
		news.setId(id);
		news.setTheme(theme);
		news.setAuthor(author);
		news.setUrl(url);
		news.setContent(content);
		news.setChecker(checker);
		news.setSection(section);
		return news;		
	}
	
	public NewsImage returnNewsImage(Blob image, String imgName, String imgURL, News news)
	{
		NewsImage newsImage = new NewsImage();
		newsImage.setImage(image);
		newsImage.setImgName(imgName);
		newsImage.setImgUrl(imgURL);
		newsImage.setNews(news);
		return newsImage;
	}
	public NewsImage returnNewsImage(String imgURL, News news)
	{
		return this.returnNewsImage(null, null, imgURL, news);
	}
	/**
	 * @author 少游
	 * @time:2016.10.28
	 * @param theme
	 * @param author
	 * @param url
	 * @param content
	 * @param checker
	 * @param section
	 * @return
	 * @throws Exception
	 */
	public News returnNews(String theme, String author, String url, String content, String checker, Section section)throws Exception
	{
		News news = new News();
		
		news.setTheme(theme);
		news.setAuthor(author);
		news.setUrl(url);
		news.setContent(content);
		news.setChecker(checker);
		news.setSection(section);
		return news;		
	}
}
