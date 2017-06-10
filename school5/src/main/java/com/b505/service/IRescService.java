package com.b505.service;

import java.util.List;
import java.util.Map;

import com.b505.bean.Resc;

public interface IRescService extends IBaseService<Resc> {
	public void saveRescByBatch(List<Resc> list);
	public void deleteRescByBatch(List<Integer> list);
	public void updateRescByBatch(List<Map<String, Object>> list);
}
