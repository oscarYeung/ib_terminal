package com.henyep.ib.terminal.api.dto.request.ib;

import java.io.Serializable;

public class IbSendWelcomeEmailRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 290873150243292311L;
	
	private String ib_code;

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	

}
