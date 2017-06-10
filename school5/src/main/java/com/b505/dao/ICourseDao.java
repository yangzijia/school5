package com.b505.dao;

import com.b505.bean.Course;

public interface ICourseDao extends IBaseDao<Course> {

	public Course getCourseByClassId(int id);
}
