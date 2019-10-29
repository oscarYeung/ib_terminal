package com.henyep.ib.terminal.api.dto.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class BaseDateTimeRequestBodyDto extends BaseRequestBodyDto implements Serializable {

	private static final long serialVersionUID = 5080748944685619556L;

	@NotNull(message = Constants.ERR_COMMON_START_DATE_IS_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	protected Date start_date;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	protected Date end_date;

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
