package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.SettingsCfdBean;

public interface SettingsCfdDao{
	public void saveSettingsCfd(final SettingsCfdBean settingsCfd) throws Exception;

	public List<SettingsCfdBean> getAllSettingsCfds() throws Exception;

	public List<SettingsCfdBean> getSettingsCfdByKey(String symbol, Date start_date) throws Exception;

	public int updateSettingsCfd(SettingsCfdBean settingsCfd) throws Exception;

	public int deleteSettingsCfd(String symbol, Date start_date) throws Exception;
}
