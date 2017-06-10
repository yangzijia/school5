package com.b505.tools;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

import com.b505.bean.Administrator;
import com.b505.service.IAdministratorService;

@Component
public class TryCatchAdminService
{
	@Autowired
	private IAdministratorService adminService;
	
	
	public boolean saveAdministratorBayBatch(List<Administrator> list)
	{
		try
		{
			adminService.saveAdministratorBayBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteAdministratorByBatch(List<Integer> list)
	{
		try
		{
			adminService.deleteAdministratorByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updataAdmin(Administrator admin)
	{
		boolean status = false;
		try
		{
			adminService.update(admin);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
	public boolean updataAdminByBench(List<Map<String,Object>> adminList)
	{
		boolean status = false;
		try
		{
			adminService.updateAdministratorByBatch(adminList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	public Administrator getAdministrator(String nickName){
		Administrator administrator = null;
		try {
			administrator = adminService.getAdministratorBynickName(nickName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return  administrator = null;
		}
		return administrator;
	}
	public boolean updatePhoneByNickName(String phone,String nickName){
		boolean status = false;
		try
		{
			status = adminService.updatePhoneByNickName(phone, nickName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status;
	}
}
