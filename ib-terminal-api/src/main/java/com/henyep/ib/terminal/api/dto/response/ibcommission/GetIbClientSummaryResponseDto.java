package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class GetIbClientSummaryResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4921941372529118418L;
	
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date start_date;
	
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date end_date;
	
	private List<IbClientSummariesModel> ib_client_summaries;

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

	public List<IbClientSummariesModel> getIb_client_summaries() {
		return ib_client_summaries;
	}

	public void setIb_client_summaries(List<IbClientSummariesModel> ib_client_summaries) {
		this.ib_client_summaries = ib_client_summaries;
	}



}
