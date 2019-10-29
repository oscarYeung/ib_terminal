package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsHistoryBean;

public interface IbAccountDetailsHistoryDao{
	public void saveIbAccountDetailsHistory(final IbAccountDetailsHistoryBean ibAccountDetailsHistory) throws Exception;

	public List<IbAccountDetailsHistoryBean> getAllIbAccountDetailsHistorys() throws Exception;

	public List<IbAccountDetailsHistoryBean> getIbAccountDetailsHistoryByKey(String ib_code, String currency, Date trade_date) throws Exception;

	public int updateIbAccountDetailsHistory(IbAccountDetailsHistoryBean ibAccountDetailsHistory) throws Exception;

	public int deleteIbAccountDetailsHistory(String ib_code, String currency, Date trade_date) throws Exception;

	public int deleteByTradeDate(Date trade_date) throws Exception;
	
	public int insertFromIbAccountDetails(Date trade_date) throws Exception;
}
