package com.henyep.ib.terminal.api.dto.request.marginout;

import java.io.Serializable;

public class GetMaxWithdrawalRequestDto implements Serializable{
	
	private static final long serialVersionUID = 4532832190987609079L;
	
	private String ib_code;

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	
}
