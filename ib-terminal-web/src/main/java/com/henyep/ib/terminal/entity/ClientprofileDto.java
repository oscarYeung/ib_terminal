package com.henyep.ib.terminal.entity;

import org.hibernate.validator.constraints.NotEmpty;

public class ClientprofileDto {
	@NotEmpty(message = "{common.start.date.is.null}")
	private String startDate;
	@NotEmpty(message = "{common.end.date.is.null}")
	private String  endDate;
	@NotEmpty(message = "common.client.code.is.blank")
	private String clientCode;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
}
