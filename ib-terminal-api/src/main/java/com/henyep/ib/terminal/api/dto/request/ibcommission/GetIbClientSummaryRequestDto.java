package com.henyep.ib.terminal.api.dto.request.ibcommission;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class GetIbClientSummaryRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3015424836822603651L;

	@NotNull(message = Constants.ERR_COMMON_IB_CODE_IS_BLANK)
	private String ib_code;
	
	@NotNull(message = Constants.ERR_COMMON_START_DATE_IS_NULL)
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)	
	private Date start_date;
	
	@NotNull(message = Constants.ERR_COMMON_END_DATE_IS_NULL)
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date end_date;
	
	
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
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
