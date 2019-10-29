package com.henyep.ib.terminal.api.dto.request.rebate;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.dto.request.BaseRequestBodyDto;
import com.henyep.ib.terminal.api.global.Constants;

public class RebateRequestDto extends BaseRequestBodyDto implements Serializable {

	private static final long serialVersionUID = -52245651755202652L;
	@NotNull(message = Constants.ERR_REBATE_CODE_IS_BLANK)
	private String rebate_code;

	public String getRebate_code() {
		return rebate_code;
	}

	public void setRebate_code(String rebate_code) {
		this.rebate_code = rebate_code;
	}
	
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
