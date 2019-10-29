package com.henyep.ib.terminal.api.dto.request.user;

import javax.validation.constraints.NotNull;


public class IbForgetPwdDto {
	@NotNull(message = "{login.username.not.blank}")
	private String ibLoginName;
	
	@NotNull(message = "{reg.email.not.blank}")
	private String ibEmail;

	public String getIbLoginName() {
		return ibLoginName;
	}

	public void setIbLoginName(String ibLoginName) {
		this.ibLoginName = ibLoginName;
	}

	public String getIbEmail() {
		return ibEmail;
	}

	public void setIbEmail(String ibEmail) {
		this.ibEmail = ibEmail;
	}
	
}
