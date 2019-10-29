package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class GetLastTradeDateResponseDto implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3111246414758077122L;
	
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date lastTradeDate;

	public Date getLastTradeDate() {
		return lastTradeDate;
	}

	public void setLastTradeDate(Date lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}
	

}
