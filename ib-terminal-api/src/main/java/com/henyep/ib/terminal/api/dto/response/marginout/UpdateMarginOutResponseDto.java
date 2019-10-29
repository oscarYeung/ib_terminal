package com.henyep.ib.terminal.api.dto.response.marginout;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.db.MarginOutBean;

public class UpdateMarginOutResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3029997336217653342L;
	private MarginOutBean marginOut;

	public MarginOutBean getMarginOut() {
		return marginOut;
	}

	public void setMarginOut(MarginOutBean marginOut) {
		this.marginOut = marginOut;
	}
	
}
