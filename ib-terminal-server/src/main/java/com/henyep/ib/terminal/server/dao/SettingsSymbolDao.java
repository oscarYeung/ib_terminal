package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.SettingsSymbolBean;
import com.henyep.ib.terminal.server.dto.mt4.model.MT5SymbolRecord;
import com.henyep.ib.terminal.server.dto.mt4.model.SymbolRecord;

public interface SettingsSymbolDao{
	public void saveSettingsSymbol(List<SettingsSymbolBean> settingsSymbols, Date trade_date) throws Exception;
	
	public List<SettingsSymbolBean> getAllSettingsSymbols(Date trade_date) throws Exception;

	public List<SettingsSymbolBean> getSettingsSymbolByKey(String symbol, Date start_date) throws Exception;
	
	public SettingsSymbolBean getSettingsSymbolBySymbolTradeDate(String symbol, Date trade_date) throws Exception;

	public int updateSettingsSymbol(SettingsSymbolBean settingsSymbol) throws Exception;

	public int deleteSettingsSymbol(String symbol, Date start_date) throws Exception;
	
	public List<SettingsSymbolBean> getSettingsSymbolFromSAP(Date trade_date) throws Exception;
	
	public int deleteByTradeDate(Date trade_date) throws Exception;
	
	public int updateSettingsSymbolSpreads(List<SymbolRecord> symbolRecords, Date trade_date, String last_udpate_user) throws Exception;
	
	public List<SymbolRecord> getAllDefaultSpreads() throws Exception;
	
	public int deleteDefaultSpreads() throws Exception;
	
	public void saveDefaultSpread(List<SymbolRecord> defaultSpreads) throws Exception;
	
	public void saveMT5Spread(List<MT5SymbolRecord> defaultSpreads) throws Exception;
	
}
