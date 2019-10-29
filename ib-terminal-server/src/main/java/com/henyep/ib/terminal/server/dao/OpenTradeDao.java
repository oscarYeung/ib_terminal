package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.OpenTradeBean;

public interface OpenTradeDao{
	public void saveOpenTrade(final OpenTradeBean openTrade) throws Exception;
	
	public void saveOpenTrades(List<OpenTradeBean> openTradesList) throws Exception;

	public List<OpenTradeBean> getAllOpenTrades() throws Exception;

	public List<OpenTradeBean> getOpenTradeByKey(Date trade_date, String ticket) throws Exception;

	public int updateOpenTrade(OpenTradeBean openTrade) throws Exception;

	public int deleteOpenTrade(String tradeDateString) throws Exception;
	
	public List<OpenTradeBean> getOpenTradeFromSAP(String tradeDate) throws Exception;
}
