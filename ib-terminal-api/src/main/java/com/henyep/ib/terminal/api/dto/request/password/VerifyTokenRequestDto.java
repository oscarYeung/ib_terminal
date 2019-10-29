package com.henyep.ib.terminal.api.dto.request.password;

import java.io.Serializable;

public class VerifyTokenRequestDto implements Serializable {

	private static final long serialVersionUID = 5854182089688007320L;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
