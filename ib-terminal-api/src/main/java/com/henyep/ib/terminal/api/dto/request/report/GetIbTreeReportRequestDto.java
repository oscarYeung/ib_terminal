package com.henyep.ib.terminal.api.dto.request.report;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class GetIbTreeReportRequestDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5803322785526320710L;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;
	private String brand_code;

	public Date getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}

	public String getBrand_code() {
		return brand_code;
	}

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}
}
