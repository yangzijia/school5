package com.b505.tools;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Leader;
//import com.b505.bean.util.Roll_AlertUtil;
import com.b505.bean.util.Roll_AlertUtil2;
import com.b505.bean.util.Roll_AlertUtils;
import com.b505.service.ILeaderService;
import com.b505.service.ILoginUserService;

@Component
public class TryCatchLeaderService 
{
	@Autowired
	private ILeaderService leaderService;
	@Autowired
	private ILoginUserService userService;
	
	public List<Leader> getAllLeader()
	{
		List<Leader> leaderList;
		try
		{
			leaderList = leaderService.getAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			leaderList = null;
		}
		return leaderList;
	}
	
	public Leader getLeaderByNickname(String nickname)
	{
		Leader leader;
		try
		{
			leader = leaderService.getLeaderByNickname(nickname);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return leader = null;
		}
		return leader;
	}
	
	
	public boolean deleteLeaderByBatch(List<Integer> list)
	{
		try
		{
			leaderService.deleteLeaderByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/*
	 * @方法名：saveUserByBatch(List<User> userList)
	 * @功能：批量保存user
	 * @功能说明：批量保存user
	 * @作者：李振强
	 * @创建时间：2014.5.16
	 * @修改时间：2014.5.16
	 */
	public boolean saveLeaderByBatch(List<Leader> list)
	{
		try
		{
			leaderService.saveLeaderByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean updateLeaderByBatch(List<Map<String, Object>> list)
	{
		try
		{
			leaderService.updateLeaderByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updatePhoneBynickName(String phone, String nickName)
	{
		boolean status = false;
		try
		{
			status = leaderService.updatePhoneBynickName(phone, nickName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status;
	}
	
	public boolean updatePasswordByNickName(String password, String nickName)
	{
		boolean status = false;
		try
		{
			status = userService.updatePasswordByNickName(password, nickName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status;
	}
	
	/**
	 * @功能：学生求救获取本院副书记、副院长以下领导信息
	 * @author lyf
	 * @time 2015.11.17
	 * @param collegeid
	 * @param rank
	 * @return
	 */
	public List<Leader> getLeadersByCollegeAndRank(int collegeid){
		List<Leader> list;
		try{
			list = leaderService.getLeadersByCollegeAndRank(collegeid);
		}catch(Exception e){
			e.printStackTrace();
			list=null;
		}
		return list;
	}

	public List<Roll_AlertUtils> getRoll_Alert(String college,
			String yearClass, String profession, String classId)
	{
		List<Roll_AlertUtils>list;
		try
		{
			list=leaderService.getRoll_Alert(college,yearClass,profession,classId);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			list=null;
		}
		return list;
	}

	public List<Roll_AlertUtil2> getLeaderGetRollAlertDetail(String studentNumber)
	{
		List<Roll_AlertUtil2>list;
		try
		{
			list=leaderService.getLeaderGetRollAlertDetail(studentNumber);
		} catch (Exception e)
		{
			e.printStackTrace();
		list=null;
		}
		return list;
	}

	public List<Roll_AlertUtils> getwebLeaderGetAllRollAlert()
	{
		// TODO Auto-generated method stub
		List<Roll_AlertUtils>list;
		try
		{
			list=leaderService.getwebLeaderGetAllRollAlert();
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			list=null;
		}
		return list;
	}
}

