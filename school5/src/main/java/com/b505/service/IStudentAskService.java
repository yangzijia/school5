package com.b505.service;

import java.util.List;
import java.util.Map;

import com.b505.bean.StudentAsk;
import com.b505.bean.Type;
import com.b505.bean.util.GradeId;

public interface IStudentAskService extends IBaseService<StudentAsk>{

	 public List<StudentAsk> getSAskByGradeIdandStatus(int gradeId,String status);
	 public int hasAskSuccess(String nickName,String teacherName);  
	 public GradeId getClassIdByNickname(String nickName);
	 public List<Type> getType();
	 public void deleteAskByBatch(List<Integer> list);
	 public void updateAskByBatch(List<Map<String, Object>> list);
	 public StudentAsk getSAskBynickName(String nickName);
	 public StudentAsk getSAskBynickNameAndType(String nickName,String type);
}
