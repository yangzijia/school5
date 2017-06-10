package com.b505.service;

import com.b505.bean.DormcalloverCount;

public interface IDormcalloverCountService extends IBaseService<DormcalloverCount> {

	public DormcalloverCount getDormcalloverCountBysnumber(String studentNumber);
}
