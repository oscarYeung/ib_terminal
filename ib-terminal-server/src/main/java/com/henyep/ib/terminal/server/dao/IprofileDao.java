package com.henyep.ib.terminal.server.dao;

import com.henyep.ib.terminal.api.dto.db.IbProfileBean;

public interface IprofileDao
{
	int create(IbProfileBean profile) throws Exception;

	int updateProfile(IbProfileBean profile) throws Exception;

	void execProc(String procName, Object... os);
	
	IbProfileBean getProfileByIbCode(String ibCode) throws Exception;

	IbProfileBean getProfileByUsernameAndBrandCode(String username, String brandCode) throws Exception;

	int getProfileCountByUsername(String username) throws Exception;

	IbProfileBean getProfileByUsernameAndPassword(String username, String password) throws Exception;
	
	IbProfileBean getProfileByUsernameAndEmail(String username,String email)throws Exception;
	
	int updateProfilePassword(String ib_code, String password, String sender) throws Exception;
}
