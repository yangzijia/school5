
package com.b505.dao;

import java.util.List;
import java.util.Map;

import com.b505.bean.Teacher;
import com.b505.bean.util.TeacherRegisterUtil;
import com.b505.bean.util.TeacherUtil;

public interface ITeacherDao extends IBaseDao<Teacher>{
	public Teacher getTeacherByNickname(String nickName);
	public List<Teacher> getTeacherListByCollege(String collegeName);
	public int deleteTeacherByPhone(String phone);
	public List<Teacher> getHeadTeacherListByCollege(String collegeName);
	public List<Teacher> getHeadteacherList();
	public List<TeacherUtil> getTeacherUtilByRole();
	public List<TeacherUtil> getHeadTeacherUtilByRole(String collegeName);
	public void deleteTeacherByBatch(List<Integer> list);
	public void updateTeacherByBatch(List<Map<String, Object>> list);
	public TeacherRegisterUtil getTeacherRegisterUtilByNickName(String nickName);
	public Teacher getHeadTeacherRegisterUtilByNickName(
			String nickName);
	public boolean updatePhoneByNickName(String phone,String nickName);
	public String getTeacherNameByNickName(String nickname);
	public Map<String, Object> getTeaNameByTid(int tid);
	public Teacher getTeacherByTnaem(String teachername);
	
	public String getTeaNameByTid1(int tid);
	 
}
