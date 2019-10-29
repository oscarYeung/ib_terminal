package com.henyep.ib.terminal.server.dao;

import com.henyep.ib.terminal.api.dto.db.IbProfileBean;

/**
 * ib_profile_history:CRUD
 *
 * @author ShenZhong
 * @date 2016年8月25日
 */
public interface IprofileHistoryDao
{
	int create(IbProfileBean profile) throws Exception;
}
