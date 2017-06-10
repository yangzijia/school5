package com.b505.dao.impl;

import org.springframework.stereotype.Repository;

import com.b505.bean.UserRecord;
import com.b505.dao.IUserRecordDao;

@Repository(value="IUserRecordDao")
public class UserRecordDaoImpl extends BaseDaoImpl<UserRecord>  implements IUserRecordDao{


}
