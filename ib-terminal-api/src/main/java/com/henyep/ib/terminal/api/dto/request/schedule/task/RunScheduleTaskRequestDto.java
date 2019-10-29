package com.henyep.ib.terminal.api.dto.request.schedule.task;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class RunScheduleTaskRequestDto implements Serializable{


	private static final long serialVersionUID = 5803955032637714834L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;

	public Date getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
	
	
	
}
