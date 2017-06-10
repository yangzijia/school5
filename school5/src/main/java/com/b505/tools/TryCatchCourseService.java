package com.b505.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Course;
import com.b505.service.ICourseService;

@Component
public class TryCatchCourseService {

	@Autowired
	private ICourseService courseService;
	
	/*
	 * @方法名：getCourseByClassId(int id)
	 * @功能：由班级id获取该班级的课表
	 * @功能说明：获取班级课表信息
	 * @创建时间：2015.10.07
	 */
	public Course getCourseByClassId(int id){
		
		Course course = null;
		try {
			course = courseService.getCourseByClassId(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return  course = null;
		}
		return course;
	}
}
