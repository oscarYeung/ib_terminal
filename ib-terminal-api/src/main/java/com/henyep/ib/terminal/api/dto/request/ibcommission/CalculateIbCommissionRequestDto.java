package com.henyep.ib.terminal.api.dto.request.ibcommission;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class CalculateIbCommissionRequestDto {

	@NotNull(message = Constants.ERR_COMMON_TRADE_DATE_IS_NULL)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date tradeDate;

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
		
	
}
