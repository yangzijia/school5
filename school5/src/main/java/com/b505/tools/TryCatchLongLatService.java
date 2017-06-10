package com.b505.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.LongLat;
import com.b505.bean.LongLatType;
import com.b505.bean.util.HteacherLongLatUtil;
import com.b505.bean.util.LongLatUtil;
import com.b505.service.ILongLatService;

@Component
public class TryCatchLongLatService
{
	@Autowired
	private ILongLatService longLatService;
	
	public long getLongLatLengthByGrade(String yearClass, String profession, String classID)
	{
		long longLatLength;
		try
		{
			longLatLength = longLatService.getLongLatLengthByGrade(yearClass,profession,classID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return longLatLength = 0;
		}
		return longLatLength;
	}
	
	public List<LongLat> getLongLatByStudentID(String studentID)
	{
		List <LongLat> longLatList;
		try
		{
			longLatList = longLatService.getLongLatByStudentId(studentID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return longLatList = null;
		}
		return longLatList;
	}
	
	/*
	 * @方法名： longLatSaveOrUpdate(Object obj)
	 * @功能：地图信息存入数据库中
	 * @功能说明：地图信息存入数据库中，如果出错，就返回false，反之，返回true 。
	 * @作者：李振强
	 * @创建时间：2014.5.15
	 * @修改时间：2014.5.15
	 */
	public boolean longLatSaveOrUpdate(LongLat longLat)
	{
		try
		{
			longLatService.saveOrUpdate(longLat);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;		
	}
	
	public List<LongLatUtil> getLongLatUtilByGradeId(int gradeId){
		List<LongLatUtil> longLatUtilList;
		try {
			longLatUtilList = longLatService.getLongLatUtilByGrade(gradeId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return longLatUtilList = null;
		}
		return longLatUtilList;
	}
	public List<LongLat> getLongLatByGrade(int gradeId){
		List<LongLat> LongLatList;
		try {
			LongLatList = longLatService.getLongLatClientUtilByGrade(gradeId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return LongLatList = null;
		}
		return LongLatList;
	}
	public LongLat getLongLatByLatById(Long mapId){
		LongLat longLat = new LongLat();
		try {
			longLat = longLatService.get(mapId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return longLat = null;
		}
		return longLat;
	}
	public boolean deletedLongLatByID(LongLat MapId){
		boolean  b = false;
		try {
			int c = longLatService.deletedLongLatByID(MapId);
			if(c==1){
				b=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return b;
		}
		return b;
	}
	
	/**
	 * @方法名：getAllLongLatUtil()
	 * @功能：领导查询紧急情况详情时，初始化加载数据
	 * @作者：lyf
	 * @创建时间：2015.11.11
	 */
	public List<LongLatUtil> getAllLongLatUtil(){
		List<LongLatUtil> longLatUtilList;
		try {
			longLatUtilList = longLatService.getAllLongLatUtil();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return longLatUtilList = null;
		}
		return longLatUtilList;
	}
	
	/**
	 * @方法名：getHteacherAllLongLatUtil()
	 * @功能：领导查询紧急情况详情时，初始化加载数据,加载教师预警信息
	 * @作者：lyf
	 * @创建时间：2015.11.27
	 */
	public List<HteacherLongLatUtil> getHteacherAllLongLatUtil(){
		List<HteacherLongLatUtil> longLatUtilList;
		try {
			longLatUtilList = longLatService.getHteacherAllLongLatUtil();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return longLatUtilList = null;
		}
		return longLatUtilList;
	}
	
	
	/**
	 * @方法名：getAllLatTypes()
	 * @功能：给客户端返回预警类型
	 * @作者：lyf
	 * @创建时间：2015.11.18
	 */
	public List<LongLatType> getAllLatTypes(){
		
		List<LongLatType> list;
		try{
			list = longLatService.getAllLatTypes();
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}
		return list;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function 服务端学生科获取预警消息
	 */
	public List<LongLatUtil> getLongLatUtilByStudentAdmin(){
		List<LongLatUtil> longLatUtilList;
		try {
			longLatUtilList = longLatService.getLongLatUtilByStudentAdmin();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return longLatUtilList = null;
		}
		return longLatUtilList;
	}	
	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function 服务端后勤集团获取预警消息
	 */
	public List<LongLatUtil> getLongLatUtilByLogisticsAdmin(){
		List<LongLatUtil> longLatUtilList;
		try {
			longLatUtilList = longLatService.getLongLatUtilByLogisticsAdmin();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return longLatUtilList = null;
		}
		return longLatUtilList;
	}	
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function 服务端宿管科获取预警消息
	 */
	public List<LongLatUtil> getLongLatUtilByDormAdmin(){
		List<LongLatUtil> longLatUtilList;
		try {
			longLatUtilList = longLatService.getLongLatUtilByDormAdmin();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return longLatUtilList = null;
		}
		return longLatUtilList;
	}	
	
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 客户端学生科获取预警信息
	 */
	public List<LongLat> getLongLatClientUtilByStudentAdmin(){
		List<LongLat> list;
		try{
			list = longLatService.getLongLatClientUtilByStudentAdmin();
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}
		return list;
	}
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 客户端学院教学科获取预警信息
	 */
	public List<LongLat> getLongLatClientUtilByCollegeAdmin(){
		List<LongLat> list;
		try{
			list = longLatService.getLongLatClientUtilByCollegeAdmin();
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}
		return list;
	}
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 客户端后勤集团获取预警信息
	 */
	public List<LongLat> getLongLatClientUtilByLogisticsAdmin(){
		List<LongLat> list;
		try{
			list = longLatService.getLongLatClientUtilByLogisticsAdmin();
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}
		return list;
	}
	/**
	 * @author lyf
	 * @time 2015-11-23
	 * @function 客户端宿管科获取预警信息
	 */
	public List<LongLat> getLongLatClientUtilByDormAdmin(){
		List<LongLat> list;
		try{
			list = longLatService.getLongLatClientUtilByDormAdmin();
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}
		return list;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-26
	 * @function 服务端领导按预警类型查询预警消息
	 */
	public List<LongLatUtil> getLongLatUtilByLongLattype(String longlattype){
		List<LongLatUtil> list;
		try{
			list = longLatService.getLongLatUtilByLongLattype(longlattype);
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}	
		return list;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-27
	 * @function 服务端领导按预警类型查询教师预警消息
	 */
	public List<LongLatUtil> getTeacherLongLatUtilByLongLattype(String longlattype){
		List<LongLatUtil> list;
		try{
			list = longLatService.getTeacherLongLatUtilByLongLattype(longlattype);
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}	
		return list;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-26
	 * @function 服务端领导按时间段查询预警消息
	 */
	public List<LongLatUtil> getLongLatUtilByDate(String date1,String date2){
		List<LongLatUtil> list;
		try{
			list = longLatService.getLongLatUtilByDate(date1,date2);
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}	
		return list;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-27
	 * @function 服务端领导按时间段查询教师预警消息
	 */
	public List<LongLatUtil> getTeacherLongLatUtilByDate(String date1,String date2){
		List<LongLatUtil> list;
		try{
			list = longLatService.getTeacherLongLatUtilByDate(date1, date2);
		}catch(Exception e){
			e.printStackTrace();
			list = null;
		}	
		return list;
	}

	/**
	 * @author JSY
	 * @time 2016.3.15
	 * @function 客户端领导登录后直接查询最新的预警消息
	 */
	public List<LongLat> getAllLongLat(){
		
		 List<LongLat> longLatList;
		try
		{
			longLatList=longLatService.getAllLongLat();
			System.out.println("longLatList---->"+" "+longLatList);
		} catch (Exception e)
		{
			return longLatList=null;
		}
		
		return longLatList;
	}
}
