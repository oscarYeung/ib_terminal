package com.henyep.ib.terminal.api.dto.response.schedule.task;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class ClearScheduleTaskResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6841621952409896429L;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;

	public Date getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
	

}
