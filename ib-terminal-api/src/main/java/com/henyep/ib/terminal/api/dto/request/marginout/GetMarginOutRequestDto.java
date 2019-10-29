package com.henyep.ib.terminal.api.dto.request.marginout;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.dto.db.MarginOutBean;
import com.henyep.ib.terminal.api.global.Constants;


public class GetMarginOutRequestDto implements Serializable {

	protected static final long serialVersionUID = 4019461922787121133L;
	
	
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	protected Date startDate;
	
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	protected Date endDate;
	
	
	@NotNull(message = Constants.ERR_MARGIN_OUT_NULL)
	private MarginOutBean marginOutBean;
	
	
	public MarginOutBean getMarginOutBean() {
		return marginOutBean;
	}
	public void setMarginOutBean(MarginOutBean marginOutBean) {
		this.marginOutBean = marginOutBean;
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
