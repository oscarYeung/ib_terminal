package com.henyep.ib.terminal.api.dto.response.password;

import java.io.Serializable;

public class PasswordUpdatedResponseDto implements Serializable {

	private static final long serialVersionUID = 2809794639548672387L;
	private String user_code;
	private String user_type;
	private Boolean success;

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
