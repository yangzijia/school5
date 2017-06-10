package com.b505.service;

import java.util.List;
import java.util.Map;

import com.b505.bean.College;
import com.b505.bean.Teacher;
import com.b505.bean.util.TeacherRegisterUtil;
import com.b505.bean.util.TeacherUtil;
import com.b505.dao.IBaseDao;

public interface ITeacherService extends IBaseService<Teacher> {
	public void setIBaseDao(IBaseDao<Teacher> iteacherDao);
	public int hasRegisterSuccess(String teacherCardId,String nickName,College college);
	public int hasRegisterSuccess(String teacherCardId,String nickName);
	public Teacher getTeacherByNickname(String nickName);
	public List<Teacher> getTeacherListByCollege(String collegeName);
	public int deleteTeacherByPhone(String phone);
	public List<Teacher> getHeadTeacherListByCollege(String collegeName);
	public List<Teacher> getHeadteacherList();
	public boolean updatePhoneByNickName(String phone, String nickName);
	public List<TeacherUtil> getTeacherUtilByRole();
	public void deleteTeacherByBatch(List<Integer> list);
	public void updateTeacherByBatch(List<Map<String, Object>> list);
	public List<TeacherUtil> getHeadTeacherUtilByRole(String collegeName);
	public TeacherRegisterUtil getTeacherRegisterUtilByNickName(String nickName);
	public Teacher getHeadTeacherRegisterUtilByNickName(
			String nickName);
	public String getTeacherNameByNickName(String nickname);
	public Map<String, Object> getTeaNameByTid(int tid);
	public Teacher getTeacherByTnaem(String teachername);
	
	public String getTeaNameByTid1(int tid);
}
