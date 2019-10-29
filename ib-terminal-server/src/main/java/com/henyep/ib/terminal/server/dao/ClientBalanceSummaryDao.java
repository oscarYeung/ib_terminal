package com.henyep.ib.terminal.server.dao;

import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.ClientBalanceSummaryBean;
import com.henyep.ib.terminal.server.dto.dao.ClientDeficitDto;
import com.henyep.ib.terminal.server.dto.dao.ClientEquityChangeDto;
import com.henyep.ib.terminal.server.dto.dao.GroupClientTradeDto;
import com.henyep.ib.terminal.server.dto.dao.GroupDeficitDto;
import com.henyep.ib.terminal.server.dto.dao.GroupEquityChangeDto;

public interface ClientBalanceSummaryDao {
	public void saveClientBalanceSummary(final ClientBalanceSummaryBean clientBalanceSummary) throws Exception;

	public List<ClientBalanceSummaryBean> getAllClientBalanceSummarys() throws Exception;

	public List<ClientBalanceSummaryBean> getClientBalanceSummaryByKey(String client_code, Date trade_date) throws Exception;

	public int updateClientBalanceSummary(ClientBalanceSummaryBean clientBalanceSummary) throws Exception;

	public int deleteClientBalanceSummary(String client_code, Date trade_date) throws Exception;
	
	public ClientEquityChangeDto getEquityChangeByIbDayRange(String ib_code, Date start_date, Date end_date) throws Exception;

	public List<ClientDeficitDto> getClientDeficit(String ib_code, Date start_date, Date end_date) throws Exception;

	public int deleteClientBalanceSummaryByTradeDate(Date trade_date);
	
	public int deleteClientBalanceSummaryByLessThanTradeDate(Date trade_date);

	public List<ClientBalanceSummaryBean> getClientBalanceSummaryFromSAP(Date trade_date);

	public void saveClientBalanceSummaryList(List<ClientBalanceSummaryBean> clientBalanceSummaryList) throws Exception;

	public List<GroupDeficitDto> getGroupDeficitFromSAP(List<String> groups, Date last_month_end, Date current_month_end) throws Exception;

	public List<GroupEquityChangeDto> getGroupEquityChangeFromSAP(List<String> groups, Date start_date, Date end_date) throws Exception;
	
	public List<GroupClientTradeDto> getGroupClientTradeFromSAP(List<String> groups, Date start_date, Date end_date) throws Exception;
	
	public int updateAccountBalance(String clientCode, Double accountBalance, String updatedBy) throws Exception;
	
	public List<ClientBalanceSummaryBean> getLatestClientBalanceSummary(String clientCode) throws Exception;
	
	

}
