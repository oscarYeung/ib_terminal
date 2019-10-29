package com.henyep.ib.terminal.api.dto.response.client.balance.summary;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.db.ClientBalanceSummaryBean;

public class UpdateAccountBalanceResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6827429587300888046L;
	
	private ClientBalanceSummaryBean client_balance_summary;

	public ClientBalanceSummaryBean getClient_balance_summary() {
		return client_balance_summary;
	}

	public void setClient_balance_summary(ClientBalanceSummaryBean client_balance_summary) {
		this.client_balance_summary = client_balance_summary;
	}
	
	
}
