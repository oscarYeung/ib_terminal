package com.henyep.ib.terminal.api.dto.request.rebate;

import java.io.Serializable;

public class GetRebateByIbCodeRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3494801707950195095L;

	
	private String ib_code;


	public String getIb_code() {
		return ib_code;
	}


	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	

}
