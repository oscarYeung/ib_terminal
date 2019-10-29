package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.SettingsUsdCurrencyExchangeBean;

public interface SettingsUsdCurrencyExchangeDao{
	public void saveSettingsUsdCurrencyExchange(final SettingsUsdCurrencyExchangeBean settingsUsdCurrencyExchange) throws Exception;

	public List<SettingsUsdCurrencyExchangeBean> getAllSettingsUsdCurrencyExchanges() throws Exception;

	public List<SettingsUsdCurrencyExchangeBean> getSettingsUsdCurrencyExchangeByKey(String base_currency) throws Exception;

	public int updateSettingsUsdCurrencyExchange(SettingsUsdCurrencyExchangeBean settingsUsdCurrencyExchange) throws Exception;

	public int deleteSettingsUsdCurrencyExchange(String base_currency) throws Exception;
}
