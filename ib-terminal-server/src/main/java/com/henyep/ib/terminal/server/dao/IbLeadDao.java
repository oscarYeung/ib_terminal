package com.henyep.ib.terminal.server.dao;
import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbLeadBean;

public interface IbLeadDao{
	public void saveIbLead(final IbLeadBean ibLead) throws Exception;
	
	public void saveIbLeads(List<IbLeadBean> ibLeads) throws Exception;

	public List<IbLeadBean> getAllIbLeads() throws Exception;

	public List<IbLeadBean> getIbLeadByKey(String salesforce_id) throws Exception;

	public int updateIbLead(IbLeadBean ibLead) throws Exception;

	public int deleteIbLead(String salesforce_id) throws Exception;
	
	public List<IbLeadBean> getLeads(String ib_code, String name, String email, String phone);
	
	public List<IbLeadBean> getLeadsWithSubIb(String ib_code, String name, String email, String phone);
	
	public int deleteIbLeads(List<String> salesforce_ids) throws Exception;
	
	public int deleteIbLeadsByTradeDate(Date tradeDate) throws Exception;	
	
	public List<String> getDeletedLeadFromSAP(Date tradeDate);
	
	public List<IbLeadBean> getNewLeadFromSAP(Date tradeDate);
	
	
}
