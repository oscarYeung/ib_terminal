package com.henyep.ib.terminal.server.dto.processor;

import com.henyep.ib.terminal.api.dto.db.ClientDailySummaryBean;

public class ibClientSummaryDto {

	private String ib_code;

	private ClientDailySummaryBean clientSummary;

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public ClientDailySummaryBean getClientSummary() {
		return clientSummary;
	}

	public void setClientSummary(ClientDailySummaryBean clientSummary) {
		this.clientSummary = clientSummary;
	}

}
