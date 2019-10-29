package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.SettingsPointValueBean;

public interface SettingsPointValueDao{
	public void saveSettingsPointValue(final SettingsPointValueBean settingsPointValue) throws Exception;

	public List<SettingsPointValueBean> getAllSettingsPointValues() throws Exception;

	public List<SettingsPointValueBean> getSettingsPointValueByKey(String symbol, Date start_date) throws Exception;

	public int updateSettingsPointValue(SettingsPointValueBean settingsPointValue) throws Exception;

	public int deleteSettingsPointValue(String symbol, Date start_date) throws Exception;
}
