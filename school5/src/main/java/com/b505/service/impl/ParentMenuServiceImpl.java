package com.b505.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.ParentMenu;
import com.b505.bean.util.ParentMenuUtil;
import com.b505.dao.IBaseDao;
import com.b505.dao.IParentMenuDao;
import com.b505.service.IParentMenuService;
@Service("ParentMenuService")
public class ParentMenuServiceImpl extends BaseServiceImpl<ParentMenu> implements IParentMenuService {
	private IParentMenuDao iparentMenuDao;
	@Autowired
	@Qualifier("IParentMenuDao")
	@Override
	public void setIBaseDao(IBaseDao<ParentMenu> iparentMenuDao) {
		// TODO Auto-generated method stub
		this.baseDao = iparentMenuDao;
		this.iparentMenuDao = (IParentMenuDao)iparentMenuDao;
	}
	@Override
	public boolean isHaveParentMenu(String parentMenuName) {
		// TODO Auto-generated method stub
		ParentMenu parentMenu = new ParentMenu();
		parentMenu = this.get("menuName",parentMenuName );
		if(parentMenu!=null){
			return true;
		}else {
			return false;
		}
	}
	@Override
	public void saveParentMenuByBatch(List<ParentMenu> list) {
		// TODO Auto-generated method stub
		iparentMenuDao.saveParentMenuByBatch(list);
	}
	@Override
	public void deleteParentMenuByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		iparentMenuDao.deleteParentMenuByBatch(list);
	}
	@Override
	public void updateParentMenuByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		iparentMenuDao.updateParentMenuByBatch(list);
	}
	@Override
	public List<ParentMenuUtil> getParentMenuUtils() {
		// TODO Auto-generated method stub
		return iparentMenuDao.getParentMenuUtils();
	}
	

}
