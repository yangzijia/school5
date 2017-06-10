package com.b505.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.College;
import com.b505.bean.Teacher;
import com.b505.bean.util.TeacherRegisterUtil;
import com.b505.bean.util.TeacherUtil;
import com.b505.dao.IBaseDao;
import com.b505.dao.ITeacherDao;
import com.b505.service.ITeacherService;

@Service("TeacherService")
public class TeacherServiceImpl extends BaseServiceImpl<Teacher> implements ITeacherService{
	private ITeacherDao iteacherDao;
	@Autowired
	@Qualifier("ITeacherDao")
	@Override
	public void setIBaseDao(IBaseDao<Teacher> iteacherDao) {
		// TODO Auto-generated method stub
		this.baseDao = iteacherDao;
		this.iteacherDao = (ITeacherDao)iteacherDao;
	}
	//注意延迟加载
		@Override
		public int hasRegisterSuccess(String teacherCardId,String nickName,College college){
			@SuppressWarnings("unused")
			int b = 0;
			Teacher teacher = iteacherDao.get("teacherCardId", teacherCardId);
			System.out.println(teacher);
			System.out.println(teacher.getUser().getNickName());
			System.out.println(teacher.getCollege());
				if(teacher!=null && teacher.getUser().getNickName().equals(nickName)&&teacher.getCollege().equals(college)){
					return b =1;
				}else{
					 return b = 0;
				}
			
			
		}
		@Override
		public int hasRegisterSuccess(String teacherCardId,String nickName){
			@SuppressWarnings("unused")
			int b = 0;
			Teacher teacher = iteacherDao.get("teacherCardId", teacherCardId);
			if(teacher!=null && teacher.getUser().getNickName().equals(nickName)){
				return b = 1;
			}else{
				return b = 0;
			}
				
		}
		@Override
		public Teacher getTeacherByNickname(String nickName){
			return iteacherDao.getTeacherByNickname(nickName);
		}
		@Override
		public List<Teacher> getTeacherListByCollege(String collegeName){
			return iteacherDao.getTeacherListByCollege(collegeName);
		}
		@Override
		public int deleteTeacherByPhone(String phone) {
			// TODO Auto-generated method stub
			int b = iteacherDao.deleteTeacherByPhone(phone);
			return b;
		}
		@Override
		public List<Teacher> getHeadTeacherListByCollege(String collegeName) {
			// TODO Auto-generated method stub
			return iteacherDao.getHeadTeacherListByCollege(collegeName);
		}
		@Override
		public List<Teacher> getHeadteacherList() {
			// TODO Auto-generated method stub
			return iteacherDao.getHeadteacherList();
		}
		@Override
		public List<TeacherUtil> getTeacherUtilByRole() {
			// TODO Auto-generated method stub
			List<TeacherUtil> list = new ArrayList<TeacherUtil>();
			list = iteacherDao.getTeacherUtilByRole();
			return list;
		}
		@Override
		public void deleteTeacherByBatch(List<Integer> list) {
			// TODO Auto-generated method stub
			iteacherDao.deleteTeacherByBatch(list);
		}
		@Override
		public void updateTeacherByBatch(List<Map<String, Object>> list) {
			// TODO Auto-generated method stub
			iteacherDao.updateTeacherByBatch(list);
		}
		@Override
		public List<TeacherUtil> getHeadTeacherUtilByRole(String collegeName) {
			// TODO Auto-generated method stub
			List<TeacherUtil> list = iteacherDao.getHeadTeacherUtilByRole(collegeName);
			return list;
		}
		@Override
		public TeacherRegisterUtil getTeacherRegisterUtilByNickName(
				String nickName) {
			// TODO Auto-generated method stub
			return iteacherDao.getTeacherRegisterUtilByNickName(nickName);
		}
		@Override
		public Teacher getHeadTeacherRegisterUtilByNickName(
				String nickName) {
			// TODO Auto-generated method stub
			return iteacherDao.getHeadTeacherRegisterUtilByNickName(nickName);
		}
		@Override
		public boolean updatePhoneByNickName(String phone, String nickName) {
			// TODO Auto-generated method stub
			return iteacherDao.updatePhoneByNickName(phone, nickName);
		}
		@Override
		public String getTeacherNameByNickName(String nickname) {
			// TODO Auto-generated method stub
			return iteacherDao.getTeacherNameByNickName(nickname);
		}
		@Override
		public Map<String, Object> getTeaNameByTid(int tid){
			
			return iteacherDao.getTeaNameByTid(tid);
		}
		
		@Override
		public Teacher getTeacherByTnaem(String teachername){
			
			return iteacherDao.getTeacherByTnaem(teachername);
		}
		
		@Override
		public String getTeaNameByTid1(int tid){
			
			return iteacherDao.getTeaNameByTid1(tid);
		}
}
