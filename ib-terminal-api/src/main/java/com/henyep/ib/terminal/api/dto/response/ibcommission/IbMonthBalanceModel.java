package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;

public class IbMonthBalanceModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116275323917516805L;

	private String ib_code;
	
	private String currency;
	
	private Double commissions;
	
	private Double rebates;
	
	private Double spread_widening;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getCommissions() {
		return commissions;
	}

	public void setCommissions(Double commissions) {
		this.commissions = commissions;
	}

	public Double getRebates() {
		return rebates;
	}

	public void setRebates(Double rebates) {
		this.rebates = rebates;
	}

	public Double getSpread_widening() {
		return spread_widening;
	}

	public void setSpread_widening(Double spread_widening) {
		this.spread_widening = spread_widening;
	}

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
}
