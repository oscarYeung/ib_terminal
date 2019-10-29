package com.henyep.ib.terminal.api.dto.request.clientPackageDetails;

import java.io.Serializable;

public class GetClientPackagesByIbCodeRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8507342127554683164L;
	
	private String ib_code;

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	
	

}
