package com.henyep.ib.terminal.api.dto.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class BaseTradeDateRequestBodyDto extends BaseRequestBodyDto implements Serializable {

	private static final long serialVersionUID = 5080748944685619556L;

	@NotNull(message = Constants.ERR_COMMON_TRADE_DATE_IS_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	protected Date trade_date;

	public Date getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
		
}
