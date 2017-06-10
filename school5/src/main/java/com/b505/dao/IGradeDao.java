package com.b505.dao;

import java.util.List;
import java.util.Map;

import com.b505.bean.Grade;
import com.b505.bean.util.GradeUtil;

public interface IGradeDao extends IBaseDao<Grade>{
	public List<GradeUtil> getGradeByCollege(String collegeName);
	public String[] getyearClass();
	public String[] getProfesssionByCY(String collegeName, String yearClass);
	public String[] getClassIdByCYP(String collegeName, String yearClass,String profession);
	public Grade getByProperty(String yearClass, String profession ,String classId);
	public void saveGradeByBatch(List<Grade> list);
	public void updateGradeByBatch(List<Map<String, Object>> list);
	public void deleteGradeByBatch(List<Map<String, Object>> list);
	public List<GradeUtil> getGradeUtilsByHeadTeacherNickName(String nickName);
	public void updateGradeUtilsByHeadTeacherNickName(List<GradeUtil> list);
	public int getGradeIdByGrade(String yearClass,String profession,String classId);
	
}
