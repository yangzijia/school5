package com.b505.tools;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.DormcalloverCount;
import com.b505.service.IDormcalloverCountService;

@Component
public class TryCatchDormcalloverCountService {

	@Autowired 
	private IDormcalloverCountService iCountService;
	
	public DormcalloverCount getDormcalloverCountBysnumber(String studentNumber){
		DormcalloverCount dCount;
		try{
			dCount = iCountService.getDormcalloverCountBysnumber(studentNumber);
		}catch(Exception e){
			e.printStackTrace();
			dCount = null;
		}
		return dCount;
	}
	
	public boolean save(DormcalloverCount dCount){
		boolean status = false;
		try
		{
			iCountService.save(dCount);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
	public boolean update(DormcalloverCount dCount){
		boolean status = false;
		try
		{
			iCountService.update(dCount);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return status;
		}
		return status = true;
	}
	
}
