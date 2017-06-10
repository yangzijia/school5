package com.b505.service;

import com.b505.bean.Course;

public interface ICourseService extends IBaseService<Course> {

	public Course getCourseByClassId(int id);
}
