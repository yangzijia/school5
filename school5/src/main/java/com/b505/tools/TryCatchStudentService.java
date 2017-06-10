package com.b505.tools;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Roll_Alert;
import com.b505.bean.Student;
import com.b505.bean.StudentAsk;
import com.b505.bean.util.StudentGid;
import com.b505.service.IStudentService;

@Component
public class TryCatchStudentService 
{
	@Autowired
	private IStudentService studentService;
	
	/*
	 * @方法名： getStudentByNickname(String nickname) 
	 * @功能：根据昵称得到student
	 * @功能说明：根据昵称得到student，如果出错，就置student为Null
	 * @作者：李振强
	 * @创建时间：2014.5.15
	 * @修改时间：2014.5.15
	 */
	public Student getStudentByNickname(String nickname) throws Exception
	{
		Student student;
		try
		{
			student = studentService.getStudentByNickname(nickname);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			student = null;
		}
		return student;
	}
	
	public Student getStudentByStudentID(Serializable studentID)
	{
		Student student;
		try
		{
			student = studentService.get(studentID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return student = null;
		}
		return student;
	}
	
	public List<Student> getStudentListByGradeID(Integer gradeID)
	{
		List<Student> studentList;
		try
		{
			studentList = studentService.getStudentByGrade(gradeID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			studentList = null;
		}
		return studentList;
	}
	
	public boolean saveStudent(Student student)
	{
		try
		{
			studentService.save(student);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*
	 * @方法名：update(Student student)
	 * @功能：update student
	 * @功能说明：update student
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public boolean update(Student student)
	{
		try
		{
			studentService.update(student);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updatePhoneByNickName(String phone, String nickName)
	{
		boolean status = false;
		try
		{
			status = studentService.updatePhoneByNickName(phone, nickName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status;
	}
	
	
	/*
	 * @方法名：deleted(Student student)
	 * @功能：deleted student
	 * @功能说明：deleted student
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public boolean deletedStudent(Student student)
	{
		try
		{
			studentService.delete(student);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public StudentGid getGidByNumber(String number)
	{
		StudentGid sGid;
		
		try{
			
			sGid = studentService.getGidByNumber(number);
		}catch(Exception e)
		{
			e.printStackTrace();
			sGid = null;
		}
		return sGid;
		
	}
	
	/*
	 * @方法名：getStudentAskBynickName(String nickName)
	 * @功能：由学生昵称得到学生的请假信息列表
	 * @功能说明：由学生昵称得到学生的请假信息列表
	 */
	public List<StudentAsk> getStudentAskBynickNameAndtype(String nickName,String type){
		
		List<StudentAsk> sAsksList;
		try
		{
			sAsksList = studentService.getStudentAskBynickNameAndtype(nickName,type);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			sAsksList = null;
		}
		return sAsksList;
	}
	
	/*
	 * @方法名：getstudentAskDetailBynickName(String nickName)
	 * @功能：学生查看自己请假信息详情
	 * @功能说明：学生查看自己请假信息详情
	 */
	/*public StudentAsk getstudentAskDetailBynickName(String nickName){
		
		StudentAsk sAsk;
		try
		{
			sAsk = studentService.getstudentAskDetailBynickName(nickName);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			sAsk = null;
		}
		return sAsk;
	}*/
	
	/*
	 * @方法名：getstudentNameBynickName(String nickName)
	 * @功能：由学生昵称得到学生姓名
	 * @功能说明：由学生昵称得到学生姓名
	 */
	public String getstudentNameBynickName(String nickName){
		
		String studentName=null;
		try {
			studentName=studentService.getstudentNameBynickName(nickName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return studentName;
	}
	
	/*
	 * @方法名：getstudentAskDetailByaskId(String nickName)
	 * @功能：教师查看学生请假信息详情
	 * @功能说明：教师查看学生请假信息详情
	 */
	public StudentAsk getstudentAskDetailByaskId(int askid){
		
		StudentAsk sAsk;
		try
		{
			sAsk = studentService.getstudentAskDetailByaskId(askid);
             
		}
		catch(Exception e)
		{
			e.printStackTrace();
			sAsk = null;
		}
		return sAsk;
	}
	
	
	/*
	 * @方法名： getAlertByNickname(String nickname) 
	 * @功能：根据昵称得到预警成绩
	 * @功能说明：根据昵称得到预警成绩，如果出错，就置roll_Alert为Null
	 * @作者：JSY
	 * @创建时间：2016.5.20
	 * 
	 */
	public List<Roll_Alert>  getAlertByNickname(String nickName) throws Exception
	{
		List<Roll_Alert> roll_Alert;
		try
		{
			roll_Alert = studentService.getAlertByNickname(nickName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			roll_Alert = null;
		}
		return roll_Alert;
	}

	
}

