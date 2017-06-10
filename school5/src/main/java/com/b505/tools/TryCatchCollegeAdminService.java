package com.b505.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Roll_Alert;
import com.b505.bean.util.LongLatUtil;
import com.b505.service.ICollegeAdminAlertService;
import com.b505.service.ILongLatService;

@Component
public class TryCatchCollegeAdminService
{
	@Autowired
	private ILongLatService longLatService;
	@Autowired
	private ICollegeAdminAlertService collegeAdminAlertService;
	/**
	 * @author lyf
	 * @time 2015-11-22
	 * @function 服务端学院教学科获取预警消息
	 */
	public List<LongLatUtil> getLongLatUtilByCollegeAdmin(){
		List<LongLatUtil> longLatUtilList;
		try {
			longLatUtilList = longLatService.getLongLatUtilByCollegeAdmin();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return longLatUtilList = null;
		}
		return longLatUtilList;
	}

	/**
	 * @author 贾少游
	 * @time 2016.5.22
	 * @function 服务端学院教学科获取预警成绩
	 */
	public List<Roll_Alert> getCollegeAdminAlert()
	{
		// TODO Auto-generated method stub
		List<Roll_Alert> roll_Alert;
		try
		{
			roll_Alert=collegeAdminAlertService.getCollegeAdminAlert();
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			return roll_Alert=null;
		}
		return roll_Alert;
	}	
}
