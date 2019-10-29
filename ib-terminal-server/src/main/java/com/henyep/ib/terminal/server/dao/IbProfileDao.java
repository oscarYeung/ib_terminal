package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;

public interface IbProfileDao{
	public void saveIbProfile(final IbProfileBean ibProfile) throws Exception;

	public List<IbProfileBean> getAllIbProfiles() throws Exception;

	public IbProfileBean getIbProfileByKey(String ib_code) throws Exception;
	
	public List<IbProfileBean> getIbProfileByIbCode(String ib_code) throws Exception;

	public int updateIbProfile(IbProfileBean ibProfile) throws Exception;

	public int deleteIbProfile(int ib_code, String brand_code) throws Exception;

	public IbProfileBean getProfileByIbCodeAndPassword(String ibCode, String password) throws Exception;
	
	
}
