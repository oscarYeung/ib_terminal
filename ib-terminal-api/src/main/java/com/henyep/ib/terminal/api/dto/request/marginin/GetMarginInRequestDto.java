package com.henyep.ib.terminal.api.dto.request.marginin;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.dto.db.MarginInBean;
import com.henyep.ib.terminal.api.global.Constants;

public class GetMarginInRequestDto  {

	@Valid
	@NotNull(message = Constants.ERR_MARGIN_IN_NULL)
	private MarginInBean marginInBean;
	
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date startDate;
	
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date endDate;

	public MarginInBean getMarginInBean() {
		return marginInBean;
	}

	public void setMarginInBean(MarginInBean marginInBean) {
		this.marginInBean = marginInBean;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

	
	
}
