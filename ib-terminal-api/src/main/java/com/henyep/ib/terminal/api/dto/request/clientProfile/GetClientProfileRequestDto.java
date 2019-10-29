package com.henyep.ib.terminal.api.dto.request.clientProfile;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class GetClientProfileRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4045963100982912942L;

	@NotNull(message = Constants.ERR_COMMON_CLIENT_CODE_IS_BLANK)
	private String client_code;
	
	@NotNull(message = Constants.ERR_COMMON_START_DATE_IS_NULL)
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)	
	private Date start_date;
	
	@NotNull(message = Constants.ERR_COMMON_END_DATE_IS_NULL)
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)	
	private Date end_date;
	
	
	public String getClient_code() {
		return client_code;
	}
	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	
	
	
	
}
