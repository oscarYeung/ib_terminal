package com.henyep.ib.terminal.server.dto.security;

import java.util.Date;

public class SenderDto {

	private String sender;
	private Date last_request_time;
	

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Date getLast_request_time() {
		return last_request_time;
	}

	public void setLast_request_time(Date last_request_time) {
		this.last_request_time = last_request_time;
	}


}
