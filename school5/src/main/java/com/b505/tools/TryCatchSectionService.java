package com.b505.tools;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b505.bean.Section;
import com.b505.service.ISectionService;

@Component
public class TryCatchSectionService 
{
	@Autowired
	private  ISectionService   sectionService;
	
	
	public boolean saveSectionByBatch(List<Section> list)
	{
		try
		{
			sectionService.saveSectionByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean updateSectionByBatch(List<Map<String, Object>> list)
	{
		try
		{
			sectionService.updateSectionByBatch(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean deleteSectionBySectionName(Integer sectionID, String sectionName)
	{
		boolean status = false;
		try
		{
			status = sectionService.deleteSectionBySectionName(sectionID, sectionName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	
	public List<Section> getSectionAll()
	{
		List<Section> sectionList;
		try
		{
			sectionList =  sectionService.getAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return sectionList = null;
		}
		return sectionList;
	}
	
	
	public Section  getSection(String propertyName1, Object value1)
	{
		Section section;
		try
		{
			section = sectionService.get(propertyName1, value1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return section = null;
		}
		return section;
	}


	public Section getSection1(String section)
	{
		// TODO Auto-generated method stub
		Section section1;
		try
		{
			section1=sectionService.getSection1(section);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			return section1 = null;
		}
		return section1;
	}
}


