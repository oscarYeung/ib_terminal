package com.henyep.ib.terminal.api.dto.request.user;

import java.io.Serializable;
import java.util.Date;

public class IbSummaryRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1794813299557299714L;

	private String ib_code;
	private Date trade_date;
	
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	public Date getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
	
	
	
	
	
}
