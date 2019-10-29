package com.henyep.ib.terminal.api.dto.response.marginout;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.db.MarginOutBean;

public class InsertMarginOutResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3809129554809364797L;
	
	
	private MarginOutBean marginOut;
	
	private MarginOutBean marginOutFee;


	public MarginOutBean getMarginOutFee() {
		return marginOutFee;
	}


	public void setMarginOutFee(MarginOutBean marginOutFee) {
		this.marginOutFee = marginOutFee;
	}


	public MarginOutBean getMarginOut() {
		return marginOut;
	}


	public void setMarginOut(MarginOutBean marginOut) {
		this.marginOut = marginOut;
	}
	
	
	

}
