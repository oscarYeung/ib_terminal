package com.henyep.ib.terminal.server.dao;
import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.ClientMarginInOutBean;

public interface ClientMarginInOutDao{
	public void saveClientMarginInOut(final ClientMarginInOutBean clientMarginInOut) throws Exception;
	public void saveClientMarginInOuts(List<ClientMarginInOutBean> clientMarginInOuts) throws Exception;
	

	public List<ClientMarginInOutBean> getAllClientMarginInOuts() throws Exception;

	public List<ClientMarginInOutBean> getClientMarginInOutByKey(String order_id) throws Exception;

	public int updateClientMarginInOut(ClientMarginInOutBean clientMarginInOut) throws Exception;

	public int deleteClientMarginInOut(String order_id) throws Exception;
	
	public List<ClientMarginInOutBean> getClientMarginByDateClientCodes(Date startDate, Date endDate, List<String> cltCodes) throws Exception;
	
	public List<ClientMarginInOutBean> getClientMarginGroupByDate(String cltCode, Date startDate, Date endDate) throws Exception;

	public List<ClientMarginInOutBean> getClientMarginInOutFromSAP(String tradeDate) throws Exception;

	public int clearClientMarginInOut(String tradeDate) throws Exception;
	
	public List<ClientMarginInOutBean> getByIbCode(String ibCode, Date startDate, Date endDate) throws Exception;
	
	public List<ClientMarginInOutBean> getByClientCode(String ibCode, String ClientCode, Date startDate, Date endDate) throws Exception;
	
}
