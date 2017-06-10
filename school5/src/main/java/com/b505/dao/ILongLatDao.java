package com.b505.dao;
import java.util.List;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatType;
import com.b505.bean.util.HteacherLongLatUtil;
import com.b505.bean.util.LongLatUtil;


public interface ILongLatDao extends IBaseDao<LongLat>{
	public List <LongLat> getLongLatByStudentId(String id);
	public Long getLongLatLengthByGrade(String yearClass,String profession, String classId );
	public List<LongLatUtil> getLongLatUtilByGrade(int gradeId);
	public List<LongLat> getLongLatClientUtilByGrade(int gradeId);
	public List<LongLatUtil> getAllLongLatUtil();
	public List<HteacherLongLatUtil> getHteacherAllLongLatUtil();
	public List<LongLatType> getAllLatTypes();
	public List<LongLatUtil> getLongLatUtilByStudentAdmin();
	public List<LongLatUtil> getLongLatUtilByCollegeAdmin();
	public List<LongLatUtil> getLongLatUtilByLogisticsAdmin();
	public List<LongLatUtil> getLongLatUtilByDormAdmin();
	public List<LongLat> getLongLatClientUtilByStudentAdmin();
	public List<LongLat> getLongLatClientUtilByCollegeAdmin();
	public List<LongLat> getLongLatClientUtilByLogisticsAdmin();
	public List<LongLat> getLongLatClientUtilByDormAdmin();
	public List<LongLatUtil> getLongLatUtilByLongLattype(String longlattype);
	public List<LongLatUtil> getTeacherLongLatUtilByLongLattype(String longlattype);
	public List<LongLatUtil> getLongLatUtilByDate(String date1,String date2);
	public List<LongLatUtil> getTeacherLongLatUtilByDate(String date1,String date2);
	
	
	public List<LongLat> getAllLongLat();
	
}