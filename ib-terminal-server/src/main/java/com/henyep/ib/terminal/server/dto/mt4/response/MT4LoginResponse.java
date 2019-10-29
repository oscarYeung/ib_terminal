package com.henyep.ib.terminal.server.dto.mt4.response;

import com.google.gson.annotations.SerializedName;

public class MT4LoginResponse extends BaseResponseModel {

	@SerializedName("Token")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
