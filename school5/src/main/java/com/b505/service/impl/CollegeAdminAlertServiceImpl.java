package com.b505.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.b505.bean.Roll_Alert;
import com.b505.dao.IBaseDao;
import com.b505.dao.ICollegeAdminAlertDao;
import com.b505.service.ICollegeAdminAlertService;
@Service("CollegeAdminAlertService")
public class CollegeAdminAlertServiceImpl extends BaseServiceImpl<Roll_Alert> implements ICollegeAdminAlertService{
    private ICollegeAdminAlertDao iCollegeAdminAlertDao;
    @Autowired
	@Qualifier("ICollegeAdminAlertDao")
	@Override
	public void setIBaseDao(IBaseDao<Roll_Alert> iCollegeAdminAlertDao) {
		// TODO Auto-generated method stub
		this.baseDao = iCollegeAdminAlertDao;
		this.iCollegeAdminAlertDao = (ICollegeAdminAlertDao) iCollegeAdminAlertDao;
	}
    @Override
	public List<Roll_Alert> getCollegeAdminAlert(){
		return iCollegeAdminAlertDao.getCollegeAdminAlert();
	}

}
