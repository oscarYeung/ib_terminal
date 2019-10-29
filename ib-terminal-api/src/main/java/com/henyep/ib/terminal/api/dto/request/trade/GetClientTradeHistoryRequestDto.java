package com.henyep.ib.terminal.api.dto.request.trade;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;

public class GetClientTradeHistoryRequestDto extends BaseDateTimeRequestBodyDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6582747901944394755L;

	private String ib_code;
	
	
	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	private String client_code;

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}
	
	
}
