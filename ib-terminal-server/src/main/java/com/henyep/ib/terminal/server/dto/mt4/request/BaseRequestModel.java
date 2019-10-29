package com.henyep.ib.terminal.server.dto.mt4.request;

import com.google.gson.annotations.SerializedName;

public class BaseRequestModel {
	@SerializedName("Token")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
