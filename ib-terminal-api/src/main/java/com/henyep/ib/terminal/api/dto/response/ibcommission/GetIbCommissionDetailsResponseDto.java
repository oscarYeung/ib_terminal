package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class GetIbCommissionDetailsResponseDto {

	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date start_date;
	
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date end_date;
	
	private List<IbCommissionDetailsModel> ib_commission_summary_details;

	public List<IbCommissionDetailsModel> getIb_commission_summary_details() {
		return ib_commission_summary_details;
	}

	public void setIb_commission_summary_details(List<IbCommissionDetailsModel> ib_commission_summary_details) {
		this.ib_commission_summary_details = ib_commission_summary_details;
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
