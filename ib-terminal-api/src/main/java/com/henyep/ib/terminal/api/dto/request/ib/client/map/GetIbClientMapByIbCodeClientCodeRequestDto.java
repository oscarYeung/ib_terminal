package com.henyep.ib.terminal.api.dto.request.ib.client.map;

import java.io.Serializable;

public class GetIbClientMapByIbCodeClientCodeRequestDto implements Serializable{

	private static final long serialVersionUID = -5910902036587144506L;
	
	private String ib_code;
	private String client_code;

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	
}
