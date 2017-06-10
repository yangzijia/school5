package com.b505.dao;

import com.b505.bean.DormcalloverCount;

public interface IDormcalloverCountDao extends IBaseDao<DormcalloverCount>{

	public DormcalloverCount getDormcalloverCountBysnumber(String studentNumber);
}
