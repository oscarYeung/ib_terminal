package com.henyep.ib.terminal.api.dto.request.ibcommission;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class GetIbSummaryRequestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2933654654396156840L;
	
	
	@NotNull(message = Constants.ERR_COMMON_IB_CODE_IS_BLANK)
	private String ib_code;
	
	
	@NotNull(message = Constants.ERR_COMMON_TRADE_DATE_IS_NULL)
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
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
