package com.b505.service;

import java.util.List;
import java.util.Map;

import com.b505.bean.Section;

public interface ISectionService extends IBaseService<Section> {
	public Boolean deleteSectionBySectionName(Integer sectionId,String sectionName);
	public void saveSectionByBatch(List<Section> list);
	public void updateSectionByBatch(List<Map<String, Object>> list) ;
	public Section getSection1(String section);
}
