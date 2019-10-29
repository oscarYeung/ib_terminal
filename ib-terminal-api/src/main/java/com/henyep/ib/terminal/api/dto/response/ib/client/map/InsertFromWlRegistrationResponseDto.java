package com.henyep.ib.terminal.api.dto.response.ib.client.map;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.db.ClientBalanceSummaryBean;
import com.henyep.ib.terminal.api.dto.db.ClientDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;

public class InsertFromWlRegistrationResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3963842375941381020L;
	
	
	private IbClientMapBean ib_client_map;
	private ClientDetailsBean client_details;
	private ClientBalanceSummaryBean client_balance_summary;
	
	public IbClientMapBean getIb_client_map() {
		return ib_client_map;
	}
	public void setIb_client_map(IbClientMapBean ib_client_map) {
		this.ib_client_map = ib_client_map;
	}
	public ClientDetailsBean getClient_details() {
		return client_details;
	}
	public void setClient_details(ClientDetailsBean client_details) {
		this.client_details = client_details;
	}
	public ClientBalanceSummaryBean getClient_balance_summary() {
		return client_balance_summary;
	}
	public void setClient_balance_summary(ClientBalanceSummaryBean client_balance_summary) {
		this.client_balance_summary = client_balance_summary;
	}
	
	
}
