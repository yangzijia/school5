package com.b505.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.Roll_Alert;
import com.b505.bean.Student;
import com.b505.bean.StudentAsk;
import com.b505.bean.util.GradeUtil;
import com.b505.bean.util.StudentGid;
import com.b505.bean.util.StudentRegisterUtil;
import com.b505.bean.util.StudentUtil;
import com.b505.dao.IBaseDao;
import com.b505.dao.IStudentDao;
import com.b505.service.IStudentService;
@Service("StudentService")
public class StudentServiceImpl extends BaseServiceImpl<Student> implements IStudentService{
	private IStudentDao istudentDao;
	@Autowired
	@Qualifier("IStudentDao")
	@Override
	public void setIBaseDao(IBaseDao<Student> istudentDao) {
		// TODO Auto-generated method stub
		this.baseDao = istudentDao;
		this.istudentDao = (IStudentDao)istudentDao;
	}
	@Override
	public int hasRegisterSuccess(String studentId,String userNickname){
		int b = 0;
		Student student = istudentDao.get(studentId);
		System.out.println("StudentServiceImpl_hasRegisterSuccess_得到的student为："+student);
		if(student!=null && userNickname.equals(student.getUser().getNickName())){
			return b = 1;
		}else {
			return b;
		}
	}
	@Override
	public Student getStudentByNickname(String userNickname){
		
		return 	istudentDao.getStudentByNickname(userNickname);
	}
	@Override
	public List<Student>getStudentByGrade(Integer gradeId){
		return istudentDao.getStudentByGrade(gradeId);
	}
	@Override
	public List<Student> getStudentByGradeAll(String yearClass,String profession,String classId){
		return istudentDao.getStudentByGradeAll(yearClass, profession, classId);
	}
	@Override
	public int deleteById(Student student) {
		this.delete(student);
		 int b = 0;
		 if(this.get(student.getId())==null){
			 b=1;
		 }
		return b;
	}
	@Override
	public void deleteStudentByBatch(List<String> list){
		istudentDao.deleteStudentByBatch(list);
	}
	@Override
	public void   updateStudentByBatch(List<Student> list){
		istudentDao.updateStudentByBatch(list);
	}
	@Override
	public List<StudentUtil> getStudentUtilsByGrade(String yearClass,String profession,String classId){
		// TODO Auto-generated method stub
		return istudentDao.getStudentUtilsByGrade(yearClass,profession,classId);
	}
	@Override
	public List<StudentUtil> getStudentUtilUnregistered() {
		// TODO Auto-generated method stub
		
		return istudentDao.getStudentUtilUnregistered();
	}
	@Override
	public StudentRegisterUtil getStudentRegisterInforByNickName(
			String nickName) {
		// TODO Auto-generated method stub
		return istudentDao.getStudentRegisterInforByNickName(nickName);
	}
	@Override
	public boolean updatePhoneByNickName(String phone, String nickName) {
		// TODO Auto-generated method stub
		return istudentDao.updatePhoneByNickName(phone, nickName);
	}
	@Override
	public GradeUtil getGradeUtilByStudentNumber(String studentNumber) {
		// TODO Auto-generated method stub
		return istudentDao.getGradeUtilByStudentNumber(studentNumber);
	}
	@Override
	public StudentGid getGidByNumber(String number){
		
		return istudentDao.getGidByNumber(number);
	}
	@Override
	public List<StudentAsk> getStudentAskBynickNameAndtype(String nickName,String type){
		
		return istudentDao.getStudentAskBynickNameAndtype(nickName,type);
	}
	
	/*@Override
	public StudentAsk getstudentAskDetailBynickName(String nickName){
		
		return istudentDao.getstudentAskDetailBynickName(nickName);
	}*/
	
	@Override
	public String getstudentNameBynickName(String nickName){
		
		return istudentDao.getstudentNameBynickName(nickName);
	}
	
	@Override
	public StudentAsk getstudentAskDetailByaskId(int askid){
		
		return istudentDao.getstudentAskDetailByaskId(askid);
	}
	@Override
	public List<Roll_Alert> getAlertByNickname(String nickName)
	{
		// TODO Auto-generated method stub
		return istudentDao.getAlertByNickname(nickName);
	}
	@Override
	public GradeUtil getGradeBystudentNumber(String studentNumber)
	{
		// TODO Auto-generated method stub
		return istudentDao.getGradeBystudentNumber(studentNumber);
	}
	@Override
	public Roll_Alert getRoll_AlertBystudentNumber(String studentNumber,
			String studentName)
	{
		// TODO Auto-generated method stub
		return istudentDao.getRoll_AlertBystudentNumber(studentNumber,studentName);
	}
}
