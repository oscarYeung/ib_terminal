package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.ClientDailySummaryBean;

public interface ClientDailySummaryDao{
	public void saveClientDailySummary(final ClientDailySummaryBean clientDailySummary) throws Exception;

	public List<ClientDailySummaryBean> getAllClientDailySummarys() throws Exception;

	public List<ClientDailySummaryBean> getClientDailySummarysByDateRange(Date start_date, Date end_date, List<String> clientList) throws Exception;
	
	public List<ClientDailySummaryBean> getClientDailySummaryByKey(String client_code, Date trade_date) throws Exception;

	public int updateClientDailySummary(ClientDailySummaryBean clientDailySummary) throws Exception;

	public int deleteClientDailySummary(String client_code, Date trade_date) throws Exception;
	
	
}
