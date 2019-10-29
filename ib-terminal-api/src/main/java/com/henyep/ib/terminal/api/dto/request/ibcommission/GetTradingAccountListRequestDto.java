package com.henyep.ib.terminal.api.dto.request.ibcommission;

import java.io.Serializable;

public class GetTradingAccountListRequestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 549760013531939906L;
	
	
	private String ib_code;


	public String getIb_code() {
		return ib_code;
	}


	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	
	
	
	

}
