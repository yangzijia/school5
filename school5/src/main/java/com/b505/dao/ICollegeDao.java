package com.b505.dao;

import java.util.List;
import java.util.Map;

import com.b505.bean.College;

public interface ICollegeDao extends IBaseDao<College>{
	public String getCollege(Integer id);
	public String[] getAllCollegeName();
	public void saveCollegeByBatch(List<College> list);
	public void deleteCollegeByBatch(List<Integer> list);
	public void updateCollegeByBatch(List<Map<String, Object>> list);
	//学生求救时，用于获取领导所属学院
	public int getCollegeIdByGradeId(int gradeId);
}
