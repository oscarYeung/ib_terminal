package com.henyep.ib.terminal.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class PasswordDto {
	@NotEmpty(message = "{margin.in.null.id.input}")
	private String user_code;
	
	@NotEmpty(message = "{common.email.is.blank}")
	@Email(message = "{reg.email}")
	private String email;
	
	private String identifyingCode;
	
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdentifyingCode() {
		return identifyingCode;
	}
	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}
	
}
