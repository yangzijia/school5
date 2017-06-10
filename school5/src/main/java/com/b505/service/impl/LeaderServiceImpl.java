package com.b505.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b505.bean.Leader;
import com.b505.bean.util.LeaderRegisterUtil;
import com.b505.bean.util.LeaderUtil;

import com.b505.bean.util.Roll_AlertUtil2;
import com.b505.bean.util.Roll_AlertUtils;
import com.b505.dao.IBaseDao;
import com.b505.dao.ILeaderDao;
import com.b505.service.ILeaderService;

@Service(value="LeaderService")
public class LeaderServiceImpl extends BaseServiceImpl<Leader> implements ILeaderService {
	private ILeaderDao ileaderDao ;
	@Autowired
	@Qualifier("ILeaderDao")
	@Override
	public void setIBaseDao(IBaseDao<Leader> ileaderDao) {
		// TODO Auto-generated method stub
		this.baseDao = ileaderDao;
		this.ileaderDao = (ILeaderDao)ileaderDao;
	}
	@Override
	public int hasRegisterSuccess(String userNickname, String phone) {
		// TODO Auto-generated method stub
		Leader leader  = ileaderDao.get("phone", phone);
		int b = 0;
		if(leader!=null&&leader.getUser().getNickName().equals(userNickname)){
			b = 1;
		}else{
			b = 0;
		}
		return b;
	}
	@Override
	public int deleteLeaderByPhone(String phone) {
		// TODO Auto-generated method stub
		int b = ileaderDao.deleteLeaderByPhone(phone);
		return b;

	}
	@Override
	public List<LeaderUtil> getLeaderUtils(){
		List<LeaderUtil> list=ileaderDao.getLeaderUtils();
		return list;
	}
	@Override
	public void deleteLeaderByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		ileaderDao.deleteLeaderByBatch(list);
		
	}
	@Override
	public void saveLeaderByBatch(List<Leader>  list) {
		// TODO Auto-generated method stub
		ileaderDao.saveLeaderByBatch(list);
	}
	@Override
	public void updateLeaderByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		ileaderDao.updateLeaderByBatch(list);
	}
	@Override
	public LeaderRegisterUtil getLeaderRegisterUtilBynickName(String nickName) {
		// TODO Auto-generated method stub
		return  ileaderDao.getLeaderRegisterUtilBynickName(nickName);
	}
	@Override
	public boolean updatePhoneBynickName(String phone, String nickName) {
		// TODO Auto-generated method stub
		return ileaderDao.updatePhoneBynickName(phone, nickName);
	}
	@Override
	public Leader getLeaderByNickname(String nickname) {
		// TODO Auto-generated method stub
		return ileaderDao.getLeaderByNickname(nickname);
	}

	@Override
	public List<Leader> getLeadersByCollegeAndRank(int collegeid){
		return ileaderDao.getLeadersByCollegeAndRank(collegeid);
	}
	@Override
	public List<Roll_AlertUtils> getRoll_Alert(String college,
			String yearClass, String profession, String classId)
	{
		// TODO Auto-generated method stub
		return ileaderDao.getRoll_Alert(college,yearClass,profession,classId);
	}
	@Override
	public List<Roll_AlertUtil2> getLeaderGetRollAlertDetail(String studentNumber)
	{
		// TODO Auto-generated method stub
		return ileaderDao.getLeaderGetRollAlertDetail(studentNumber);
	}
	@Override
	public List<Roll_AlertUtils> getwebLeaderGetAllRollAlert()
	{
		// TODO Auto-generated method stub
		return ileaderDao.getwebLeaderGetAllRollAlert();
	}
}
