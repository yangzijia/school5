package com.b505.dao;

import java.util.List;

import com.b505.bean.Dormcallover;

public interface IDormcalloverDao extends IBaseDao<Dormcallover>{

	public void saveDormcalloverByBatch(List<Dormcallover> list);
	public List<Dormcallover> getDormcalloversByGradeAndDate(int gradeId,String date);
	public List<Dormcallover> getDormcalloversByGrade(int gradeId);
	public void delDormcalloverByBatch(List<Integer> list);
}
