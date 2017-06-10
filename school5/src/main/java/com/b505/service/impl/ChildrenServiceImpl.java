package com.b505.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.ChildrenMenu;
import com.b505.bean.util.ChildrenMenuUtil;
import com.b505.dao.IBaseDao;
import com.b505.dao.IChildrenMenuDao;
import com.b505.service.IChildrenMenuService;
@Service("ChildrenMenuService")
public class ChildrenServiceImpl extends BaseServiceImpl<ChildrenMenu> implements IChildrenMenuService{
	private IChildrenMenuDao ichildrenMenuDao;
	@Autowired
	@Qualifier("IChildrenMenuDao")
	@Override
	public void setIBaseDao(IBaseDao<ChildrenMenu> ichildrenMenuDao) {
		// TODO Auto-generated method stub
		this.baseDao = ichildrenMenuDao;
		this.ichildrenMenuDao = (IChildrenMenuDao)ichildrenMenuDao;
	}
	@Override
	public List<ChildrenMenu> getChildrenMenuByparent(String parentMenuName){
		return ichildrenMenuDao.getChildrenMenuByparent(parentMenuName);
	}
	@Override
	public void deleteChildrenMenuByBatch(List<Integer> list) {
		ichildrenMenuDao.deleteChildrenMenuByBatch(list);
		
	}
	@Override
	public void saveChildrenMenuByBatch(List<ChildrenMenu> list) {
		ichildrenMenuDao.saveChildrenMenuByBatch(list);
		
	}
	@Override
	public void updateChildrenMenuByBatch(List<Map<String, Object>> list) {
		ichildrenMenuDao.updateChildrenMenuByBatch(list);
		
	}
	@Override
	public boolean isHaveChrildrenMenu(String url) {
		List <ChildrenMenu> list= new ArrayList<ChildrenMenu>(); 
		list=ichildrenMenuDao.getByUrlOrByChildrenMenuname(url);
		boolean b = false;
		if(list!=null){
			b=true;
		}
		return b;
	}
	@Override
	public List<ChildrenMenuUtil> getAllChilderUtils() {
		// TODO Auto-generated method stub
		return ichildrenMenuDao.getAllChilderUtils();
	}
	@Override
	public List<ChildrenMenu> getChildrenMenusByRoleAndParent(String roleName,String parentMenu) {
		// TODO Auto-generated method stub
		return ichildrenMenuDao.getChildrenMenusByRoleAndParent(roleName,parentMenu);
	}
	
}
