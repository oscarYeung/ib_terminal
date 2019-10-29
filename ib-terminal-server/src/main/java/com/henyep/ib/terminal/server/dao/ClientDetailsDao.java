package com.henyep.ib.terminal.server.dao;
import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.ClientDetailsBean;
import com.henyep.ib.terminal.api.dto.response.clientProfile.SearchMyClientsModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbClientAccountModel;

public interface ClientDetailsDao{
	public void saveClientDetails(final ClientDetailsBean clientDetails) throws Exception;
	
	public void saveClientDetails(final List<ClientDetailsBean> clientDetailss) throws Exception;

	public List<ClientDetailsBean> getAllClientDetailss() throws Exception;

	public List<ClientDetailsBean> getClientDetailsByKey(String client_trading_id) throws Exception;

	public int updateClientDetails(ClientDetailsBean clientDetails) throws Exception;

	public int deleteClientDetails(String client_trading_id) throws Exception;
	
	public List<IbClientAccountModel> getIbClientAccountSummary(String ib_code, Date trade_date) throws Exception;

	public List<ClientDetailsBean> getClientDetailsFromSAP() throws Exception;

	public int clearClientDetails() throws Exception;
	
	public List<SearchMyClientsModel> searchMyClients(String ib_code, String search_trading_id, String search_ib_code, String search_name,
			String search_phone, String search_email) throws Exception;
	
	public List<ClientDetailsBean> getManuallyAddedClientDetails() throws Exception;
	
	public List<ClientDetailsBean> getClientDetailsByKeys(List<String> client_trading_ids) throws Exception;
}
