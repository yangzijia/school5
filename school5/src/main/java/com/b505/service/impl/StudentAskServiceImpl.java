package com.b505.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.StudentAsk;
import com.b505.bean.Type;
import com.b505.bean.util.GradeId;
import com.b505.dao.IBaseDao;
import com.b505.dao.IStudentAskDao;
import com.b505.service.IStudentAskService;

@Service("IStudentAskService")
public class StudentAskServiceImpl extends BaseServiceImpl<StudentAsk> implements IStudentAskService{
	
	private IStudentAskDao iStudentAskDao;
	@Autowired
	@Qualifier("IStudentAskDao")
	public void setIBaseDao(IBaseDao<StudentAsk> iStudentAskDao) {
		// TODO Auto-generated method stub
		this.baseDao = iStudentAskDao;
		this.iStudentAskDao = (IStudentAskDao)iStudentAskDao;
	}
	

	@Override
	public List<StudentAsk> getSAskByGradeIdandStatus(int gradeId,String status){
		
		return iStudentAskDao.getSAskByGradeIdandStatus(gradeId, status);
	}
	
	@Override
	public int hasAskSuccess(String nickName,String teacherName){
		
			@SuppressWarnings("unused")
			int b = 0;
			StudentAsk sAsk=iStudentAskDao.getSAskBynickName(nickName);
			if(sAsk==null){
				return b = 2;
			}else if(!(sAsk.getHeadteacher().equals(teacherName))){						
				return b = 3;
			}else {
				return b = 1;
			}
	}
	
	@Override
	public GradeId getClassIdByNickname(String nickName){
		
		return iStudentAskDao.getClassIdByNickname(nickName);
	}
	
	@Override
	public List<Type> getType(){
		
		return iStudentAskDao.getType();
	}
	
	@Override
	public void deleteAskByBatch(List<Integer> list){
		
		iStudentAskDao.deleteAskByBatch(list);
	}
	
	@Override
	public void updateAskByBatch(List<Map<String, Object>> list){
		
		iStudentAskDao.updateAskByBatch(list);
	}
	@Override
	public StudentAsk getSAskBynickName(String nickName){
		return iStudentAskDao.getSAskBynickName(nickName);
	}
	@Override
	public StudentAsk getSAskBynickNameAndType(String nickName,String type){
		return iStudentAskDao.getSAskBynickNameAndType(nickName, type);
	}
}
