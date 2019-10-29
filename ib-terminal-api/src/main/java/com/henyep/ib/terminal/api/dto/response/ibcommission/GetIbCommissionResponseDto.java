package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class GetIbCommissionResponseDto {

	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date start_date;
	
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date end_date;
	
	private List<IbCommissionModel> ib_commission_summaries;

	public List<IbCommissionModel> getIb_commission_summaries() {
		return ib_commission_summaries;
	}

	public void setIb_commission_summaries(List<IbCommissionModel> ib_commission_summaries) {
		this.ib_commission_summaries = ib_commission_summaries;
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
