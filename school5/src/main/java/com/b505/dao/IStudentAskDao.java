package com.b505.dao;

import java.util.List;
import java.util.Map;

import com.b505.bean.StudentAsk;
import com.b505.bean.Type;
import com.b505.bean.util.GradeId;

public interface IStudentAskDao extends IBaseDao<StudentAsk> {

	public StudentAsk getSAskBynickName(String nickName);  //辅助用于验证学生请假是否成功
	public List<StudentAsk> getSAskByGradeIdandStatus(int gradeId,String status);
	public GradeId getClassIdByNickname(String nickName);
	public List<Type> getType();
	public void deleteAskByBatch(List<Integer> list);
	public void updateAskByBatch(List<Map<String, Object>> list);
	public StudentAsk getSAskBynickNameAndType(String nickName,String type);
}
