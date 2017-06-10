package com.b505.service;

import java.util.List;

import com.b505.bean.Roll_Alert;
import com.b505.bean.Student;
import com.b505.bean.StudentAsk;
import com.b505.bean.util.GradeUtil;
import com.b505.bean.util.StudentGid;
import com.b505.bean.util.StudentRegisterUtil;
import com.b505.bean.util.StudentUtil;

public interface IStudentService extends IBaseService<Student> {
	public int hasRegisterSuccess(String studentId,String userNickname);
	public Student getStudentByNickname(String userNickname);
	public List<Student>getStudentByGrade(Integer gradeId);
	public List<Student> getStudentByGradeAll(String yearClass,String profession,String classId);
	public int deleteById(Student student);
	public void deleteStudentByBatch(List<String> list);
	public void   updateStudentByBatch(List<Student> list);
	public List<StudentUtil> getStudentUtilsByGrade(String yearClass,String profession,String classId);
	public List<StudentUtil> getStudentUtilUnregistered() ;
	public StudentRegisterUtil getStudentRegisterInforByNickName(String nickName);
	public boolean updatePhoneByNickName(String phone, String nickName);
	public GradeUtil getGradeUtilByStudentNumber(String studentNumber);
	public StudentGid getGidByNumber(String number);
	public List<StudentAsk> getStudentAskBynickNameAndtype(String nickName,String type);
	//public StudentAsk getstudentAskDetailBynickName(String nickName);
	public String getstudentNameBynickName(String nickName);
	public StudentAsk getstudentAskDetailByaskId(int askid);
	public List<Roll_Alert> getAlertByNickname(String nickName);
	public GradeUtil getGradeBystudentNumber(String studentNumber);
	public Roll_Alert getRoll_AlertBystudentNumber(String studentNumber,String studentName);
}
