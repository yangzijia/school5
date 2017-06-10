package com.b505.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.CallOverCount;
import com.b505.bean.util.CallOverCountUtil;
import com.b505.bean.util.CallOverCountUtils;
import com.b505.service.ICallOverCountService;

@Component
public class TryCatchCallOverCountService 
{
	@Autowired
	private ICallOverCountService callOverCountService;
	
	public List<CallOverCountUtil> getCountUtilsByCollege(String collgeName)
	{
		List<CallOverCountUtil> callOverCountUtilList;
		try
		{
			callOverCountUtilList = callOverCountService.getCountUtilsByCollege(collgeName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return callOverCountUtilList = null;
		}
		return callOverCountUtilList;
	}
	
	public List<CallOverCountUtil> getCountUtilsByGrade(String yearClass,String profession, String classId)
	{
		List<CallOverCountUtil> callOverCountUtilList;
		try
		{
			callOverCountUtilList = callOverCountService.getCountUtilsByGrade(yearClass, profession, classId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return callOverCountUtilList = null;
		}
		return callOverCountUtilList;
	}
	
	public List<CallOverCountUtil> getCountUtilsByPagerAndCollege(String collgeName, Integer currentPage, Integer pageSize)
	{
		List<CallOverCountUtil> callOverCountUtilList;
		try
		{
			callOverCountUtilList = callOverCountService.getCountUtilsByPagerAndCollege(collgeName, currentPage, pageSize);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return callOverCountUtilList = null;
		}
		return callOverCountUtilList;
	}
	
	public Long getTotalCountByCollegeAndStatus(String collegeName)
	{
		Long totalCountSize = 0L;
		try
		{
			totalCountSize = callOverCountService.getTotalCountByCollegeAndStatus(collegeName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return totalCountSize = 0L;
		}
		return totalCountSize;
	}
	
	public CallOverCount getCallOverCountBysnumber(String studentNumber)
	{
		CallOverCount callOverCount;
		try
		{
			callOverCount = callOverCountService.getCallOverCountBysnumber(studentNumber);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return callOverCount = null;
		}
		return callOverCount;
	}
	
	public boolean save(CallOverCount callOverCount)
	{
		boolean status = false;
		try
		{
			callOverCountService.save(callOverCount);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
	
	public boolean updata(CallOverCount callOverCount)
	{
		boolean status = false;
		try
		{
			callOverCountService.update(callOverCount);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
	/**
	 * @author lyf
	 * @time 2015-11-20
	 * @functiuon 辅导员查询考勤预警信息
	 * @param yearClass
	 * @param profession
	 * @param classId
	 * @return
	 */
	public List<CallOverCountUtils> getWarnCountUtilsByGrade(String yearClass,String profession,String classId ){
		List<CallOverCountUtils> callOverCountUtils;
		try{
			callOverCountUtils = callOverCountService.getWarnCountUtilsByGrade(yearClass, profession, classId);
		}catch(Exception e){
			e.printStackTrace();
			callOverCountUtils = null;
		}
		return callOverCountUtils;
	}
}

