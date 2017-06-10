package com.b505.service;

import java.util.List;

import com.b505.bean.Dormcallover;

public interface IDormcalloverService extends IBaseService<Dormcallover>{

	public void saveDormcalloverByBatch(List<Dormcallover> list);
	public List<Dormcallover> getDormcalloversByGradeAndDate(int gradeId,String date);
	public List<Dormcallover> getDormcalloversByGrade(int gradeId);
	public void delDormcalloverByBatch(List<Integer> list);
}
