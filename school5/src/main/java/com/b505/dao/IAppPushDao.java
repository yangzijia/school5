package com.b505.dao;

import com.b505.bean.AppPushInfo;

public interface IAppPushDao extends IBaseDao<AppPushInfo> {

	public abstract void savepushinfo(AppPushInfo api);

}
