package com.henyep.ib.terminal.api.dto.response.clientProfile;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class ClientMarginModel {

	@JsonFormat(pattern=Constants.FORMAT_DATETIME)	
	private Date trade_date;
	private double net_margin;
	public Date getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
	public double getNet_margin() {
		return net_margin;
	}
	public void setNet_margin(double net_margin) {
		this.net_margin = net_margin;
	}
	
	
}
