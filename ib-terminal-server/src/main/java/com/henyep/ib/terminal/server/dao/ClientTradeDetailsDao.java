package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.ClientTradeDetailsBean;

public interface ClientTradeDetailsDao{
	public void saveClientTradeDetails(final ClientTradeDetailsBean clientTradeDetails) throws Exception;
	public void saveClientTradeDetailsList(List<ClientTradeDetailsBean> clientTradeDetailsList) throws Exception;
	
	public List<ClientTradeDetailsBean> getAllClientTradeDetailss() throws Exception;

	public List<ClientTradeDetailsBean> getClientTradeDetailsByKey(String ticket) throws Exception;
	
	public List<ClientTradeDetailsBean> getClientTradeDetailsFromSAP(String tradeDate) throws Exception;
	
	public int clearClientTradeDetails(String tradeDate) throws Exception;
	
	public List<ClientTradeDetailsBean> getClientTradeDetailsByCltCodesDateRange(List<String> clientCodes, Date startDate, Date endDate) throws Exception;
	
	public List<ClientTradeDetailsBean> getClientTradeDetailsByDateRange(Date startDate, Date endDate) throws Exception;

	public int updateClientTradeDetails(ClientTradeDetailsBean clientTradeDetails) throws Exception;

	public int deleteClientTradeDetails(String ticket) throws Exception;
	
	public List<ClientTradeDetailsBean> getClientTradeDetailsByTradeDate(Date tradeDate) throws Exception;
	
}
