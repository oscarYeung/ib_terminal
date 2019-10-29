package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.SettingsMt4GroupBean;

public interface SettingsMt4GroupDao{
	public void saveSettingsMt4Group(final SettingsMt4GroupBean settingsMt4Group) throws Exception;

	public List<SettingsMt4GroupBean> getAllSettingsMt4Groups() throws Exception;

	public List<SettingsMt4GroupBean> getSettingsMt4GroupByKey(String mt4_group, Date start_date, String product_group) throws Exception;

	public int updateSettingsMt4Group(SettingsMt4GroupBean settingsMt4Group) throws Exception;

	public int deleteSettingsMt4Group(String mt4_group, Date start_date, String product_group) throws Exception;
}
