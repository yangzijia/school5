package com.b505.service;

import java.util.List;

import com.b505.bean.CallOverCount;
import com.b505.bean.util.CallOverCountUtil;
import com.b505.bean.util.CallOverCountUtils;

public interface ICallOverCountService extends IBaseService<CallOverCount> {
	public CallOverCount getCallOverCountBysnumber(String studentNumber);
	public List<CallOverCountUtil> getCountUtilsByCollege(String collgeName);
	public List<CallOverCountUtil> getCountUtilsByGrade(String yearClass,
			String profession, String classId);
	public Long getTotalCountByCollegeAndStatus(String collegeName);
	public List<CallOverCountUtil> getCountUtilsByPagerAndCollege(
			String collgeName, Integer currentPage, Integer pageSize);
	public List<CallOverCountUtils> getWarnCountUtilsByGrade(String yearClass,String profession,String classId );
}
