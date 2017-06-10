package com.b505.dao;

import java.util.List;
import java.util.Map;

import com.b505.bean.Leader;
import com.b505.bean.util.LeaderRegisterUtil;
import com.b505.bean.util.LeaderUtil;

import com.b505.bean.util.Roll_AlertUtil2;
import com.b505.bean.util.Roll_AlertUtils;

public interface ILeaderDao extends IBaseDao<Leader>{
	public int deleteLeaderByPhone(String phone);
	public List<LeaderUtil> getLeaderUtils();
	public void  deleteLeaderByBatch(List<Integer> list);
	public void updateLeaderByBatch(List<Map<String, Object>> list);
	public void saveLeaderByBatch(List<Leader>  list);
	public LeaderRegisterUtil getLeaderRegisterUtilBynickName(String nickName);
	public boolean updatePhoneBynickName(String phone,String nickName);
	public Leader getLeaderByNickname(String nickname);
	//学生求救获取本院副书记、副院长以下领导信息
	public List<Leader> getLeadersByCollegeAndRank(int collegeid);
	public List<Roll_AlertUtils> getRoll_Alert(String college,
			String yearClass, String profession, String classId);
	public List<Roll_AlertUtil2> getLeaderGetRollAlertDetail(String studentNumber);
	public List<Roll_AlertUtils> getwebLeaderGetAllRollAlert();
}
