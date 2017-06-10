package com.b505.dao;

import com.b505.bean.TeacherClass;
import com.b505.bean.util.TeacherId;

public interface ITeacherClassDao extends IBaseDao<TeacherClass> {
	
	public TeacherId getTidByS_C_ID(int gradeId);
}
