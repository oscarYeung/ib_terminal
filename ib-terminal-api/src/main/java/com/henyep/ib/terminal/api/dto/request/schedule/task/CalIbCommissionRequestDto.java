package com.henyep.ib.terminal.api.dto.request.schedule.task;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class CalIbCommissionRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6661908258427050842L;

	private String team_head;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;

	public Date getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
	
	public String getTeam_head() {
		return team_head;
	}

	public void setTeam_head(String team_head) {
		this.team_head = team_head;
	}
	
}
