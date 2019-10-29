package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.ClientGroupMappingBean;

public interface ClientGroupMappingDao{
	public void saveClientGroupMapping(final ClientGroupMappingBean clientGroupMapping) throws Exception;
	
	public void saveClientGroupMapping(List<ClientGroupMappingBean> clientGroupMappingBeans, String lastUpdateUser) throws Exception;

	public List<ClientGroupMappingBean> getAllClientGroupMappings() throws Exception;

	public List<ClientGroupMappingBean> getClientGroupMappingByKey(String client_trading_id) throws Exception;

	public int updateClientGroupMapping(ClientGroupMappingBean clientGroupMapping) throws Exception;

	public int deleteClientGroupMapping(String client_trading_id) throws Exception;
	
	public int deleteClientGroupMappingByCreateDate(Date create_date) throws Exception;
	
	public List<ClientGroupMappingBean> getFromSAP(Date tradeDate) throws Exception;
	
	public List<String> getVarSpreadClients() throws Exception;
}
