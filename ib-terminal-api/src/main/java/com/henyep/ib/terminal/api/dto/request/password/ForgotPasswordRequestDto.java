package com.henyep.ib.terminal.api.dto.request.password;

import java.io.Serializable;

public class ForgotPasswordRequestDto implements Serializable {

	private static final long serialVersionUID = -7931512241106737967L;
	private String user_code;
	private String user_type;
	private String email;
	private String lang;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
