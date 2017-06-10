package com.b505.service;

import java.util.List;
import java.util.Map;

import com.b505.bean.College;

public interface ICollegeService extends IBaseService<College> {
	public String[] getAllCollegeName();
	public String getCollege(Integer id);
	public void saveCollegeByBatch(List<College> list);
	public void deleteCollegeByBatch(List<Integer> list);
	public void updateCollegeByBatch(List<Map<String, Object>> list);
	public int getCollegeIdByGradeId(int gradeId);
}
