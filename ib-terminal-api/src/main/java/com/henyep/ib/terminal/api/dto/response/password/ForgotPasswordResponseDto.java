package com.henyep.ib.terminal.api.dto.response.password;

import java.io.Serializable;

public class ForgotPasswordResponseDto implements Serializable {

	private static final long serialVersionUID = 2809794639548672387L;
	private String user_code;
	private String email;
	private String verify_code;

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

	public String getVerify_code() {
		return verify_code;
	}

	public void setVerify_code(String verify_code) {
		this.verify_code = verify_code;
	}

}
