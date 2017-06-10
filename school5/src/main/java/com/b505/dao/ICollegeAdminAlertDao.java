package com.b505.dao;

import java.util.List;

import com.b505.bean.Roll_Alert;

public interface ICollegeAdminAlertDao extends IBaseDao<Roll_Alert>
{

	public List<Roll_Alert> getCollegeAdminAlert();

}
