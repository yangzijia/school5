package com.b505.dao;
import java.util.List;
import java.util.Map;

import com.b505.bean.Section;

public interface ISectionDao extends IBaseDao<Section> {
	public void saveSectionByBatch(List<Section> list);
	public Boolean deleteSectionBySectionName(Integer sectionId,String sectionName);
	public void updateSectionByBatch(List<Map<String, Object>> list);
	public Section getSection1(String section);
}
