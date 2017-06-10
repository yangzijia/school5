package com.b505.service;

import com.b505.bean.TeacherClass;
import com.b505.bean.util.TeacherId;

public interface ITeacherClassService extends IBaseService<TeacherClass> {

	 public TeacherId getTidByS_C_ID(int gradeId);
}
